package atmuyg;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class kullaniciekrani extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					kullaniciekrani frame = new kullaniciekrani();
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

	public kullaniciekrani() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		JButton btn_bakiye = new JButton("bakiye sorgulama");
		btn_bakiye.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bakiyesorg b = new bakiyesorg();
				b.setId(id);
				b.setVisible(true);
				dispose();
			}
		});

		JButton btnNewButton = new JButton("para çekme");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paracekme p = new paracekme();
				p.setId(id);
				p.setVisible(true);
				dispose();
			}
		});

		JButton btnNewButton_1 = new JButton("para yatırma");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parayatirma p = new parayatirma();
				p.setId(id);
				p.setVisible(true);
				dispose();
			}
		});

		JButton btnNewButton_2 = new JButton("çıkış");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ilkekran i = new ilkekran();
				i.setVisible(true);
				dispose();

			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(62)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
						.addComponent(btn_bakiye, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
					.addGap(72)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnNewButton_2, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
						.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
					.addGap(14))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(41)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_bakiye, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
					.addGap(84))
		);
		contentPane.setLayout(gl_contentPane);
	}

}
