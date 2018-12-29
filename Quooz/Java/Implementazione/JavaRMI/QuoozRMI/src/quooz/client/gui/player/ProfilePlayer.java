package quooz.client.gui.player;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import quooz.client.gui.shared.Home;
import quooz.client.gui.utility.RemoteHandlers;
import quooz.client.gui.utility.UIGraphicalUtils;
import quooz.client.gui.utility.ConnectionIssues;
import quooz.shared.DatabaseProblemException;
import quooz.shared.user.Player;
import quooz.shared.user.PlayerHandlerInterface;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.SwingConstants;

public class ProfilePlayer extends JPanel {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Player player;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(UIGraphicalUtils.getBackground(), 0, 0, null);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(new Color(255, 212, 88));
		g2d.drawLine(0, 75, 770, 75);
	}

	public ProfilePlayer(JPanel contentPane, Player player) {
		setBounds(100, 100, 800, 600);
		setLayout(null);
		this.player = player;
		contentPane.add(this);

		JLabel lblPreviousPage = new JLabel(" INDIETRO");
		lblPreviousPage.setBounds(636, 10, 154, 58);
		lblPreviousPage.setFont(UIGraphicalUtils.loadPractiqueFont(33f));
		lblPreviousPage.setForeground(UIGraphicalUtils.getMyYellow());
		lblPreviousPage.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblPreviousPage.setIcon(new ImageIcon(this.getClass().getResource("/left_yellow.png")));
		lblPreviousPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new HomePlayer(contentPane, player);
				setVisible(false);
			}
		});
		add(lblPreviousPage);

		JLabel btnCancellaAccount = new JLabel("Cancella account");
		btnCancellaAccount.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCancellaAccount.setIcon(new ImageIcon(getClass().getResource("/Cancella account.png")));
		btnCancellaAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Object[] dialog = { "SI", "NO" };
				int n = JOptionPane.showOptionDialog(contentPane,
						"Sei sicuro di voler eliminare il tuo account?" + "di gioco?\r\nPerderai tutti i salvataggi! ",
						"Sei sicuro?", JOptionPane.WARNING_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, dialog,
						dialog[0]);
				if (n == 0) {
					try {
						PlayerHandlerInterface ph = RemoteHandlers.getInstance().getPlayerHandler();
						ph.unregisterPlayer(player);
						setP(null);
						Home.getInstance(contentPane);
						setVisible(false);
					} catch (RemoteException e1) {
						RemoteHandlers.reset();
						ConnectionIssues.getInstance(contentPane);
						setVisible(false);
					} catch (NotBoundException e1) {
						RemoteHandlers.reset();
						ConnectionIssues.getInstance(contentPane);
						setVisible(false);
					} catch (DatabaseProblemException e1) {
						RemoteHandlers.reset();
						ConnectionIssues.getInstance(contentPane);
						setVisible(false);
					}
				}
			}
		});
		
		btnCancellaAccount.setBounds(22, 333, 122, 67);
		add(btnCancellaAccount);

		Font frutiger18 = UIGraphicalUtils.loadFrutigerFont(18f);
		Color myYellow=UIGraphicalUtils.getMyYellow();
		JLabel level = new JLabel("LIVELLO: " + player.getLevel().toInt());
		level.setFont(frutiger18);
		level.setBounds(22, 294, 432, 20);
		level.setForeground(myYellow);
		add(level);
		JLabel score = new JLabel("PUNTEGGIO: " + player.getScore());
		score.setFont(frutiger18);
		score.setBounds(22, 263, 432, 20);
		score.setForeground(myYellow);
		add(score);
		JLabel date = new JLabel("DATA DI NASCITA: " + player.getDateOfBirth());
		date.setBounds(22, 232, 432, 20);
		date.setFont(frutiger18);
		date.setForeground(myYellow);
		add(date);
		JLabel cognome = new JLabel("COGNOME: " + player.getSurname());
		cognome.setFont(frutiger18);
		cognome.setForeground(myYellow);
		cognome.setBounds(22, 201, 432, 20);
		add(cognome);
		JLabel name = new JLabel("NOME: " + player.getName());
		name.setFont(frutiger18);
		name.setForeground(myYellow);
		name.setBounds(22, 170, 432, 20);
		add(name);
		JLabel email = new JLabel("EMAIL: " + player.getEmail());
		email.setBounds(22, 139, 432, 20);
		email.setFont(frutiger18);
		email.setForeground(myYellow);
		add(email);
		JLabel user = new JLabel("USERNAME: " + player.getUsername());
		user.setFont(frutiger18);
		user.setBounds(22, 108, 432, 20);
		user.setForeground(myYellow);
		add(user);

		JLabel lblProfileIcon = new JLabel("");
		lblProfileIcon.setBounds(10, 18, 55, 55);
		lblProfileIcon
				.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/profilo_giallo.png")).getImage()
						.getScaledInstance(lblProfileIcon.getWidth(), lblProfileIcon.getHeight(), Image.SCALE_SMOOTH)));
		add(lblProfileIcon);

		JLabel lblTitle = new JLabel("PROFILO");
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitle.setVerticalAlignment(SwingConstants.TOP);
		lblTitle.setBounds(78, 15, 537, 96);
		lblTitle.setFont(UIGraphicalUtils.loadPractiqueFont(70f));
		lblTitle.setForeground(new Color(255, 212, 88));
		add(lblTitle);

	}

	public void setP(Player p) {
		this.player = p;
	}
}
