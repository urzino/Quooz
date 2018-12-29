package quooz.client.gui.shared;

import java.awt.*;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import quooz.client.Quooz;
import quooz.client.gui.author.HomeAuthor;
import quooz.client.gui.player.HomePlayer;
import quooz.client.gui.utility.RemoteHandlers;
import quooz.client.gui.utility.UIGraphicalUtils;
import quooz.client.gui.utility.ConnectionIssues;
import quooz.shared.DatabaseProblemException;
import quooz.shared.UserAlreadyLoggedException;
import quooz.shared.user.*;
import javax.swing.JPasswordField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class SignIn extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField usernameTextField;
	private JPasswordField passwordTextField;
	private static SignIn instance;

	/**
	 * Create the panel.
	 */
	public static SignIn getInstance(JPanel contentPane) {
		if (instance == null) {
			instance = new SignIn(contentPane);
			contentPane.add(instance);
		}
		instance.setVisible(true);
		return instance;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(UIGraphicalUtils.getBackground(), 0, 0, null);
	}

	private SignIn(JPanel contentPane) {
		setToolTipText("");
		setBorder(null);

		setBounds(100, 100, 800, 600);
		setLayout(null);
		Font frutiger18 = UIGraphicalUtils.loadFrutigerFont(18f);
		Color myYellow = UIGraphicalUtils.getMyYellow();
		JButton btnAccedi = new JButton("");
		btnAccedi.setOpaque(false);
		btnAccedi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAccedi.setEnabled(false);
		btnAccedi.setForeground(new Color(0, 0, 139));
		btnAccedi.setBackground(myYellow);
		btnAccedi.setBounds(344, 325, 122, 67);
		btnAccedi.setContentAreaFilled(false);
		btnAccedi.setBorderPainted(false);
		btnAccedi.setIcon(new ImageIcon(getClass().getResource("/Accedi.png")));
		add(btnAccedi);
		btnAccedi.setActionCommand("Accedi");

		JLabel lblTitle = new JLabel("Inserisci le tue credenziali per accedere all'area personale:");
		lblTitle.setFont(frutiger18);
		lblTitle.setForeground(myYellow);
		lblTitle.setBounds(213, 196, 469, 23);
		add(lblTitle);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(frutiger18);
		lblUsername.setForeground(myYellow);
		lblUsername.setBounds(272, 243, 104, 20);
		add(lblUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(frutiger18);
		lblPassword.setForeground(myYellow);
		lblPassword.setBounds(272, 274, 104, 20);
		add(lblPassword);

		usernameTextField = new JTextField();
		usernameTextField.setBounds(386, 243, 144, 20);
		add(usernameTextField);
		usernameTextField.setColumns(10);

		passwordTextField = new JPasswordField();
		passwordTextField.setBounds(386, 274, 144, 20);
		add(passwordTextField);

		usernameTextField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) { // watch for key strokes,
													// enable button only if
													// both username and
													// password are filled
				if (usernameTextField.getText().length() == 0
						|| String.valueOf(passwordTextField.getPassword()).length() == 0)
					btnAccedi.setEnabled(false);
				else {
					btnAccedi.setEnabled(true);
				}
			}
		});

		passwordTextField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) { // watch for key strokes,
													// enable button only if
													// both username and
													// password are filled
				if (usernameTextField.getText().length() == 0
						|| String.valueOf(passwordTextField.getPassword()).length() == 0)
					btnAccedi.setEnabled(false);
				else {
					btnAccedi.setEnabled(true);
				}
			}
		});

		JLabel lblPreviousPage = new JLabel(" INDIETRO");
		lblPreviousPage.setBounds(668, 11, 122, 58);
		lblPreviousPage.setFont(UIGraphicalUtils.loadPractiqueFont(33f));
		lblPreviousPage.setForeground(UIGraphicalUtils.getMyYellow());
		lblPreviousPage.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblPreviousPage.setIcon(new ImageIcon(this.getClass().getResource("/left_yellow.png")));
		lblPreviousPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Home.getInstance(contentPane);
				usernameTextField.setText("");
				passwordTextField.setText("");
				setVisible(false);
			}
		});
		add(lblPreviousPage);

		btnAccedi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (btnAccedi.isEnabled()) {
					String name = usernameTextField.getText();
					String pwd = String.valueOf(passwordTextField.getPassword());
					Player p = null;
					try {
						PlayerHandlerInterface ph = RemoteHandlers.getInstance().getPlayerHandler();
						Quooz.setClient(name);
						//try player login
						p = ph.signInPlayer(name, pwd, Quooz.getClient());

						if (p != null) {
							//username and password represent a player
							new HomePlayer(contentPane, p);
							setVisible(false);
							usernameTextField.setText("");
							passwordTextField.setText("");
							btnAccedi.setEnabled(false);
						} else {
							Author a = null;
							AuthorHandlerInterface ah = RemoteHandlers.getInstance().getAuthorHandler();
							//try author login
							a = ah.signIn(name, pwd, Quooz.getClient());
							if (a != null) {
								//username and password not represent an author
								HomeAuthor.getInstance(contentPane, a);
								setVisible(false);
								usernameTextField.setText("");
								passwordTextField.setText("");
								btnAccedi.setEnabled(false);
							} else {
								//there's no author registred on the credentials
								Quooz.resetClient();
								JOptionPane.showMessageDialog(contentPane, "Nickname o password errati");
							}
						}
					} catch (UserAlreadyLoggedException e) {
						JOptionPane.showMessageDialog(contentPane, e.getMessage());

					} catch (RemoteException e1) {
						Quooz.resetClient();
						RemoteHandlers.reset();
						ConnectionIssues.getInstance(contentPane);
						setVisible(false);

					} catch (NotBoundException e) {
						Quooz.resetClient();
						RemoteHandlers.reset();
						ConnectionIssues.getInstance(contentPane);
						setVisible(false);
					} catch (DatabaseProblemException e) {
						RemoteHandlers.reset();
						ConnectionIssues.getInstance(contentPane);
						setVisible(false);
					}

				} else
					Toolkit.getDefaultToolkit().beep();
			}
		});
	}
}
