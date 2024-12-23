package atmuyg;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class bakiyesorg extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_bakiye;
    private String url="jdbc:sqlserver://Kadircan\\\\SQLEXPRESS:1433;databaseName=atmuyg ;encrypt=true;trustServerCertificate=true";
    private String user = "sa"; // Kullanıcı adı
	private String password = "61"; // Şifre
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					bakiyesorg frame = new bakiyesorg();
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
	public int id;//class seviyesinde her yerden erişelim diye
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public bakiyesorg() {
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("BAKİYE  :");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JButton btnNewButton = new JButton("GERİ DÖN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kullaniciekrani i=new kullaniciekrani();
				i.setVisible(true);
				i.id=id;
				dispose();
			}
		});
		
		txt_bakiye = new JTextField();
		txt_bakiye.setText("*********");
		txt_bakiye.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("BAKİYENİ GÖR");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sorgu();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(65)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnNewButton_1)
							.addPreferredGap(ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
							.addComponent(btnNewButton))
						.addComponent(txt_bakiye, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(84)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(txt_bakiye, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnNewButton_1))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
	public void sorgu() {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		
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
				  String bakiyes = String.valueOf(bakiye);
				  txt_bakiye.setText(bakiyes);  
				
			  }
			  else {
				  JOptionPane.showMessageDialog(null, "Bakiye görüntülenemedi, ID bulunamadı. " , "Hata", JOptionPane.ERROR_MESSAGE);
			  }
		
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
		
	
}
