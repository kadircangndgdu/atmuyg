package atmuyg;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class paracekme extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String url = "jdbc:sqlserver://Kadircan\\\\SQLEXPRESS:1433;databaseName=atmuyg ;encrypt=true;trustServerCertificate=true";
	private String user = "sa"; // Kullanıcı adı
	private String password = "61"; // Şifre

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					paracekme frame = new paracekme();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public int id;// class seviyesinde her yerden erişelim diye
	private JTextField txt_cekilen;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public paracekme() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("Çekmek İstediğiniz Tutar");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JButton btn_cek = new JButton("çek");
		btn_cek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paracek();
			}
		});

		JButton btnNewButton = new JButton("geridön");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kullaniciekrani i = new kullaniciekrani();
				i.setVisible(true);
				i.id = id;
				dispose();
			}
		});
		
		txt_cekilen = new JTextField();
		txt_cekilen.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(128)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(txt_cekilen, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
					.addGap(130))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(152)
					.addComponent(btn_cek, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(151, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(349, Short.MAX_VALUE)
					.addComponent(btnNewButton)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(38)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txt_cekilen, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btn_cek)
					.addGap(81)
					.addComponent(btnNewButton)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
	public Connection conn = null;
	public PreparedStatement stmt = null;
	public void paracek() {
		 float bakiye=sorgu();
		 Float cekilcekbakiye =Float.valueOf(txt_cekilen.getText());
		 if(bakiye<cekilcekbakiye) {
			 JOptionPane.showMessageDialog(null, "bakiyenizden daha fazla çekemezsiniz tekrar deneyin" , "Hata", JOptionPane.ERROR_MESSAGE);
		 }
		 else {
			String query=("UPDATE Table_1 SET bakiye = bakiye - ? WHERE id = ?");
			try {
				stmt = conn.prepareStatement(query);
				stmt.setFloat(1, cekilcekbakiye); // Yatırılacak tutar
				stmt.setInt(2, getId());
				int rowsAffected = stmt.executeUpdate();
				if (rowsAffected > 0) {
				    JOptionPane.showMessageDialog(null, "Para başarıyla çekildi.", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
				} else {
				    JOptionPane.showMessageDialog(null, "İşlem başarısız. Kullanıcı ID'si bulunamadı.", "Hata", JOptionPane.ERROR_MESSAGE);
				}
				bakiyesorg b=new bakiyesorg();
				b.setId(id);
				b.setVisible(true);
				dispose();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 
		 }

	}
public float sorgu() {
		

		
		try {
			String query="select bakiye from Table_1 where id=? ";
		
			// Veritabanı bağlantısını oluştur
			conn = DriverManager.getConnection(url, user, password);
			
			// Kullanıcı bakiyesi güncelleme SQL sorgusu
			
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, getId()); // Kullanıcı ID'si 
			
			// Sorguyu çalıştır
			  ResultSet rs = stmt.executeQuery();
			  if(rs.next()) {
				  float bakiye = rs.getFloat("bakiye");	
				  return bakiye;
			  }
			  else {
				  JOptionPane.showMessageDialog(null, "Bakiye görüntülenemedi, ID bulunamadı. " , "Hata", JOptionPane.ERROR_MESSAGE);
				  return 0;
			  }
		
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} 
	}

}
