package quooz.client.gui.player;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedHashMap;
import javax.swing.JScrollPane;

import quooz.shared.DatabaseProblemException;
import quooz.shared.question.Question;
import quooz.shared.question.QuestionHandlerInterface;
import quooz.shared.system.GameHandlerInterface;
import quooz.client.gui.utility.RemoteHandlers;
import quooz.client.gui.utility.UIGraphicalUtils;
import quooz.client.gui.utility.ConnectionIssues;
import quooz.client.gui.utility.MultiLineLabel;
import quooz.shared.user.Player;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class CorrectAnswerHistory extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private LinkedHashMap<Integer, String> lhm;

	public LinkedHashMap<Integer, String> getLhm() {
		return lhm;
	}

	public void setLhm(LinkedHashMap<Integer, String> lhm) {
		this.lhm = lhm;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(UIGraphicalUtils.getBackground(), 0, 0, null);
	}

	@SuppressWarnings("unchecked")
	public CorrectAnswerHistory(JPanel contentPane, Player player) {
		setBounds(100, 100, 800, 600);
		setLayout(null);
		contentPane.add(this);
		Font frutiger16= UIGraphicalUtils.loadFrutigerFont(16f);
		Color myYellow = UIGraphicalUtils.getMyYellow();
		Color myBlue = UIGraphicalUtils.getMyBlue();
		JLabel lblElencoRisposteCorrette = new JLabel("Elenco risposte corrette");
		lblElencoRisposteCorrette.setForeground(myYellow);
		lblElencoRisposteCorrette.setFont(frutiger16);
		lblElencoRisposteCorrette.setBounds(42, 29, 192, 14);
		add(lblElencoRisposteCorrette);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(42, 86, 306, 445);
		add(scrollPane);

		@SuppressWarnings("rawtypes")
		JList list = new JList();
		list.setBackground(myBlue);
		list.setForeground(myYellow);
		scrollPane.setViewportView(list);
		scrollPane.setOpaque(false);
		DefaultListModel<String> dml = new DefaultListModel<String>();
		try {
			GameHandlerInterface gh = RemoteHandlers.getInstance().getGameHandler();
			setLhm(gh.getCorrectAnswerHistory(player));
			//load question list
			for (int i = 0; i < lhm.size(); i++) {
				dml.addElement(lhm.get(lhm.keySet().toArray()[i]));
			}
			list.setModel(dml);
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

		JLabel lblPreviousPage = new JLabel(" INDIETRO");
		lblPreviousPage.setBounds(636, 10, 154, 58);
		lblPreviousPage.setFont(UIGraphicalUtils.loadPractiqueFont(33f));
		lblPreviousPage.setForeground(UIGraphicalUtils.getMyYellow());
		lblPreviousPage.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblPreviousPage.setIcon(new ImageIcon(this.getClass().getResource("/left_yellow.png")));
		lblPreviousPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new StatsPlayer(contentPane, player);
				setVisible(false);
			}
		});
		add(lblPreviousPage);

		JPanel panelImgTxt = new JPanel();
		panelImgTxt.setBounds(410, 86, 306, 445);
		add(panelImgTxt);
		panelImgTxt.setLayout(null);

		MultiLineLabel lblTxtQuestionImgTxt = new MultiLineLabel("Testo Domanda");
		lblTxtQuestionImgTxt.setBounds(6, 6, 294, 34);
		lblTxtQuestionImgTxt.setForeground(myYellow);
		lblTxtQuestionImgTxt.setFont(frutiger16);
		panelImgTxt.add(lblTxtQuestionImgTxt);

		JLabel lblImgQuestionImgTxt = new JLabel("Immagine Domanda");
		lblImgQuestionImgTxt.setBounds(71, 52, 180, 147);
		lblImgQuestionImgTxt.setForeground(myYellow);
		lblImgQuestionImgTxt.setFont(frutiger16);
		panelImgTxt.add(lblImgQuestionImgTxt);

		JLabel lblRisposta = new JLabel("Risposta:");
		lblRisposta.setBounds(6, 211, 199, 16);
		lblRisposta.setFont(frutiger16);
		lblRisposta.setForeground(myYellow);
		panelImgTxt.add(lblRisposta);

		JLabel lblOptionImgTxt = new JLabel("Opzione");
		lblOptionImgTxt.setBounds(6, 283, 294, 34);
		lblOptionImgTxt.setFont(frutiger16);
		lblOptionImgTxt.setForeground(myYellow);
		panelImgTxt.add(lblOptionImgTxt);

		JPanel panelImgImg = new JPanel();
		panelImgImg.setBounds(410, 86, 306, 445);
		add(panelImgImg);
		panelImgImg.setLayout(null);

		MultiLineLabel lblTxtQuestionImgImg = new MultiLineLabel("Testo Domanda");
		lblTxtQuestionImgImg.setBounds(6, 6, 294, 37);
		lblTxtQuestionImgImg.setFont(frutiger16);
		lblTxtQuestionImgImg.setForeground(myYellow);
		panelImgImg.add(lblTxtQuestionImgImg);

		JLabel lblImgQuestionImgImg = new JLabel("Immagine domanda");
		lblImgQuestionImgImg.setBounds(55, 55, 198, 156);
		lblImgQuestionImgImg.setFont(frutiger16);
		lblImgQuestionImgImg.setForeground(myYellow);
		panelImgImg.add(lblImgQuestionImgImg);

		JLabel lblRisposta_3 = new JLabel("Risposta:");
		lblRisposta_3.setBounds(6, 223, 162, 16);
		lblRisposta_3.setFont(frutiger16);
		lblRisposta_3.setForeground(myYellow);
		panelImgImg.add(lblRisposta_3);

		JLabel lblOptionImgImg = new JLabel("Domanda Opzione");
		lblOptionImgImg.setBounds(55, 251, 198, 156);
		lblOptionImgImg.setFont(frutiger16);
		lblOptionImgImg.setForeground(myYellow);
		panelImgImg.add(lblOptionImgImg);

		JPanel panelTxtTxt = new JPanel();
		panelTxtTxt.setBounds(410, 86, 306, 445);
		add(panelTxtTxt);
		panelTxtTxt.setLayout(null);

		MultiLineLabel lblTxtQuestionTxtTxt = new MultiLineLabel("Testo domanda");
		lblTxtQuestionTxtTxt.setBounds(6, 6, 294, 34);
		lblTxtQuestionTxtTxt.setFont(frutiger16);
		lblTxtQuestionTxtTxt.setForeground(myYellow);
		panelTxtTxt.add(lblTxtQuestionTxtTxt);

		JLabel lblOptionTxtTxt = new JLabel("Correct Option");
		lblOptionTxtTxt.setBounds(6, 169, 294, 27);
		lblOptionTxtTxt.setFont(frutiger16);
		lblOptionTxtTxt.setForeground(myYellow);
		panelTxtTxt.add(lblOptionTxtTxt);

		JLabel lblRisposta_2 = new JLabel("Risposta:");
		lblRisposta_2.setBounds(6, 91, 160, 16);
		lblRisposta_2.setFont(frutiger16);
		lblRisposta_2.setForeground(myYellow);
		panelTxtTxt.add(lblRisposta_2);

		JPanel panelTxtImg = new JPanel();
		panelTxtImg.setBounds(410, 86, 306, 445);
		add(panelTxtImg);
		panelTxtImg.setLayout(null);

		MultiLineLabel lblTxtQuestionTxtImg = new MultiLineLabel("Testo Domdanda");
		lblTxtQuestionTxtImg.setBounds(6, 5, 294, 35);
		lblTxtQuestionTxtImg.setFont(frutiger16);
		lblTxtQuestionTxtImg.setForeground(myYellow);
		panelTxtImg.add(lblTxtQuestionTxtImg);

		JLabel lblOptionTxtImg = new JLabel("Option Image");
		lblOptionTxtImg.setBounds(62, 157, 190, 189);
		lblOptionTxtImg.setFont(frutiger16);
		lblOptionTxtImg.setForeground(myYellow);
		panelTxtImg.add(lblOptionTxtImg);

		JLabel lblRisposta_1 = new JLabel("Risposta:");
		lblRisposta_1.setBounds(6, 90, 230, 16);
		lblRisposta_1.setFont(frutiger16);
		lblRisposta_1.setForeground(myYellow);
		panelTxtImg.add(lblRisposta_1);

		panelImgImg.setVisible(false);
		panelImgTxt.setVisible(false);
		panelTxtImg.setVisible(false);
		panelTxtTxt.setVisible(false);
		panelImgImg.setOpaque(false);
		panelImgTxt.setOpaque(false);
		panelTxtImg.setOpaque(false);
		panelTxtTxt.setOpaque(false);

		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {

				@SuppressWarnings("rawtypes")
				//show selected question each mouse click on the list
				JList list1 = (JList) evt.getSource();
				//get selected index
				int index = list1.locationToIndex(evt.getPoint());
				int lab = (int) lhm.keySet().toArray()[index];
				System.out.println(lab);
				Question question = null;
				try {
					QuestionHandlerInterface qh = RemoteHandlers.getInstance().getQuestionHandler();

					question = qh.getQuestionById(lab);

					if (question.getImage() == null && question.getOptions().get(0).getImage() == null) {
						//case txt txt question
						panelImgImg.setVisible(false);
						panelImgTxt.setVisible(false);
						panelTxtTxt.setVisible(true);
						panelTxtImg.setVisible(false);
						lblTxtQuestionTxtTxt.setText(question.getText());
						lblOptionTxtTxt.setText(question.getOptions().get(3).getText());
					} else if (question.getImage() != null && question.getOptions().get(0).getImage() != null) {
						//case Img Img question
						panelImgImg.setVisible(true);
						panelImgTxt.setVisible(false);
						panelTxtTxt.setVisible(false);
						panelTxtImg.setVisible(false);
						lblTxtQuestionImgImg.setText(question.getText());

						lblImgQuestionImgImg.setIcon(new ImageIcon(
								question.getImage().getImage().getScaledInstance(lblImgQuestionImgImg.getWidth(),
										lblImgQuestionImgImg.getHeight(), Image.SCALE_SMOOTH)));
						lblOptionImgImg.setIcon(
								new ImageIcon(question.getOptions().get(3).getImage().getImage().getScaledInstance(
										lblOptionImgImg.getWidth(), lblOptionImgImg.getWidth(), Image.SCALE_SMOOTH)));

					} else if (question.getImage() == null && question.getOptions().get(0).getImage() != null) {
						//case txt img question
						panelImgImg.setVisible(false);
						panelImgTxt.setVisible(false);
						panelTxtTxt.setVisible(false);
						panelTxtImg.setVisible(true);
						lblTxtQuestionTxtImg.setText(question.getText());
						lblOptionTxtImg.setIcon(
								new ImageIcon(question.getOptions().get(3).getImage().getImage().getScaledInstance(
										lblOptionImgImg.getWidth(), lblOptionImgImg.getWidth(), Image.SCALE_SMOOTH)));

					} else if (question.getImage() != null && question.getOptions().get(0).getImage() == null) {
						//case img txt question
						panelImgImg.setVisible(false);
						panelImgTxt.setVisible(true);
						panelTxtTxt.setVisible(false);
						panelTxtImg.setVisible(false);
						lblTxtQuestionImgTxt.setText(question.getText());
						lblImgQuestionImgTxt.setIcon(new ImageIcon(
								question.getImage().getImage().getScaledInstance(lblImgQuestionImgImg.getWidth(),
										lblImgQuestionImgImg.getHeight(), Image.SCALE_SMOOTH)));
						lblOptionImgTxt.setText(question.getOptions().get(3).getText());
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
		});

	}
}
