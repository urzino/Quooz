package quooz.client.gui.player;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.LinkedHashMap;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import quooz.client.gui.utility.RemoteHandlers;
import quooz.client.gui.utility.UIGraphicalUtils;
import quooz.client.gui.utility.ConnectionIssues;
import quooz.shared.DatabaseProblemException;
import quooz.shared.system.GameHandlerInterface;
import quooz.shared.user.Player;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.SwingConstants;

public class Leaderboard extends JPanel {	
	private static final long serialVersionUID = 1L;
	private int currentFirstShow;
	private int currentLastShow;
	private final int maxUserDisplay = 12;
	private LinkedHashMap<String, Integer> leaderboard;
	private JLabel firstClassified;
	private JLabel secondClassified;
	private JLabel thirdClassified;

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(UIGraphicalUtils.getBackground(), 0, 0, null);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(new Color(255, 212, 88));
		g2d.drawLine(0, 75, 770, 75);
	}

	/**
	 * Create the panel.
	 */
	public Leaderboard(JPanel contentPane, Player p) {
		setBounds(100, 100, 800, 600);
		setLayout(null);
		contentPane.add(this);
		currentFirstShow = 0;
		currentLastShow = maxUserDisplay;
		try {
			GameHandlerInterface gh = RemoteHandlers.getInstance().getGameHandler();
			leaderboard = gh.getLeaderboard();
			@SuppressWarnings("rawtypes")
			JList list = new JList();			
			list.setOpaque(false);
			list.setBounds(78, 100, 400, 361);
			add(list);

			firstClassified = new JLabel("");
			firstClassified.setBounds(48, 100, 30, 30);
			firstClassified.setIcon(new ImageIcon(
					new ImageIcon(this.getClass().getResource("/coppa_oro.png")).getImage().getScaledInstance(
							firstClassified.getWidth(), firstClassified.getHeight(), Image.SCALE_SMOOTH)));
			add(firstClassified);

			secondClassified = new JLabel("");
			secondClassified.setBounds(48, 130, 30, 30);
			secondClassified.setIcon(new ImageIcon(
					new ImageIcon(this.getClass().getResource("/coppa_argento.png")).getImage().getScaledInstance(
							firstClassified.getWidth(), firstClassified.getHeight(), Image.SCALE_SMOOTH)));
			add(secondClassified);

			thirdClassified = new JLabel("");
			thirdClassified.setBounds(48, 160, 30, 30);
			thirdClassified.setIcon(new ImageIcon(
					new ImageIcon(this.getClass().getResource("/coppa_bronzo.png")).getImage().getScaledInstance(
							firstClassified.getWidth(), firstClassified.getHeight(), Image.SCALE_SMOOTH)));
			add(thirdClassified);

			fillLeaderBoard(currentFirstShow, maxUserDisplay, list);

			JLabel lblPreviousPage = new JLabel(" INDIETRO");
			lblPreviousPage.setBounds(636, 10, 154, 58);
			lblPreviousPage.setFont(UIGraphicalUtils.loadPractiqueFont(33f));
			lblPreviousPage.setForeground(UIGraphicalUtils.getMyYellow());
			lblPreviousPage.setCursor(new Cursor(Cursor.HAND_CURSOR));
			lblPreviousPage.setIcon(new ImageIcon(this.getClass().getResource("/left_yellow.png")));
			lblPreviousPage.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					new HomePlayer(contentPane, p);
					setVisible(false);
				}
			});
			add(lblPreviousPage);

			JLabel lblLeadIcon = new JLabel("");
			lblLeadIcon.setBounds(15, 18, 55, 55);
			lblLeadIcon.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/coppa_oro.png")).getImage()
					.getScaledInstance(lblLeadIcon.getWidth(), lblLeadIcon.getHeight(), Image.SCALE_SMOOTH)));
			add(lblLeadIcon);

			JLabel lblTitle = new JLabel("CLASSIFICA");
			lblTitle.setVerticalAlignment(SwingConstants.TOP);
			lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
			lblTitle.setBounds(78, 15, 537, 96);
			lblTitle.setFont(UIGraphicalUtils.loadPractiqueFont(70f));
			lblTitle.setForeground(new Color(255, 212, 88));

			add(lblTitle);
			
			JLabel lblShowMore = new JLabel("");
			lblShowMore.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {	
					//go to the next leaderboard page 
					currentFirstShow += maxUserDisplay;
					currentLastShow += maxUserDisplay;
					boolean next = fillLeaderBoard(currentFirstShow, currentLastShow, list);
					if (!next) { 
						currentFirstShow -= maxUserDisplay;
						currentLastShow -= maxUserDisplay;
					}
					
				}
			});
			lblShowMore.setBounds(414, 472, 64, 64);
			lblShowMore.setIcon(new ImageIcon(
					new ImageIcon(this.getClass().getResource("/right_yellow.png")).getImage()));
			lblShowMore.setCursor(new Cursor(Cursor.HAND_CURSOR));
			add(lblShowMore);
			
			JLabel lblShowLess = new JLabel("");
			lblShowLess.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					currentFirstShow = (currentFirstShow - maxUserDisplay < 0) ? 0 : currentFirstShow - maxUserDisplay;
					currentLastShow = (currentLastShow - maxUserDisplay < maxUserDisplay) ? maxUserDisplay
							: currentLastShow - maxUserDisplay;
					fillLeaderBoard(currentFirstShow, currentLastShow, list);
				}
			});
			lblShowLess.setBounds(78, 472, 64, 64);
			lblShowLess.setIcon(new ImageIcon(
					new ImageIcon(this.getClass().getResource("/left_yellow.png")).getImage()));
			lblShowLess.setCursor(new Cursor(Cursor.HAND_CURSOR));
			add(lblShowLess);
		} catch (RemoteException e) {
			RemoteHandlers.reset();
			ConnectionIssues.getInstance(contentPane);
			setVisible(false);
		} catch (NotBoundException e) {
			RemoteHandlers.reset();
			ConnectionIssues.getInstance(contentPane);
			setVisible(false);
		} catch (DatabaseProblemException e1) {
			RemoteHandlers.reset();
			ConnectionIssues.getInstance(contentPane);
			setVisible(false);
		}
	}

	/**
	 * Fill the leaderboard from 'first' to 'last' position.
	 *
	 * @param first the first to be showed
	 * @param last the last to be showed
	 * @param list the list
	 * @return true, if other players are showed 
	 */
	private boolean fillLeaderBoard(int first, int last, @SuppressWarnings("rawtypes") JList list) {
		int y = 0;
		if (first >= leaderboard.size()) { //no more players
			JOptionPane.showMessageDialog(this, "Non ci sono altri player da visualizzare!");
			return false;
		} else if (last > leaderboard.size()) { //set upper index to size if last exceed leaderboard.size
			last = leaderboard.size();
		}
		list.removeAll();
		firstClassified.setVisible(false);
		secondClassified.setVisible(false);
		thirdClassified.setVisible(false);
		//print player rows
		for (int i = first; i < last; i++) {
			JLabel jl = new JLabel(" " + String.valueOf(i + 1) + " - " + leaderboard.keySet().toArray()[i] + " - "
					+ leaderboard.get(leaderboard.keySet().toArray()[i]));
			jl.setFont(UIGraphicalUtils.loadFrutigerFont(18f));
			jl.setVerticalAlignment(SwingConstants.CENTER);
			jl.setBounds(0, y, list.getWidth(), 30);
			jl.setForeground(new Color(255, 212, 88));
			if (i % 2 == 0) {
				jl.setBackground(new Color(7, 38, 57, 58));
			} else
				jl.setBackground(new Color(255, 255, 255, 0));
			jl.setOpaque(true);
			list.add(jl);
			y += 30;
			switch (i) {
			case 0:
				firstClassified.setVisible(true);
			case 1:
				secondClassified.setVisible(true);
			case 2:
				thirdClassified.setVisible(true);
			}
		}
		list.revalidate();
		list.repaint();
		if(first!=0){ //not showing the first page
			return true;
		}
		return false;
	}

}
