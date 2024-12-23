package atmuyg;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class kullanicigiris extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_kullaniciadi;
	private JPasswordField psw_kullanicisifre;
	private String url = "jdbc:sqlserver://Kadircan\\SQLEXPRESS:1433;databaseName=atmuyg ;encrypt=true;trustServerCertificate=true";
	private String user = "sa"; // Kullanıcı adı
	private String password = "61"; // Şifre
	private JButton btn_giris;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					kullanicigiris frame = new kullanicigiris();
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
	public kullanicigiris() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("kullanıcı adı");

		JLabel lblNewLabel_1 = new JLabel("sifre");

		txt_kullaniciadi = new JTextField();
		txt_kullaniciadi.setColumns(10);

		psw_kullanicisifre = new JPasswordField();

		btn_giris = new JButton("giriş yap");
		btn_giris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				girisyap();

			}
		});

		JButton btnNewButton = new JButton("geridön");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ilkekran i = new ilkekran();
				i.setVisible(true);
				dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(78)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 68,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 45,
												GroupLayout.PREFERRED_SIZE))
								.addGap(35)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(psw_kullanicisifre).addComponent(txt_kullaniciadi)))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(163).addComponent(btn_giris)))
				.addContainerGap(149, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
						.addContainerGap(341, Short.MAX_VALUE).addComponent(btnNewButton)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(57)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
								.addComponent(txt_kullaniciadi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(50)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_1)
								.addComponent(psw_kullanicisifre, GroupLayout.PREFERRED_SIZE, 15,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, 55, Short.MAX_VALUE).addComponent(btn_giris)
						.addGap(9).addComponent(btnNewButton)));
		contentPane.setLayout(gl_contentPane);
	}

	public void girisyap() {

		try {
			// Bağlantıyı oluşturuyor
			Connection connection = DriverManager.getConnection(url, user, password);

			// Kullanıcı adı ve şifreyi alıyoruz
			String kullaniciadi = txt_kullaniciadi.getText();
			char[] sifreArray = psw_kullanicisifre.getPassword(); // Şifreyi alıyoruz
			String sifre = new String(sifreArray); // Char dizisini String'e çeviriyoruz
			int id; // Bir sonraki ekrandan ID ile erişeceğiz

			String query = "SELECT id, kullaniciadi, sifre FROM Table_1 WHERE kullaniciadi = ? AND sifre = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, kullaniciadi);
			preparedStatement.setString(2, sifre);

			// Sorguyu çalıştırıyoruz
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				// Kullanıcı adı ve şifre doğruysa
				id = rs.getInt("id");
				JOptionPane.showMessageDialog(null, "Giriş başarılı!", "Bilgilendirme",
						JOptionPane.INFORMATION_MESSAGE);

				// Yeni ekranı açıyoruz
				kullaniciekrani kullanici = new kullaniciekrani();
				kullanici.setId(id);
				dispose(); // Mevcut giriş ekranını kapatıyoruz
				kullanici.setVisible(true); // Yeni ekranı açıyoruz
			} else {
				JOptionPane.showMessageDialog(null, "Kullanıcı adı veya şifre hatalı!", "Hata",
						JOptionPane.ERROR_MESSAGE);
			}

			// Bağlantıyı kapatıyoruz
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Bağlantı hatası: " + e.getMessage(), "Hata",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
