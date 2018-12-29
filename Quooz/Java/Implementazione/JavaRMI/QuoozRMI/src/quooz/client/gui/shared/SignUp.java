package quooz.client.gui.shared;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.toedter.calendar.JDateChooser;
import quooz.client.gui.utility.RemoteHandlers;
import quooz.client.gui.utility.UIGraphicalUtils;
import quooz.client.gui.utility.ConnectionIssues;
import quooz.shared.DatabaseProblemException;
import quooz.shared.user.PlayerHandlerInterface;

public class SignUp extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldName;
	private JTextField textFieldSurname;
	private JTextField textFieldEmail;
	private JTextField textFieldUsername;
	private JPasswordField passwordFieldPwd;
	private JPasswordField passwordFieldConfirmPwd;
	private JDateChooser dateOfBirth;
	private JButton btnRegistrati;
	private static SignUp instance;

	/**
	 * Create the panel.
	 */
	public static SignUp getInstance(JPanel contentPane) {
		instance = new SignUp(contentPane);
		contentPane.add(instance);
		instance.setVisible(true);
		return instance;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(UIGraphicalUtils.getBackground(), 0, 0, null);
	}

	private SignUp(JPanel contentPane) {
		setBounds(100, 100, 800, 600);
		setLayout(null);
		Font frutiger18 = UIGraphicalUtils.loadFrutigerFont(18f);
		Color myYellow = UIGraphicalUtils.getMyYellow();
		JLabel lblTitle = new JLabel("Registrati per accedere:");
		lblTitle.setForeground(myYellow);
		lblTitle.setFont(frutiger18);
		lblTitle.setBounds(320, 127, 207, 20);
		add(lblTitle);

		JLabel lblName = new JLabel("Nome:");
		lblName.setForeground(myYellow);
		lblName.setFont(frutiger18);
		lblName.setBounds(224, 169, 164, 20);
		add(lblName);

		JLabel lblSurname = new JLabel("Cognome:");
		lblSurname.setForeground(myYellow);
		lblSurname.setFont(frutiger18);
		lblSurname.setBounds(224, 200, 164, 20);
		add(lblSurname);

		JLabel lblDateOfBirth = new JLabel("Data di nascita:");
		lblDateOfBirth.setForeground(myYellow);
		lblDateOfBirth.setFont(frutiger18);
		lblDateOfBirth.setBounds(224, 231, 164, 20);
		add(lblDateOfBirth);

		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setForeground(myYellow);
		lblEmail.setFont(frutiger18);
		lblEmail.setBounds(224, 262, 164, 20);
		add(lblEmail);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(myYellow);
		lblUsername.setFont(frutiger18);
		lblUsername.setBounds(224, 293, 164, 20);
		add(lblUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(myYellow);
		lblPassword.setFont(frutiger18);
		lblPassword.setBounds(224, 324, 164, 20);
		add(lblPassword);

		JLabel lblConfirmPassword = new JLabel("Conferma Password:");
		lblConfirmPassword.setForeground(myYellow);
		lblConfirmPassword.setFont(frutiger18);
		lblConfirmPassword.setBounds(224, 355, 164, 20);
		add(lblConfirmPassword);

		textFieldName = new JTextField();
		textFieldName.setBounds(398, 169, 149, 20);
		add(textFieldName);
		textFieldName.setColumns(10);

		textFieldSurname = new JTextField();
		textFieldSurname.setBounds(398, 200, 149, 20);
		add(textFieldSurname);
		textFieldSurname.setColumns(10);

		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(398, 262, 149, 20);
		add(textFieldEmail);
		textFieldEmail.setColumns(10);

		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(398, 293, 149, 20);
		add(textFieldUsername);
		textFieldUsername.setColumns(10);

		passwordFieldPwd = new JPasswordField();
		passwordFieldPwd.setBounds(398, 324, 149, 20);
		add(passwordFieldPwd);

		dateOfBirth = new JDateChooser();
		dateOfBirth.setBounds(398, 231, 149, 20);
		add(dateOfBirth);

		btnRegistrati = new JButton("");
		btnRegistrati.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRegistrati.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					if (btnRegistrati.isEnabled()) {
						if (isDateValid()) {
							if (isEmailAddressValid(textFieldEmail.getText())) {
								PlayerHandlerInterface ph = RemoteHandlers.getInstance().getPlayerHandler();
								if (ph.isFieldAvailabe("username", textFieldUsername.getText())) {
									if (ph.isFieldAvailabe("email", textFieldEmail.getText())) {
										VerificateCode.getInstance(contentPane, textFieldEmail.getText(),
												textFieldName.getText(), textFieldSurname.getText(),
												textFieldUsername.getText(),
												String.valueOf(passwordFieldPwd.getPassword()),
												new Date(dateOfBirth.getDate().getTime()));
										setVisible(false);
									} else
										JOptionPane.showMessageDialog(contentPane, "Email non disponibile", "Errore",
												JOptionPane.WARNING_MESSAGE);
								} else
									JOptionPane.showMessageDialog(contentPane, "Username non disponibile", "Errore",
											JOptionPane.WARNING_MESSAGE);

							} else {
								JOptionPane.showMessageDialog(contentPane, "Email sintatticamente errata", "Errore",
										JOptionPane.WARNING_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(contentPane,
									"Vieni dal futuro?!\r\nInserisci una data di nascita veritiera!!", "Errore",
									JOptionPane.WARNING_MESSAGE);
						}
					} else
						Toolkit.getDefaultToolkit().beep();
				} catch (RemoteException e) {
					RemoteHandlers.reset();
					ConnectionIssues.getInstance(contentPane);
					setVisible(false);
				} catch (NotBoundException e) {
					RemoteHandlers.reset();
					ConnectionIssues.getInstance(contentPane);
					setVisible(false);
				} catch (DatabaseProblemException e) {
					RemoteHandlers.reset();
					ConnectionIssues.getInstance(contentPane);
					setVisible(false);
				}
			}
		});
		btnRegistrati.setBounds(340, 408, 122, 67);
		btnRegistrati.setContentAreaFilled(false);
		btnRegistrati.setBorderPainted(false);
		btnRegistrati.setIcon(new ImageIcon(getClass().getResource("/Registrati.png")));
		add(btnRegistrati);
		btnRegistrati.setActionCommand("Accedi");
		btnRegistrati.setEnabled(false);

		passwordFieldConfirmPwd = new JPasswordField();
		passwordFieldConfirmPwd.setBounds(398, 355, 149, 20);
		add(passwordFieldConfirmPwd);

		JLabel lblPreviousPage = new JLabel(" INDIETRO");
		lblPreviousPage.setBounds(636, 10, 154, 58);
		lblPreviousPage.setFont(UIGraphicalUtils.loadPractiqueFont(33f));
		lblPreviousPage.setForeground(UIGraphicalUtils.getMyYellow());
		lblPreviousPage.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblPreviousPage.setIcon(new ImageIcon(this.getClass().getResource("/left_yellow.png")));
		lblPreviousPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Home.getInstance(contentPane);
				textFieldName.setText("");
				textFieldSurname.setText("");
				textFieldUsername.setText("");
				textFieldEmail.setText("");
				passwordFieldPwd.setText("");
				passwordFieldConfirmPwd.setText("");
				setVisible(false);
			}
		});
		add(lblPreviousPage);

		textFieldName.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				enableButton();
			}
		});

		textFieldEmail.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				enableButton();
			}
		});

		textFieldSurname.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				enableButton();
			}
		});

		textFieldUsername.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				enableButton();
			}
		});

		passwordFieldConfirmPwd.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				enableButton();
			}
		});

		passwordFieldPwd.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				enableButton();
			}
		});
	}

	/**
	 * Enable button only if all the fields are not empty and password and
	 * confirm password matches.
	 */
	public void enableButton() {
		if (textFieldName.getText().length() == 0 || textFieldSurname.getText().length() == 0
				|| textFieldEmail.getText().length() == 0 || textFieldUsername.getText().length() == 0
				|| String.valueOf(passwordFieldPwd.getPassword()).length() == 0
				|| String.valueOf(passwordFieldConfirmPwd.getPassword()).length() == 0
				|| !String.valueOf(passwordFieldConfirmPwd.getPassword())
						.equals(String.valueOf(passwordFieldPwd.getPassword())))
			btnRegistrati.setEnabled(false);
		else {
			btnRegistrati.setEnabled(true);
		}

	}

	/**
	 * Checks if is email address valid using a regex.
	 *
	 * @param email
	 *            the email to check
	 * @return true, if email address is valid
	 */
	public boolean isEmailAddressValid(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		Pattern p = Pattern.compile(ePattern);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * Checks if is date valid (you cannot be born in the future).
	 *
	 * @return true, if is date valid
	 */
	protected boolean isDateValid() {
		if (dateOfBirth.getDate() != null) {
			return dateOfBirth.getDate().before(new Date(System.currentTimeMillis()));
		} else
			return false;
	}
}
