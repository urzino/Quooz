package quooz.client.gui.player;

import javax.swing.JPanel;
import javax.swing.Timer;

import quooz.shared.DatabaseProblemException;
import quooz.shared.question.Level;
import quooz.shared.question.Option;
import quooz.shared.question.Question;
import quooz.shared.system.GameHandlerInterface;
import quooz.client.gui.utility.RemoteHandlers;
import quooz.client.gui.utility.UIGraphicalUtils;
import quooz.client.gui.utility.ConnectionIssues;
import quooz.client.gui.utility.MultiLineLabel;
import quooz.shared.user.Player;
import quooz.shared.user.PlayerHandlerInterface;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Game extends JPanel {	
	private static final long serialVersionUID = 1L;
	private Timer timer;
	private Integer currentTime;
	private Integer maxTime;
	private Player updatedPlayer;

	public Player getUpdatedPlayer() {
		return updatedPlayer;
	}

	public void setUpdatedPlayer(Player updatedPlayer) {
		this.updatedPlayer = updatedPlayer;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(UIGraphicalUtils.getBackground(), 0, 0, null);

		Graphics2D g2d = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setStroke(new BasicStroke(7));
		g2d.setRenderingHints(rh);
		int step = 360 / maxTime; //step degrees for each seconds
		int fractionedTime=(int) Math.ceil(maxTime/3); //time for each Color (10s/7s/4s)
		if(currentTime>=fractionedTime && currentTime<((maxTime-fractionedTime))){
			g2d.setColor(Color.ORANGE);
		}else if(currentTime>=((maxTime-fractionedTime))){
			g2d.setColor(Color.RED);
		}else
			g2d.setColor(new Color(0, 230, 77));
		
		g2d.setStroke(new BasicStroke(7)); //each time draw an arc of 360°-(step*currentTime)
		g2d.drawArc(632, 23, 100, 100, 90, 360 - (step * currentTime));
	}

	public Game(JPanel contentPane, Player p, Question q) {
		setBounds(100, 100, 800, 600);
		setLayout(null);
		setOpaque(false);
		currentTime = new Integer(0);
		maxTime = new Integer((q.getDifficultyLevel().toInt()) * 10);
		Font frutiger18 = UIGraphicalUtils.loadFrutigerFont(18f);
		Font pratique60 = UIGraphicalUtils.loadPractiqueFont(60f);
		Color myYellow = UIGraphicalUtils.getMyYellow();
		ImageIcon optionIcon1 = new ImageIcon(this.getClass().getResource("/optionTxt1.png"));
		ImageIcon optionIcon2 = new ImageIcon(this.getClass().getResource("/optionTxt2.png"));
		
		
		
		
		contentPane.add(this);
		Color myGreen=new Color(0, 230, 77);
		JLabel lblTime = new JLabel(maxTime.toString());
		lblTime.setBackground(Color.WHITE);
		lblTime.setBorder(null);
		lblTime.setForeground(myGreen);
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setFont(pratique60);
		lblTime.setBounds(644, 41, 78, 78);
		add(lblTime);
		JPanel panelGameTxtTxt = new JPanel();
		panelGameTxtTxt.setBounds(25, 25, 544, 550);
		add(panelGameTxtTxt);
		panelGameTxtTxt.setLayout(null);

		MultiLineLabel lblTxtQuestionTxtTxt = new MultiLineLabel("Testo domanda");
		lblTxtQuestionTxtTxt.setHorizontalTextAlignment(MultiLineLabel.CENTER);
		lblTxtQuestionTxtTxt.setFont(frutiger18);
		lblTxtQuestionTxtTxt.setForeground(myYellow);
		lblTxtQuestionTxtTxt.setBounds(25, 5, 494, 70);
		panelGameTxtTxt.add(lblTxtQuestionTxtTxt);

		JLabel btnOption1TxtTxt = new JLabel("");
		btnOption1TxtTxt.setBounds(25, 175, 494, 50);
		btnOption1TxtTxt.setFont(frutiger18);
		btnOption1TxtTxt.setForeground(myYellow);
		btnOption1TxtTxt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOption1TxtTxt.setIcon(optionIcon1);
		btnOption1TxtTxt.setHorizontalTextPosition(JLabel.CENTER);
		panelGameTxtTxt.add(btnOption1TxtTxt);

		JLabel btnOption2TxtTxt = new JLabel("");
		btnOption2TxtTxt.setBounds(25, 250, 494, 50);
		btnOption2TxtTxt.setFont(frutiger18);
		btnOption2TxtTxt.setForeground(myYellow);
		btnOption2TxtTxt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOption2TxtTxt.setIcon(optionIcon1);
		btnOption2TxtTxt.setHorizontalTextPosition(JLabel.CENTER);
		panelGameTxtTxt.add(btnOption2TxtTxt);

		JLabel btnOption3TxtTxt = new JLabel("");
		btnOption3TxtTxt.setBounds(25, 325, 494, 50);
		btnOption3TxtTxt.setFont(frutiger18);
		btnOption3TxtTxt.setForeground(myYellow);
		btnOption3TxtTxt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOption3TxtTxt.setIcon(optionIcon1);
		btnOption3TxtTxt.setHorizontalTextPosition(JLabel.CENTER);
		panelGameTxtTxt.add(btnOption3TxtTxt);

		JLabel btnOption4TxtTxt = new JLabel("");
		btnOption4TxtTxt.setBounds(25, 400, 494, 50);
		btnOption4TxtTxt.setFont(frutiger18);
		btnOption4TxtTxt.setForeground(myYellow);
		btnOption4TxtTxt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOption4TxtTxt.setIcon(optionIcon1);
		btnOption4TxtTxt.setHorizontalTextPosition(JLabel.CENTER);
		panelGameTxtTxt.add(btnOption4TxtTxt);

		JPanel panelGameTxtImg = new JPanel();
		panelGameTxtImg.setBounds(25, 25, 544, 550);
		add(panelGameTxtImg);
		panelGameTxtImg.setLayout(null);

		MultiLineLabel lblTxtQuestionTxtImg = new MultiLineLabel("Testo domanda");
		lblTxtQuestionTxtImg.setHorizontalTextAlignment(MultiLineLabel.CENTER);
		lblTxtQuestionTxtImg.setBounds(25, 5, 494, 70);
		lblTxtQuestionTxtImg.setFont(frutiger18);
		lblTxtQuestionTxtImg.setForeground(myYellow);
		panelGameTxtImg.add(lblTxtQuestionTxtImg);

		JLabel btnOptionImg1TxtImg = new JLabel("OptionImg1");
		btnOptionImg1TxtImg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOptionImg1TxtImg.setBounds(25, 100, 200, 200);
		panelGameTxtImg.add(btnOptionImg1TxtImg);

		JLabel btnOptionImg2TxtImg = new JLabel("OptionImg2");
		btnOptionImg2TxtImg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOptionImg2TxtImg.setBounds(319, 100, 200, 200);
		panelGameTxtImg.add(btnOptionImg2TxtImg);

		JLabel btnOptionImg3TxtImg = new JLabel("OptionImg3");
		btnOptionImg3TxtImg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOptionImg3TxtImg.setBounds(25, 325, 200, 200);
		panelGameTxtImg.add(btnOptionImg3TxtImg);

		JLabel btnOptionImg4TxtImg = new JLabel("OptionImg4");
		btnOptionImg4TxtImg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOptionImg4TxtImg.setBounds(319, 325, 200, 200);
		panelGameTxtImg.add(btnOptionImg4TxtImg);

		JPanel panelGameImgTxt = new JPanel();
		panelGameImgTxt.setBounds(25, 25, 544, 550);
		add(panelGameImgTxt);
		panelGameImgTxt.setLayout(null);

		MultiLineLabel lblTxtQuestionImgTxt = new MultiLineLabel("Testo domanda");
		lblTxtQuestionImgTxt.setHorizontalTextAlignment(MultiLineLabel.CENTER);
		lblTxtQuestionImgTxt.setBounds(25, 5, 494, 70);
		lblTxtQuestionImgTxt.setFont(frutiger18);
		lblTxtQuestionImgTxt.setForeground(myYellow);
		panelGameImgTxt.add(lblTxtQuestionImgTxt);

		JLabel lblImgQuestionImgTxt = new JLabel("Immagine domanda");
		lblImgQuestionImgTxt.setBounds(172, 80, 200, 200);
		panelGameImgTxt.add(lblImgQuestionImgTxt);

		JLabel btnOptionTxt1ImgTxt = new JLabel("OptionTxt1");
		btnOptionTxt1ImgTxt.setBounds(25, 310, 494, 35);
		btnOptionTxt1ImgTxt.setFont(frutiger18);
		btnOptionTxt1ImgTxt.setForeground(myYellow);
		btnOptionTxt1ImgTxt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOptionTxt1ImgTxt.setIcon(optionIcon2);
		btnOptionTxt1ImgTxt.setHorizontalTextPosition(JLabel.CENTER);
		panelGameImgTxt.add(btnOptionTxt1ImgTxt);

		JLabel btnOptionTxt2ImgTxt = new JLabel("OptionTxt2");
		btnOptionTxt2ImgTxt.setBounds(25, 370, 494, 35);
		btnOptionTxt2ImgTxt.setFont(frutiger18);
		btnOptionTxt2ImgTxt.setForeground(myYellow);
		btnOptionTxt2ImgTxt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOptionTxt2ImgTxt.setIcon(optionIcon2);
		btnOptionTxt2ImgTxt.setHorizontalTextPosition(JLabel.CENTER);
		panelGameImgTxt.add(btnOptionTxt2ImgTxt);

		JLabel btnOptionTxt3ImgTxt = new JLabel("OptionTxt3");
		btnOptionTxt3ImgTxt.setBounds(25, 430, 494, 35);
		btnOptionTxt3ImgTxt.setFont(frutiger18);
		btnOptionTxt3ImgTxt.setForeground(myYellow);
		btnOptionTxt3ImgTxt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOptionTxt3ImgTxt.setIcon(optionIcon2);
		btnOptionTxt3ImgTxt.setHorizontalTextPosition(JLabel.CENTER);
		panelGameImgTxt.add(btnOptionTxt3ImgTxt);

		JLabel btnOptionTxt4ImgTxt = new JLabel("OptionTxt4");
		btnOptionTxt4ImgTxt.setBounds(25, 490, 494, 35);
		btnOptionTxt4ImgTxt.setFont(frutiger18);
		btnOptionTxt4ImgTxt.setForeground(myYellow);
		btnOptionTxt4ImgTxt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOptionTxt4ImgTxt.setIcon(optionIcon2);
		btnOptionTxt4ImgTxt.setHorizontalTextPosition(JLabel.CENTER);
		panelGameImgTxt.add(btnOptionTxt4ImgTxt);

		JPanel panelGameImgImg = new JPanel();
		panelGameImgImg.setBounds(25, 25, 544, 550);
		add(panelGameImgImg);
		panelGameImgImg.setLayout(null);

		MultiLineLabel lblTxtQuestionImgImg = new MultiLineLabel("Testo domanda");
		lblTxtQuestionImgImg.setHorizontalTextAlignment(MultiLineLabel.CENTER);
		lblTxtQuestionImgImg.setBounds(25, 5, 494, 70);
		lblTxtQuestionImgImg.setFont(frutiger18);
		lblTxtQuestionImgImg.setForeground(myYellow);
		panelGameImgImg.add(lblTxtQuestionImgImg);

		JLabel lblImgQuestionImgImg = new JLabel("Immagine domanda");
		lblImgQuestionImgImg.setBounds(200, 87, 150, 150);
		panelGameImgImg.add(lblImgQuestionImgImg);

		JLabel btnOptionImg1ImgImg = new JLabel("OptionImg1");
		btnOptionImg1ImgImg.setBounds(91, 255, 135, 135);
		btnOptionImg1ImgImg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelGameImgImg.add(btnOptionImg1ImgImg);

		JLabel btnOptionImg2ImgImg = new JLabel("OptionImg2");
		btnOptionImg2ImgImg.setBounds(317, 255, 135, 135);
		btnOptionImg2ImgImg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelGameImgImg.add(btnOptionImg2ImgImg);

		JLabel btnOptionImg3ImgImg = new JLabel("OptionImg3");
		btnOptionImg3ImgImg.setBounds(91, 404, 135, 135);
		btnOptionImg3ImgImg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelGameImgImg.add(btnOptionImg3ImgImg);

		JLabel btnOptionImg4ImgImg = new JLabel("OptionImg4");
		btnOptionImg4ImgImg.setBounds(317, 404, 135, 135);
		btnOptionImg4ImgImg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelGameImgImg.add(btnOptionImg4ImgImg);

		panelGameTxtTxt.setOpaque(false);
		
		MultiLineLabel labelTips = new MultiLineLabel("Testo domanda");
		labelTips.setVisible(false);
		labelTips.setText("Clicca col tasto destro sull'opzione per alprire la pagina web sul tuo browser");
		labelTips.setHorizontalTextAlignment(0);
		labelTips.setForeground(UIGraphicalUtils.getMyBlue());
		labelTips.setFont(frutiger18);
		labelTips.setBounds(10, 479, 509, 60);
		if(q.getOptions().get(0).getText()!=null){
		if(isUrl(q.getOptions().get(0).getText())||isUrl(q.getOptions().get(1).getText())||isUrl(q.getOptions().get(2).getText())||isUrl(q.getOptions().get(3).getText())){
			labelTips.setVisible(true);
		}}
		panelGameTxtTxt.add(labelTips);
		
		
		
		
		panelGameImgImg.setOpaque(false);
		panelGameImgTxt.setOpaque(false);
		panelGameTxtImg.setOpaque(false);
		panelGameImgImg.setVisible(false);
		panelGameImgTxt.setVisible(false);
		panelGameTxtImg.setVisible(false);
		panelGameTxtTxt.setVisible(false);

		Object[] dialog = { "Gioca ancora", "Torna alla home" };

		JLabel lblLevel = new JLabel("Il tuo livello:");
		lblLevel.setFont(frutiger18);
		lblLevel.setForeground(myYellow);
		lblLevel.setBounds(606, 159, 184, 29);
		add(lblLevel);

		JLabel lblScore = new JLabel("Il tuo punteggio:");
		lblScore.setFont(frutiger18);
		lblScore.setForeground(myYellow);
		lblScore.setBounds(606, 242, 184, 29);
		add(lblScore);

		JLabel lblYourScore = new JLabel(String.valueOf(p.getScore()));
		lblYourScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourScore.setFont(frutiger18);
		lblYourScore.setForeground(myYellow);

		lblYourScore.setBounds(606, 282, 100, 32);
		add(lblYourScore);

		JLabel lblYourLevel = new JLabel(String.valueOf(p.getLevel().toInt()));
		lblYourLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourLevel.setFont(frutiger18);
		lblYourLevel.setForeground(myYellow);
		lblYourLevel.setBounds(606, 188, 100, 32);
		add(lblYourLevel);

		JLabel lblSequence = new JLabel("<html></html>");
		lblSequence.setFont(UIGraphicalUtils.loadFrutigerFont(15f));
		lblSequence.setForeground(myYellow);
		lblSequence.setBounds(595, 314, 195, 100);
		String content = "";
		//show tips about next level
		if (p.getLevel().equals(Level.TWO)) {
			switch (p.getCorrectSequence()) {
			case 1: {
				content = "<html>Rispondi correttamente ad <br> altre 2 domande per <br> salire di livello!!!</html>";
				break;
			}
			case 2: {
				content = "<html>Rispondi correttamente ad <br> un'altra domanda per <br>salire di livello!!!</html>";
				break;
			}
			case -1: {
				content = "<html>Sbagliando altre <br> 4 domande <br>scenderai di livello!!!</html>";
				break;
			}
			case -2: {
				content = "<html>Sbagliando altre <br> 3 domande <br>scenderai di livello!!!</html>";
				break;
			}
			case -3: {
				content = "<html>Sbagliando altre <br> 2 domande <br>scenderai di livello!!!</html>";
				break;
			}
			case -4: {
				content = "<html>Sbagliando <br>un'altra domanda<br> scenderai di livello!!!</html>";
				break;
			}
			default: {
				content = "<html></html>";
				break;
			}
			}
		} else if(p.getLevel()==Level.THREE){
			switch (p.getCorrectSequence()) {
			case -1: {
				content = "<html>Sbagliando altre <br> 4 domande scenderai di livello!!</html>!";
				break;
			}
			case -2: {
				content = "<html>Sbagliando altre <br> 3 domande scenderai di livello!!!</html>";
				break;
			}
			case -3: {
				content = "<html>Sbagliando altre <br> 2 domande scenderai di livello!!!</html>";
				break;
			}
			case -4: {
				content = "<html>Sbagliando <br> un'altra domanda scenderai di livello!!!</html>";
				break;
			}
			default: {
				content = "<html></html>";
				break;
			}
			}

		}else{
			switch (p.getCorrectSequence()) {
			case 1: {
				content = "<html>Rispondi correttamente ad <br> altre 2 domande per <br> salire di livello!!!</html>";
				break;
			}
			case 2: {
				content = "<html>Rispondi correttamente ad <br> un'altra domanda per <br>salire di livello!!!</html>";
				break;
			}
			default:{
				content = "<html></html>";
				break;
			}
			}
			
		}
		lblSequence.setText(content);
		repaint();
		add(lblSequence);

		JLabel lblCategory = new JLabel("category");
		lblCategory.setToolTipText("Categoria della domanda");
		lblCategory.setBounds(630, 425, 110, 110);
		add(lblCategory);
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//same as in paint(...) at top page
				currentTime++;
				int fractionedTime=(int) Math.ceil(maxTime/3);
				if(currentTime>=fractionedTime && currentTime<((maxTime-fractionedTime))){
					lblTime.setForeground(Color.ORANGE);
				}else if(currentTime>=((maxTime-fractionedTime))){
					lblTime.setForeground(Color.RED);
				}else
					lblTime.setForeground(myGreen);
				revalidate();
				repaint();
				Integer elapsedTime = new Integer(maxTime - currentTime);
				lblTime.setText(elapsedTime.toString());
				if (elapsedTime == 0) {
					//time available to answer the question is over, mark answer as wrong
					try {
						GameHandlerInterface gh = RemoteHandlers.getInstance().getGameHandler();
						PlayerHandlerInterface ph = RemoteHandlers.getInstance().getPlayerHandler();
						gh.answerQuestion(q, q.getOptions().get(0), p);
						setUpdatedPlayer(ph.getUpdatedPlayer(p));

						p.setCorrectSequence(updatedPlayer.getCorrectSequence());
						p.setLevel(updatedPlayer.getLevel());
						p.setScore(updatedPlayer.getScore());

						int n = JOptionPane.showOptionDialog(contentPane, "Tempo scaduto!\r\nVuoi giocare ancora? ",
								"Tempo scaduto!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
								new ImageIcon(this.getClass().getResource("/outOfTime.png")), dialog, dialog[0]);
						System.out.println(n);
						if (n == 0) {
							Question q = null;

							q = gh.getQuestion(p);

							if (q != null) { //there's another question available
								new Game(contentPane, p, q);
								setVisible(false);
							} else { //no more question
								JOptionPane.showMessageDialog(contentPane,
										"Hai esaurito le domande a disposizione nel sistema!\r\nTorna piï¿½ avanti!");
								new HomePlayer(contentPane, p);
								setVisible(false);
							}
						} else {
							new HomePlayer(contentPane, p);
							setVisible(false);
						}
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

			}
		});

		switch (q.getCategory()) {
		case ART: {
			lblCategory.setIcon(new ImageIcon(getClass().getResource("/art110.png")));
			break;
		}
		case ENTERTAINMENT: {
			lblCategory.setIcon(new ImageIcon(getClass().getResource("/entertainment110.png")));
			break;
		}
		case GEOGRAPHY: {
			lblCategory.setIcon(new ImageIcon(getClass().getResource("/geography110.png")));
			break;
		}
		case HISTORY: {
			lblCategory.setIcon(new ImageIcon(getClass().getResource("/history110.png")));
			break;
		}
		case SCIENCE: {
			lblCategory.setIcon(new ImageIcon(getClass().getResource("/science110.png")));
			break;
		}
		case SPORT: {
			lblCategory.setIcon(new ImageIcon(getClass().getResource("/sport110.png")));
			break;
		}
		}
		ArrayList<Option> o = q.getOptions();
		Collections.shuffle(o);
		
		//check the type of the question
		if (q.getImage() == null && q.getOptions().get(0).getImage() == null) {
			//TxtTxt
			panelGameTxtTxt.setVisible(true);
			lblTxtQuestionTxtTxt.setText(q.getText());
			btnOption1TxtTxt.setText(o.get(0).getText());
			btnOption2TxtTxt.setText(o.get(1).getText());
			btnOption3TxtTxt.setText(o.get(2).getText());
			btnOption4TxtTxt.setText(o.get(3).getText());
		} else if (q.getImage() != null && q.getOptions().get(0).getImage() == null) {
			//ImgTxt
			panelGameImgTxt.setVisible(true);
			lblTxtQuestionImgTxt.setText(q.getText());
			Image resizedImage = q.getImage().getImage().getScaledInstance(lblImgQuestionImgTxt.getWidth(),
					lblImgQuestionImgTxt.getHeight(), Image.SCALE_SMOOTH);
			lblImgQuestionImgTxt.setIcon(new ImageIcon(resizedImage));
			btnOptionTxt1ImgTxt.setText(o.get(0).getText());
			btnOptionTxt2ImgTxt.setText(o.get(1).getText());
			btnOptionTxt3ImgTxt.setText(o.get(2).getText());
			btnOptionTxt4ImgTxt.setText(o.get(3).getText());
		} else if (q.getImage() == null && q.getOptions().get(0).getImage() != null) {
			//TxtImg
			panelGameTxtImg.setVisible(true);
			lblTxtQuestionTxtImg.setText(q.getText());
			Image resizedImage = o.get(0).getImage().getImage().getScaledInstance(btnOptionImg1TxtImg.getWidth(),
					btnOptionImg1TxtImg.getHeight(), Image.SCALE_SMOOTH);
			btnOptionImg1TxtImg.setIcon(new ImageIcon(resizedImage));
			resizedImage = o.get(1).getImage().getImage().getScaledInstance(btnOptionImg2TxtImg.getWidth(),
					btnOptionImg2TxtImg.getHeight(), Image.SCALE_SMOOTH);
			btnOptionImg2TxtImg.setIcon(new ImageIcon(resizedImage));
			resizedImage = o.get(2).getImage().getImage().getScaledInstance(btnOptionImg3TxtImg.getWidth(),
					btnOptionImg3TxtImg.getHeight(), Image.SCALE_SMOOTH);
			btnOptionImg3TxtImg.setIcon(new ImageIcon(resizedImage));
			resizedImage = o.get(3).getImage().getImage().getScaledInstance(btnOptionImg4TxtImg.getWidth(),
					btnOptionImg4TxtImg.getHeight(), Image.SCALE_SMOOTH);
			btnOptionImg4TxtImg.setIcon(new ImageIcon(resizedImage));
		} else {
			//ImgImg
			panelGameImgImg.setVisible(true);
			lblTxtQuestionImgImg.setText(q.getText());
			Image resizedImage = q.getImage().getImage().getScaledInstance(lblImgQuestionImgImg.getWidth(),
					lblImgQuestionImgImg.getHeight(), Image.SCALE_SMOOTH);
			lblImgQuestionImgImg.setIcon(new ImageIcon(resizedImage));
			resizedImage = o.get(0).getImage().getImage().getScaledInstance(btnOptionImg1ImgImg.getWidth(),
					btnOptionImg1ImgImg.getHeight(), Image.SCALE_SMOOTH);
			btnOptionImg1ImgImg.setIcon(new ImageIcon(resizedImage));
			resizedImage = o.get(1).getImage().getImage().getScaledInstance(btnOptionImg2ImgImg.getWidth(),
					btnOptionImg2ImgImg.getHeight(), Image.SCALE_SMOOTH);
			btnOptionImg2ImgImg.setIcon(new ImageIcon(resizedImage));
			resizedImage = o.get(2).getImage().getImage().getScaledInstance(btnOptionImg3ImgImg.getWidth(),
					btnOptionImg3ImgImg.getHeight(), Image.SCALE_SMOOTH);
			btnOptionImg3ImgImg.setIcon(new ImageIcon(resizedImage));
			resizedImage = o.get(3).getImage().getImage().getScaledInstance(btnOptionImg4ImgImg.getWidth(),
					btnOptionImg4ImgImg.getHeight(), Image.SCALE_SMOOTH);
			btnOptionImg4ImgImg.setIcon(new ImageIcon(resizedImage));
		}
		timer.start();
		
		//right click on a label which is a link open the URL in the web browser (Only for txt txt)
		btnOption1TxtTxt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)){
				handleResponse(contentPane, q, o, p, 0);}
				else{
					String text=q.getOptions().get(0).getText().toString();
					if(isUrl(text)){
						try {
							openWebpage( new URI(text));
						} catch (URISyntaxException e1) {
							
						}
					}
				}
			}
		});
		btnOption2TxtTxt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)){
				handleResponse(contentPane, q, o, p, 1);}
				else{
					String text=q.getOptions().get(1).getText().toString();
					if(isUrl(text)){
						try {
							openWebpage( new URI(text));
						} catch (URISyntaxException e1) {
						
						}
					}
				}
			}
		});
		btnOption3TxtTxt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)){
				handleResponse(contentPane, q, o, p, 2);}
				else{
					String text=q.getOptions().get(2).getText().toString();
					if(isUrl(text)){
						try {
							openWebpage( new URI(text));
						} catch (URISyntaxException e1) {
							
						}
					}
				}
			}
		});
		btnOption4TxtTxt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)){
				handleResponse(contentPane, q, o, p, 3);}
				else{
					String text=q.getOptions().get(3).getText().toString();
					if(isUrl(text)){
						try {
							openWebpage( new URI(text));
						} catch (URISyntaxException e1) {
							
						}
					}
				}
			}
		});
		btnOptionTxt1ImgTxt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleResponse(contentPane, q, o, p, 0);
			}
		});
		btnOptionTxt2ImgTxt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleResponse(contentPane, q, o, p, 1);
			}
		});
		btnOptionTxt3ImgTxt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleResponse(contentPane, q, o, p, 2);
			}
		});
		btnOptionTxt4ImgTxt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleResponse(contentPane, q, o, p, 3);
			}
		});

		btnOptionImg1ImgImg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleResponse(contentPane, q, o, p, 0);
			}
		});

		btnOptionImg2ImgImg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleResponse(contentPane, q, o, p, 1);
			}
		});
		btnOptionImg3ImgImg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleResponse(contentPane, q, o, p, 2);
			}
		});
		btnOptionImg4ImgImg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleResponse(contentPane, q, o, p, 3);
			}
		});
		btnOptionImg1TxtImg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleResponse(contentPane, q, o, p, 0);
			}
		});
		btnOptionImg2TxtImg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleResponse(contentPane, q, o, p, 1);
			}
		});
		btnOptionImg3TxtImg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleResponse(contentPane, q, o, p, 2);
			}
		});
		btnOptionImg4TxtImg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleResponse(contentPane, q, o, p, 3);
			}
		});
	}
	
	/**
	 * Checks if is url using a regex.
	 *
	 * @param text the url 
	 * @return true, the text is a valid url
	 */
	private boolean isUrl(String text){
		String urlPattern="\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";		
		Pattern url = Pattern.compile(urlPattern);
		Matcher m =url.matcher(text);
		return m.matches();
		
	}
	
	/**
	 * Open webpage in browser.
	 *
	 * @param uri the uri to open in default browser
	 */
	public static void openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	        } catch (Exception e) {
	           
	        }
	    }
	}
	
	
	/**
	 * Handle the response given by the player.
	 *
	 * @param contentPane the content pane
	 * @param q the question
	 * @param o the option choosen 
	 * @param p the player who give the answer
	 * @param index the index
	 */
	private void handleResponse(JPanel contentPane, Question q, ArrayList<Option> o, Player p, int index) {
		Object[] dialog = { "Gioca ancora", "Torna alla home" };
		timer.stop();
		try {
			GameHandlerInterface gh = RemoteHandlers.getInstance().getGameHandler();
			PlayerHandlerInterface ph = RemoteHandlers.getInstance().getPlayerHandler();
			boolean answer = false;
			answer = gh.answerQuestion(q, o.get(index), p);
			setUpdatedPlayer(ph.getUpdatedPlayer(p));

			p.setCorrectSequence(updatedPlayer.getCorrectSequence());
			p.setLevel(updatedPlayer.getLevel());
			p.setScore(updatedPlayer.getScore());
			if (answer) {
				int n = JOptionPane.showOptionDialog(contentPane, "Risposta esatta", "Feedback",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(this.getClass().getResource("/correctAnswer.png")), dialog, dialog[0]);
				System.out.println(n);
				if (n == 0) {
					Question q1 = null;

					q1 = gh.getQuestion(p);

					if (q1 != null) {
						new Game(contentPane, p, q1);
						setVisible(false);
					} else {
						JOptionPane.showMessageDialog(contentPane,
								"Hai esaurito le domande a disposizione nel sistema!\r\nTorna più avanti!");
						new HomePlayer(contentPane, p);
						setVisible(false);
					}
				} else {
					new HomePlayer(contentPane, p);
					setVisible(false);
				}
			} else {
				int n = JOptionPane.showOptionDialog(contentPane, "Risposta errata", "Feedback",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(this.getClass().getResource("/wrongAnswer.png")), dialog, dialog[0]);
				System.out.println(n);
				if (n == 0) {
					Question q1 = null;

					q1 = gh.getQuestion(p);

					if (q1 != null) {
						new Game(contentPane, p, q1);
						setVisible(false);
					} else {
						JOptionPane.showMessageDialog(contentPane,
								"Hai esaurito le domande a disposizione nel sistema!\r\nTorna più avanti!");
						new HomePlayer(contentPane, p);
						setVisible(false);
					}
				} else {
					new HomePlayer(contentPane, p);
					setVisible(false);
				}
			}
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
}
