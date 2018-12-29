package quooz.client.gui.shared;

import javax.swing.JPanel;

import quooz.client.gui.utility.RemoteHandlers;
import quooz.client.gui.utility.UIGraphicalUtils;
import quooz.client.gui.utility.ConnectionIssues;
import quooz.shared.DatabaseProblemException;
import quooz.shared.user.PlayerHandlerInterface;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.SwingConstants;

public class VerificateCode extends JPanel {

	private static final long serialVersionUID = 1L;

	public static VerificateCode instance;

	private String verificationCode;
	private JTextField textCode;

	public static VerificateCode getInstance(JPanel contentPane, String email, String name, String surname,
			String username, String password, Date dateOfBirth) {
		instance = new VerificateCode(contentPane, email, name, surname, username, password, dateOfBirth);
		contentPane.add(instance);
		instance.setVisible(true);
		return instance;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(UIGraphicalUtils.getBackground(), 0, 0, null);
	}

	private VerificateCode(JPanel contentPane, String email, String name, String surname, String username,
			String password, Date dateOfBirth) {
		setBounds(100, 100, 800, 600);
		setLayout(null);
		Font frutiger18 = UIGraphicalUtils.loadFrutigerFont(18f);
		Color myYellow = UIGraphicalUtils.getMyYellow();
		try {
			PlayerHandlerInterface ph = RemoteHandlers.getInstance().getPlayerHandler();
			JLabel lblVerification = new JLabel("Inserisci di seguito il codice ricevuto a questa mail:" + email);
			lblVerification.setHorizontalAlignment(SwingConstants.CENTER);
			lblVerification.setForeground(myYellow);
			lblVerification.setFont(frutiger18);
			lblVerification.setBounds(0, 191, 800, 23);
			add(lblVerification);

			textCode = new JTextField();
			textCode.setHorizontalAlignment(SwingConstants.CENTER);
			textCode.setBounds(300, 237, 200, 35);
			add(textCode);
			textCode.setColumns(10);
			verificationCode = ph.generateVerificationCode();

			ph.sendVerificationCode(email, verificationCode);
		} catch (RemoteException e2) {
			RemoteHandlers.reset();
			ConnectionIssues.getInstance(contentPane);
			setVisible(false);
		} catch (NotBoundException e2) {
			RemoteHandlers.reset();
			ConnectionIssues.getInstance(contentPane);
			setVisible(false);
		}

		JLabel btnConfermaRegistrazione = new JLabel("Conferma Registrazione");
		btnConfermaRegistrazione.setIcon(new ImageIcon(this.getClass().getResource("/conferma.png")));
		btnConfermaRegistrazione.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(verificationCode);
				String insertCode = textCode.getText();
				System.out.println(textCode.getText());
				if (verificationCode.equals(insertCode)) {
					try {
						PlayerHandlerInterface ph = RemoteHandlers.getInstance().getPlayerHandler();
						ph.signUpNewPlayer(name, surname, dateOfBirth, username, password, email);
						JOptionPane.showMessageDialog(contentPane,
								"Registrazione completata!! Torna alla home per iniziare a giocare");
						SignIn.getInstance(contentPane);
						setVisible(false);
					} catch (RemoteException e1) {
						JOptionPane.showMessageDialog(contentPane, e1.getMessage());
						RemoteHandlers.reset();
						Home.getInstance(contentPane);
						setVisible(false);
					} catch (NotBoundException e1) {
						JOptionPane.showMessageDialog(contentPane, e1.getMessage());
						RemoteHandlers.reset();
						Home.getInstance(contentPane);
						setVisible(false);
					} catch (DatabaseProblemException e1) {
						RemoteHandlers.reset();
						ConnectionIssues.getInstance(contentPane);
						setVisible(false);
					}
				} else
					JOptionPane.showMessageDialog(contentPane,
							"Registrazione annulata!! Codice errato, rieseguire la registrazione", "Errore",
							JOptionPane.ERROR_MESSAGE);
			}
		});
		btnConfermaRegistrazione.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnConfermaRegistrazione.setBounds(340, 307, 122, 67);
		add(btnConfermaRegistrazione);

		JLabel btnHome = new JLabel("Home");
		btnHome.setIcon(new ImageIcon(this.getClass().getResource("/home.png")));
		btnHome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Home.getInstance(contentPane);
				setVisible(false);
			}
		});
		btnHome.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnHome.setBounds(350, 469, 100, 67);
		add(btnHome);

	}
}