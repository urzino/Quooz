package quooz.client.gui.author;

import javax.swing.JPanel;

import quooz.client.Quooz;
import quooz.client.gui.shared.Home;
import quooz.client.gui.utility.RemoteHandlers;
import quooz.client.gui.utility.UIGraphicalUtils;
import quooz.client.gui.utility.ConnectionIssues;
import quooz.shared.user.Author;
import quooz.shared.user.AuthorHandlerInterface;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.awt.Cursor;

public class HomeAuthor extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static HomeAuthor instance;

	public static HomeAuthor getInstance(JPanel contentPane, Author a) {
		if (instance == null) {
			instance = new HomeAuthor(contentPane, a);
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

	private HomeAuthor(JPanel contentPane, Author a) {
		setBounds(100, 100, 800, 600);
		setLayout(null);

		JLabel btnAggiungi = new JLabel("");
		btnAggiungi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAggiungi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				AddQuestion.getInstance(contentPane, a);
				setVisible(false);
			}
		});
		btnAggiungi.setBounds(254, 408, 122, 67);
		btnAggiungi.setIcon(new ImageIcon(getClass().getResource("/Aggiungi.png")));
		add(btnAggiungi);

		JLabel btnModifica = new JLabel("");
		btnModifica.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnModifica.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new EditQuestion(contentPane, a);
				setVisible(false);
			}
		});
		btnModifica.setBounds(442, 408, 122, 67);
		btnModifica.setIcon(new ImageIcon(getClass().getResource("/Modifica.png")));
		add(btnModifica);

		JLabel btnLogOut = new JLabel("");
		btnLogOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					AuthorHandlerInterface ah = RemoteHandlers.getInstance().getAuthorHandler();
					ah.signOutAuthor(a);
					JOptionPane.showMessageDialog(contentPane, "Logout effettuato!");
					Quooz.resetClient();
					//return to homepage after logout
					Home.getInstance(contentPane);
					setVisible(false);

				} catch (RemoteException e) {
					RemoteHandlers.reset();
					ConnectionIssues.getInstance(contentPane);
					setVisible(false);
				} catch (NotBoundException e) {
					RemoteHandlers.reset();
					ConnectionIssues.getInstance(contentPane);
					setVisible(false);
				}

			}
		});
		btnLogOut.setBounds(10, 11, 122, 67);
		btnLogOut.setIcon(new ImageIcon(getClass().getResource("/Logout_giallo.png")));
		add(btnLogOut);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(214, 63, 350, 302);
		lblLogo.setIcon(new ImageIcon(getClass().getResource("/logo350x302.png")));
		add(lblLogo);

	}
}
