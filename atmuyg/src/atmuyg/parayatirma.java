package atmuyg;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class parayatirma extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField text_yatir;
	private String url = "jdbc:sqlserver://Kadircan\\SQLEXPRESS:1433;databaseName=atmuyg ;encrypt=true;trustServerCertificate=true";
	private String user = "sa"; // Kullanıcı adı
	private String password = "61"; // Şifre

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					parayatirma frame = new parayatirma();
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public parayatirma() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("YATIRILACAK TUTAR");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JButton btn_yatir = new JButton("YATIR");
		btn_yatir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parayatir();
			}
		});

		text_yatir = new JTextField();
		text_yatir.setColumns(10);

		JButton btnNewButton = new JButton("GERİ DÖN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kullaniciekrani i = new kullaniciekrani();
				i.id = id;
				i.setVisible(true);
				dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(359, Short.MAX_VALUE)
					.addComponent(btnNewButton))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(140)
					.addComponent(btn_yatir, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(189, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(127)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
						.addComponent(text_yatir, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(139, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(53)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(text_yatir, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btn_yatir)
					.addPreferredGap(ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
					.addComponent(btnNewButton))
		);
		contentPane.setLayout(gl_contentPane);
	}

	public void parayatir() {
		float yatirilcaktutar = Float.parseFloat(text_yatir.getText());
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			// Veritabanı bağlantısını oluştur
			conn = DriverManager.getConnection(url, user, password);

			// Kullanıcı bakiyesi güncelleme SQL sorgusu
			String sql = "UPDATE Table_1 SET bakiye = bakiye + ? WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setFloat(1, yatirilcaktutar); // Yatırılacak tutar
			stmt.setInt(2, id); // Kullanıcı ID'si (getId() methodu ile alınır)

			// Sorguyu çalıştır
			int rowsUpdated = stmt.executeUpdate();

			// Sonuç kontrolü
			if (rowsUpdated > 0) {

				JOptionPane.showMessageDialog(null, "Bakiye başarıyla güncellendi. ", "bilgi",
						JOptionPane.INFORMATION_MESSAGE);
			} else {

				JOptionPane.showMessageDialog(null, "Bakiye güncellenemedi, ID bulunamadı. ", "Hata",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
