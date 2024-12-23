package atmuyg;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class kullanicikayit extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_ad;
	private JTextField txt_sifre;

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
					kullanicikayit frame = new kullanicikayit();
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
	public kullanicikayit() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		JButton btn_kayit = new JButton("kayıt ol");
		btn_kayit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kayitol(); // Kayıt işlemini yapıyoruz
				dispose();
				kullanicigiris g = new kullanicigiris();
				g.setVisible(true);
			}
		});

		JLabel lblNewLabel = new JLabel("kullanıcı adı");

		JLabel lblNewLabel_1 = new JLabel("şifre");

		txt_ad = new JTextField();
		txt_ad.setColumns(10);

		txt_sifre = new JTextField();
		txt_sifre.setColumns(10);

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
						.addGroup(gl_contentPane.createSequentialGroup().addGap(168).addComponent(btn_kayit))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(74)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(txt_ad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(84)
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(txt_sifre, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(158, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
						.addContainerGap(341, Short.MAX_VALUE).addComponent(btnNewButton)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(49)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
								.addComponent(txt_ad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(txt_sifre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, 51, Short.MAX_VALUE).addComponent(btn_kayit)
						.addGap(45).addComponent(btnNewButton)));
		contentPane.setLayout(gl_contentPane);
	}

	public void kayitol() {
		String ad = txt_ad.getText();
		String sifre = txt_sifre.getText();
		try (Connection connection = DriverManager.getConnection(url, user, password)) {
			// Kullanıcı kaydını yapmak için SQL sorgusu
			String query = "INSERT INTO Table_1 (kullaniciadi, sifre,bakiye) VALUES (?, ?,0)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, ad);
			preparedStatement.setString(2, sifre);

			// Sorguyu çalıştıyor
			int result = preparedStatement.executeUpdate();

			// Kayıt işleminin başarılı olup olmadığını kontrol edyorumi
			if (result > 0) {
				JOptionPane.showMessageDialog(null, "Kullanıcı başarıyla kaydedildi!", "Başarılı",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Kayıt işlemi başarısız.", "Hata", JOptionPane.ERROR_MESSAGE);
			}

			// Bağlantı kapandıktan sonra PreparedStatement'ı kapatıyoruz
			preparedStatement.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Bağlantı hatası: " + e.getMessage(), "Hata",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

}
