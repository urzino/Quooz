package quooz.client.gui.shared;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import quooz.client.gui.utility.UIGraphicalUtils;

public class Home extends JPanel {
	/**
	 * 
	 */
	private static Home instance;

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public static Home getInstance(JPanel contentPane) {
		if (instance == null) {
			instance = new Home(contentPane);
			contentPane.add(instance);
		}
		instance.setVisible(true);
		return instance;
	}

	@Override
protected void paintComponent(Graphics g){
	super.paintComponent(g);
	g.drawImage(UIGraphicalUtils.getBackground(), 0, 0, null);
}

	private Home(JPanel contentPane) {
		setBounds(100, 100, 800, 600);
		setLayout(null);

		JLabel btnAccedi = new JLabel("");
		btnAccedi.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAccedi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SignIn.getInstance(contentPane);
				setVisible(false);
			}
		});
		btnAccedi.setIcon(new ImageIcon(getClass().getResource("/Accedi.png")));
		btnAccedi.setBounds(337, 402, 122, 66);
		add(btnAccedi);

		JLabel bntRegistrati = new JLabel("");
		bntRegistrati.setBorder(null);
		Image addUser = new ImageIcon(this.getClass().getResource("/registration.png")).getImage();
		bntRegistrati.setIcon(new ImageIcon(addUser));
		bntRegistrati.setCursor(new Cursor(Cursor.HAND_CURSOR));
		bntRegistrati.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				SignUp signUp = SignUp.getInstance(contentPane);
				contentPane.add(signUp);
				setVisible(false);
			}
		});
		bntRegistrati.setBounds(743, 11, 47, 60);
		add(bntRegistrati);

		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(214, 63, 350, 302);
		lblLogo.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/logo350x302.png")).getImage()));
		add(lblLogo);

	}
}
