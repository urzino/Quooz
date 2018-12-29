package quooz.client.gui.player;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import quooz.client.gui.utility.ConnectionIssues;
import quooz.client.gui.utility.RemoteHandlers;
import quooz.client.gui.utility.UIGraphicalUtils;
import quooz.shared.DatabaseProblemException;
import quooz.shared.question.Category;
import quooz.shared.system.GameHandlerInterface;
import quooz.shared.user.Player;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import javax.swing.JProgressBar;

public class StatsPlayer extends JPanel {

	
	private static final long serialVersionUID = 1L;
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(UIGraphicalUtils.getBackground(), 0, 0, null);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(UIGraphicalUtils.getMyYellow());
		g2d.drawLine(0, 75, 770, 75);
	}

	public StatsPlayer(JPanel contentPane, Player player) {
		setBounds(100, 100, 800, 600);
		setLayout(null);
		contentPane.add(this);
		Color myYellow=UIGraphicalUtils.getMyYellow();

		JLabel lblStatsIcon = new JLabel("");
		lblStatsIcon.setBounds(10, 18, 55, 55);
		lblStatsIcon.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/profilo_giallo.png")).getImage()
				.getScaledInstance(lblStatsIcon.getWidth(), lblStatsIcon.getHeight(), Image.SCALE_SMOOTH)));
		add(lblStatsIcon);

		JLabel lblTitle = new JLabel("STATISTICHE");
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitle.setVerticalAlignment(SwingConstants.TOP);
		lblTitle.setBounds(78, 15, 537, 96);
		lblTitle.setFont(UIGraphicalUtils.loadPractiqueFont(70f));
		lblTitle.setForeground(myYellow);
		add(lblTitle);
		
		Font frutiger18=UIGraphicalUtils.loadFrutigerFont(18f);

		JLabel lblLevel = new JLabel("LIVELLO");
		lblLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblLevel.setForeground(myYellow);
		lblLevel.setFont(frutiger18);
		lblLevel.setBounds(33, 113, 55, 23);
		add(lblLevel);

		JLabel lblScore = new JLabel("PUNTEGGIO");
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setForeground(myYellow);
		lblScore.setFont(frutiger18);
		lblScore.setBounds(123, 113, 95, 23);
		add(lblScore);

		JLabel labelLevelValue = new JLabel("" + player.getLevel().toInt());
		labelLevelValue.setHorizontalAlignment(SwingConstants.CENTER);
		labelLevelValue.setForeground(myYellow);
		labelLevelValue.setFont(frutiger18);
		labelLevelValue.setBounds(33, 138, 55, 23);
		add(labelLevelValue);

		JLabel labelScoreValue = new JLabel("" + player.getScore());
		labelScoreValue.setHorizontalAlignment(SwingConstants.CENTER);
		labelScoreValue.setForeground(myYellow);
		labelScoreValue.setFont(frutiger18);
		labelScoreValue.setBounds(123, 138, 95, 23);
		add(labelScoreValue);

		JLabel btnCorrectAnswerHistory = new JLabel("");
		btnCorrectAnswerHistory.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCorrectAnswerHistory.setIcon(new ImageIcon(getClass().getResource("/risposte_corrette.png")));
		btnCorrectAnswerHistory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new CorrectAnswerHistory(contentPane, player);
				setVisible(false);
			}
		});
			
		btnCorrectAnswerHistory.setBounds(354, 99, 122, 67);
		add(btnCorrectAnswerHistory);

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
		
		JLabel lblArt = new JLabel("art");
		lblArt.setToolTipText("Arte");
		lblArt.setBounds(30, 200, 55, 55);
		lblArt.setIcon(new ImageIcon(getClass().getResource("/art55.png")));
		add(lblArt);
		
		JLabel lblGeography = new JLabel("geography");
		lblGeography.setToolTipText("Geografia");
		lblGeography.setBounds(30, 260, 55, 55);
		lblGeography.setIcon(new ImageIcon(getClass().getResource("/geography55.png")));
		add(lblGeography);
		
		JLabel lblEntertainemant = new JLabel("entertainemant");
		lblEntertainemant.setToolTipText("Intrattenimento");
		lblEntertainemant.setBounds(30, 320, 55, 55);
		lblEntertainemant.setIcon(new ImageIcon(getClass().getResource("/entertainment55.png")));
		add(lblEntertainemant);
		
		JLabel lblSport = new JLabel("sport");
		lblSport.setToolTipText("Sport");
		lblSport.setBounds(30, 380, 55, 55);
		lblSport.setIcon(new ImageIcon(getClass().getResource("/sport55.png")));
		add(lblSport);
		
		JLabel lblScience = new JLabel("science");
		lblScience.setToolTipText("Scienze");
		lblScience.setBounds(30, 440, 55, 55);
		lblScience.setIcon(new ImageIcon(getClass().getResource("/science55.png")));
		add(lblScience);
		
		JLabel lblHistory = new JLabel("history");
		lblHistory.setToolTipText("Storia");
		lblHistory.setBounds(30, 500, 55, 55);
		lblHistory.setIcon(new ImageIcon(getClass().getResource("/history55.png")));
		add(lblHistory);
		
		JProgressBar progressBarArt = new JProgressBar();
		progressBarArt.setBorder(null);
		progressBarArt.setBorderPainted(false);
		progressBarArt.setBounds(123, 207, 648, 30);
		progressBarArt.setForeground(new Color(238,37,41));
		add(progressBarArt);
		
		progressBarArt.setStringPainted(true);
		
		JProgressBar progressBarGeo = new JProgressBar();
		progressBarGeo.setBorder(null);
		progressBarGeo.setBorderPainted(false);
		
		progressBarGeo.setStringPainted(true);
		progressBarGeo.setBounds(123, 272, 648, 30);
		progressBarGeo.setForeground(new Color(0,37,191));
		add(progressBarGeo);
		
		JProgressBar progressBarEnter = new JProgressBar();		
		progressBarEnter.setBorder(null);
		progressBarEnter.setBorderPainted(false);
		progressBarEnter.setStringPainted(true);
		progressBarEnter.setForeground(new Color(237,34,144));
		progressBarEnter.setBounds(123, 333, 648, 30);
		add(progressBarEnter);
		
		JProgressBar progressBarSport = new JProgressBar();		
		progressBarSport.setBorder(null);
		progressBarSport.setBorderPainted(false);
		progressBarSport.setStringPainted(true);
		progressBarSport.setForeground(new Color(246,134,31));
		progressBarSport.setBounds(123, 394, 648, 30);
		add(progressBarSport);
		
		JProgressBar progressBarSci = new JProgressBar();		
		progressBarSci.setBorder(null);
		progressBarSci.setBorderPainted(false);
		progressBarSci.setStringPainted(true);
		progressBarSci.setForeground(new Color(0,170,18));
		progressBarSci.setBounds(123, 452, 648, 30);
		add(progressBarSci);
		
		JProgressBar progressBarHyst = new JProgressBar();		
		progressBarHyst.setBorder(null);
		progressBarHyst.setBorderPainted(false);
		progressBarHyst.setStringPainted(true);
		progressBarHyst.setForeground(new Color(255,246,0));
		progressBarHyst.setBounds(123, 511, 648, 30);
		add(progressBarHyst);
		
		try {
			GameHandlerInterface gh= RemoteHandlers.getInstance().getGameHandler();
			LinkedHashMap<Category,Integer> categoryStats = gh.getCategoriesStats(player);
			if(categoryStats.containsKey(Category.ART)){
				progressBarArt.setValue(categoryStats.get(Category.ART));
			}
			if(categoryStats.containsKey(Category.ENTERTAINMENT)){
				progressBarEnter.setValue(categoryStats.get(Category.ENTERTAINMENT));
			}
			if(categoryStats.containsKey(Category.GEOGRAPHY)){
				progressBarGeo.setValue(categoryStats.get(Category.GEOGRAPHY));
			}
			if(categoryStats.containsKey(Category.HISTORY)){
				progressBarHyst.setValue(categoryStats.get(Category.HISTORY));
			}
			if(categoryStats.containsKey(Category.SCIENCE)){
				progressBarSci.setValue(categoryStats.get(Category.SCIENCE));
			}
			if(categoryStats.containsKey(Category.SPORT)){
				progressBarSport.setValue(categoryStats.get(Category.SPORT));
			}
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
}
