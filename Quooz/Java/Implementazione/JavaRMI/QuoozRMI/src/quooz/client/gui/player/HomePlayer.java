package quooz.client.gui.player;

import javax.swing.JPanel;

import quooz.shared.DatabaseProblemException;
import quooz.shared.question.Question;
import quooz.shared.system.GameHandlerInterface;
import quooz.client.Quooz;
import quooz.client.gui.shared.Home;
import quooz.client.gui.utility.RemoteHandlers;
import quooz.client.gui.utility.UIGraphicalUtils;
import quooz.client.gui.utility.ConnectionIssues;
import quooz.shared.user.Player;
import quooz.shared.user.PlayerHandlerInterface;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomePlayer extends JPanel {

	private static final long serialVersionUID = 1L;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(UIGraphicalUtils.getBackground(), 0, 0, null);
	}

	public HomePlayer(JPanel contentPane, Player p) {
		setBounds(100, 100, 800, 600);
		setLayout(null);
		contentPane.add(this);
		JLabel btnStart = new JLabel("");
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Question q = null;
				try {
					GameHandlerInterface gh = RemoteHandlers.getInstance().getGameHandler();
					q = gh.getQuestion(p);
					if (q != null) {
						setVisible(false);
						new Game(contentPane, p, q);
					} else {

						JOptionPane.showMessageDialog(contentPane,
								"Hai esaurito le domande a disposizione nel sistema!\r\nTorna più avanti!");
					}
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
		});
		btnStart.setBorder(null);
		btnStart.setIcon(new ImageIcon(getClass().getResource("/play_blu.png")));
		btnStart.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		btnStart.setBounds(211, 424, 143, 89);
		add(btnStart);

		JLabel btnProfilo = new JLabel("");
		btnProfilo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new ProfilePlayer(contentPane, p);
				setVisible(false);
			}
		});
		btnProfilo.setToolTipText("Profilo");
		btnProfilo.setBorder(null);
		Image profile = new ImageIcon(this.getClass().getResource("/profilo_blu.png")).getImage();
		btnProfilo.setIcon(new ImageIcon(profile));
		btnProfilo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		btnProfilo.setBounds(545, 405, 127, 108);
		add(btnProfilo);

		JLabel btnStatistiche = new JLabel("");
		btnStatistiche.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new StatsPlayer(contentPane, p);
				setVisible(false);
			}
		});
		btnStatistiche.setToolTipText("Statistiche");
		btnStatistiche.setBorder(null);
		Image stats = new ImageIcon(this.getClass().getResource("/statistiche_blu.png")).getImage();
		btnStatistiche.setIcon(new ImageIcon(stats));
		btnStatistiche.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		btnStatistiche.setBounds(545, 250, 127, 108);
		add(btnStatistiche);

		JLabel btnClassifica = new JLabel("");
		btnClassifica.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Leaderboard(contentPane, p);
				setVisible(false);
			}
		});
		btnClassifica.setToolTipText("Classifica");
		btnClassifica.setBorder(null);
		Image leaderboard = new ImageIcon(this.getClass().getResource("/classifica_blu.png")).getImage();
		btnClassifica.setIcon(new ImageIcon(leaderboard));
		btnClassifica.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		btnClassifica.setBounds(545, 104, 127, 108);
		add(btnClassifica);

		JLabel btnLogOut = new JLabel("");
		btnLogOut.setIcon(new ImageIcon(getClass().getResource("/Logout_giallo.png")));
		btnLogOut.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					PlayerHandlerInterface ph = RemoteHandlers.getInstance().getPlayerHandler();
					ph.signOutPlayer(p);
					Quooz.resetClient();
					JOptionPane.showMessageDialog(contentPane, "Logout effettuato!");
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
				}

			}
		});
		btnLogOut.setBounds(10, 11, 122, 67);
		add(btnLogOut);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setToolTipText("");
		lblLogo.setBounds(91, 77, 350, 302);
		lblLogo.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/logo350x302.png")).getImage()));
		add(lblLogo);
	}
}
