package quooz.client.gui.author;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import quooz.shared.DatabaseProblemException;
import quooz.shared.question.Category;
import quooz.shared.question.Level;
import quooz.shared.question.Question;
import quooz.shared.question.QuestionHandlerInterface;
import quooz.client.gui.utility.RemoteHandlers;
import quooz.client.gui.utility.UIGraphicalUtils;
import quooz.client.gui.utility.ConnectionIssues;
import quooz.client.gui.utility.FileChooser;
import quooz.shared.user.Author;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import javax.swing.SwingConstants;

public class EditQuestion extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultListModel<String> listModel;
	private JList<String> list;
	private JTextField textFieldTxtTxtCrct;
	private JTextField textFieldTxtTxtWrg1;
	private JTextField textFieldTxtTxtWrg2;
	private JTextField textFieldTxtTxtWrg3;
	private JTextField textFieldImgTxtText;
	private JTextField textFieldImgTxtCrct;
	private JTextField textFieldImgTxtWrg1;
	private JTextField textFieldImgTxtWrg2;
	private JTextField textFieldImgTxtWrg3;
	private JTextField textFieldImgImgText;
	private JTextField textFieldTxtTxtText;
	private JTextField textFieldTxtImgText;
	private final int maxQuestionLength = 139;
	private final int maxOptionLength = 59;
	private Question questionToEdit;
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

	public EditQuestion(JPanel contentPane, Author author) {
		setBounds(100, 100, 800, 600);
		setLayout(null);
		contentPane.add(this);
		setVisible(true);
		Font frutiger16 = UIGraphicalUtils.loadFrutigerFont(16f);
		Color myYellow = UIGraphicalUtils.getMyYellow();
		Color myBlue = UIGraphicalUtils.getMyBlue();
		ImageIcon edit = new ImageIcon(getClass().getResource("/Salvataggio.png"));
		ImageIcon delete = new ImageIcon(getClass().getResource("/Cancella.png"));
		listModel = new DefaultListModel<String>();
		JScrollPane listScrollPane = new JScrollPane();
		listScrollPane.setBounds(507, 102, 283, 469);
		setLayout(null);
		add(listScrollPane);

		list = new JList<String>(listModel);
		list.setBackground(myBlue);
		list.setForeground(myYellow);
		listScrollPane.setViewportView(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.setVisibleRowCount(5);
		
		try {
			QuestionHandlerInterface qh = RemoteHandlers.getInstance().getQuestionHandler();
			setLhm(qh.getQuestionList(author));
			// load quesition list into the data model
			for (int i = 0; i < lhm.size(); i++) {
				listModel.addElement(lhm.get(lhm.keySet().toArray()[i]));
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
			JOptionPane.showMessageDialog(contentPane,
					"Hai inserito un campo non valido per  il Database, ricontrolla!");
			e1.printStackTrace();
		}
		

		JLabel lblListaDomande = new JLabel("Clicca su una domanda per visualizzarla");
		lblListaDomande.setFont(frutiger16);
		lblListaDomande.setForeground(myYellow);
		lblListaDomande.setBounds(507, 71, 283, 23);
		add(lblListaDomande);

		JLabel lblPreviousPage = new JLabel(" INDIETRO");
		lblPreviousPage.setBounds(668, 11, 122, 58);
		lblPreviousPage.setFont(UIGraphicalUtils.loadPractiqueFont(33f));
		lblPreviousPage.setForeground(UIGraphicalUtils.getMyYellow());
		lblPreviousPage.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblPreviousPage.setIcon(new ImageIcon(this.getClass().getResource("/left_yellow.png")));
		lblPreviousPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				HomeAuthor.getInstance(contentPane, author);
				setVisible(false);
			}
		});
		add(lblPreviousPage);

		JPanel panelImgImg = new JPanel();
		panelImgImg.setOpaque(false);
		panelImgImg.setBounds(10, 11, 487, 560);
		add(panelImgImg);
		panelImgImg.setLayout(null);

		JLabel label_2 = new JLabel("Testo della domanda:");
		label_2.setBounds(10, 11, 470, 14);
		label_2.setFont(frutiger16);
		label_2.setForeground(myYellow);
		panelImgImg.add(label_2);

		textFieldImgImgText = new JTextField();
		textFieldImgImgText.setColumns(10);
		textFieldImgImgText.setBounds(10, 28, 467, 20);
		textFieldImgImgText.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textFieldImgImgText.getText().length() > maxQuestionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelImgImg.add(textFieldImgImgText);

		JLabel label_3 = new JLabel("Opzione corretta:");
		label_3.setBounds(10, 160, 230, 14);
		label_3.setFont(frutiger16);
		label_3.setForeground(myYellow);
		panelImgImg.add(label_3);

		JLabel label_4 = new JLabel("Opzione scorretta:");
		label_4.setBounds(247, 160, 230, 14);
		label_4.setFont(frutiger16);
		label_4.setForeground(myYellow);
		panelImgImg.add(label_4);

		JLabel label_5 = new JLabel("Opzione scorretta:");
		label_5.setBounds(10, 314, 142, 14);
		label_5.setFont(frutiger16);
		label_5.setForeground(myYellow);
		panelImgImg.add(label_5);

		JLabel label_6 = new JLabel("Opzione scorretta:");
		label_6.setBounds(247, 314, 142, 14);
		label_6.setFont(frutiger16);
		label_6.setForeground(myYellow);
		panelImgImg.add(label_6);

		JLabel lblPercorsoImmagine = new JLabel("Percorso immagine:");
		lblPercorsoImmagine.setBounds(10, 59, 191, 16);
		lblPercorsoImmagine.setFont(frutiger16);
		lblPercorsoImmagine.setForeground(myYellow);
		panelImgImg.add(lblPercorsoImmagine);

		FileChooser fileChooserImgImgQImg = new FileChooser();
		fileChooserImgImgQImg.setBounds(325, 95, 130, 27);
		panelImgImg.add(fileChooserImgImgQImg);

		JLabel btnImgImgElimina = new JLabel("");
		btnImgImgElimina.setBorder(null);
		btnImgImgElimina.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnImgImgElimina.setIcon(delete);

		btnImgImgElimina.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Object[] dialog = { "Elimina", "Annulla" };
				int n = JOptionPane.showOptionDialog(contentPane, "Stai per eliminare una domanda, sei sicuro?",
						"Feedback", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, dialog, dialog[0]);
				if (n == 0) {
					try {
						RemoteHandlers.getInstance().getQuestionHandler().deleteQuestion(getQuestionToEdit().getId(),
								author);
						JOptionPane.showMessageDialog(contentPane, "Domanda eliminata!");
						// HomeAuthor.getInstance(contentPane, author);
						new EditQuestion(contentPane, author);
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
						JOptionPane.showMessageDialog(contentPane,
								"Hai inserito un campo non valido per  il Database, ricontrolla!");
						e1.printStackTrace();
					}
				}
			}
		});
		btnImgImgElimina.setBounds(346, 499, 91, 50);
		panelImgImg.add(btnImgImgElimina);

		JComboBox<String> comboBoxImgImgCategory = new JComboBox<String>();
		comboBoxImgImgCategory.setBounds(71, 468, 250, 20);
		addCategories(comboBoxImgImgCategory);
		panelImgImg.add(comboBoxImgImgCategory);

		JComboBox<Integer> comboBoxImgImgLevel = new JComboBox<Integer>();
		comboBoxImgImgLevel.setBounds(427, 468, 50, 20);
		addLevels(comboBoxImgImgLevel);
		panelImgImg.add(comboBoxImgImgLevel);

		FileChooser fileChooserImgImgCrt = new FileChooser();
		fileChooserImgImgCrt.setBounds(10, 281, 130, 27);
		panelImgImg.add(fileChooserImgImgCrt);

		FileChooser fileChooserImgImgWrg1 = new FileChooser();
		fileChooserImgImgWrg1.setBounds(247, 281, 130, 27);
		panelImgImg.add(fileChooserImgImgWrg1);

		FileChooser fileChooserImgImgWrg2 = new FileChooser();
		fileChooserImgImgWrg2.setBounds(10, 435, 130, 27);
		panelImgImg.add(fileChooserImgImgWrg2);

		FileChooser fileChooserImgImgWrg3 = new FileChooser();
		fileChooserImgImgWrg3.setBounds(247, 435, 130, 27);
		panelImgImg.add(fileChooserImgImgWrg3);

		JLabel btnImgImgModifica = new JLabel("Modifica");
		btnImgImgModifica.setIcon(edit);
		btnImgImgModifica.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnImgImgModifica.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					QuestionHandlerInterface qh = RemoteHandlers.getInstance().getQuestionHandler();
					Question question = getQuestionToEdit();
					LinkedHashMap<Integer, File> optionsFile = new LinkedHashMap<>();
					File image = fileChooserImgImgQImg.getFile();
					//
					if (fileChooserImgImgCrt.getFile() != null) {
						optionsFile.put(question.getOptions().get(3).getId(), fileChooserImgImgCrt.getFile());
					}
					if (fileChooserImgImgWrg1.getFile() != null) {
						optionsFile.put(question.getOptions().get(0).getId(), fileChooserImgImgWrg1.getFile());
					}
					if (fileChooserImgImgWrg2.getFile() != null) {
						optionsFile.put(question.getOptions().get(1).getId(), fileChooserImgImgWrg2.getFile());
					}
					if (fileChooserImgImgWrg3.getFile() != null) {
						optionsFile.put(question.getOptions().get(2).getId(), fileChooserImgImgWrg3.getFile());
					}
					Level level = Level.getLevelFromInt((int) comboBoxImgImgLevel.getSelectedItem());
					Category category = Category
							.getCategoryFromIta(comboBoxImgImgCategory.getSelectedItem().toString());
					qh.editQuestion(question.getId(), textFieldImgImgText.getText(), level, category, null, optionsFile,
							author, image, true);
					JOptionPane.showMessageDialog(contentPane, "Domanda modificata!");
					// HomeAuthor.getInstance(contentPane, author);
					new EditQuestion(contentPane, author);
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
					JOptionPane.showMessageDialog(contentPane,
							"Hai inserito un campo non valido per  il Database, ricontrolla!");
					e1.printStackTrace();
				}

			}
		});
		btnImgImgModifica.setBounds(50, 499, 91, 50);
		panelImgImg.add(btnImgImgModifica);

		JLabel label_8 = new JLabel("Categoria:");
		label_8.setFont(frutiger16);
		label_8.setForeground(myYellow);
		label_8.setBounds(10, 471, 68, 16);
		panelImgImg.add(label_8);

		JLabel lblLivello_2 = new JLabel("Livello:");
		lblLivello_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblLivello_2.setFont(frutiger16);
		lblLivello_2.setForeground(myYellow);
		lblLivello_2.setBounds(325, 471, 90, 14);
		panelImgImg.add(lblLivello_2);

		JLabel lblImgImgQImg = new JLabel("New label");
		lblImgImgQImg.setBounds(201, 64, 85, 85);
		panelImgImg.add(lblImgImgQImg);

		JLabel lblImgImgCrct = new JLabel("New label");
		lblImgImgCrct.setBounds(10, 185, 85, 85);
		panelImgImg.add(lblImgImgCrct);

		JLabel lblImgImgWrg2 = new JLabel("New label");
		lblImgImgWrg2.setBounds(10, 339, 85, 85);
		panelImgImg.add(lblImgImgWrg2);

		JLabel lblImgImgWrg3 = new JLabel("New label");
		lblImgImgWrg3.setBounds(247, 339, 85, 85);
		panelImgImg.add(lblImgImgWrg3);

		JLabel lblImgImgWrg1 = new JLabel("New label");
		lblImgImgWrg1.setBounds(247, 185, 85, 85);
		panelImgImg.add(lblImgImgWrg1);

		panelImgImg.setVisible(false);

		JPanel panelImgTxt = new JPanel();
		panelImgTxt.setOpaque(false);
		panelImgTxt.setBounds(10, 10, 487, 560);

		add(panelImgTxt);
		panelImgTxt.setLayout(null);

		JLabel lblQuestionTxt = new JLabel("Testo della domanda:");
		lblQuestionTxt.setBounds(10, 11, 467, 14);
		lblQuestionTxt.setFont(frutiger16);
		lblQuestionTxt.setForeground(myYellow);
		panelImgTxt.add(lblQuestionTxt);

		textFieldImgTxtText = new JTextField();
		textFieldImgTxtText.setColumns(10);
		textFieldImgTxtText.setBounds(10, 36, 467, 40);
		textFieldImgTxtText.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textFieldImgTxtText.getText().length() > maxQuestionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelImgTxt.add(textFieldImgTxtText);

		JLabel lblCrctAns = new JLabel("Opzione corretta:");
		lblCrctAns.setBounds(10, 228, 210, 14);
		lblCrctAns.setFont(frutiger16);
		lblCrctAns.setForeground(myYellow);
		panelImgTxt.add(lblCrctAns);

		textFieldImgTxtCrct = new JTextField();
		textFieldImgTxtCrct.setColumns(10);
		textFieldImgTxtCrct.setBounds(10, 253, 467, 25);
		textFieldImgTxtCrct.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textFieldImgTxtCrct.getText().length() > maxOptionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelImgTxt.add(textFieldImgTxtCrct);

		JLabel lblWrgOpt12 = new JLabel("Opzione scorretta:");
		lblWrgOpt12.setBounds(10, 289, 210, 14);
		lblWrgOpt12.setFont(frutiger16);
		lblWrgOpt12.setForeground(myYellow);
		panelImgTxt.add(lblWrgOpt12);

		textFieldImgTxtWrg1 = new JTextField();
		textFieldImgTxtWrg1.setColumns(10);
		textFieldImgTxtWrg1.setBounds(10, 314, 467, 25);
		textFieldImgTxtWrg1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textFieldImgTxtWrg1.getText().length() > maxOptionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelImgTxt.add(textFieldImgTxtWrg1);

		JLabel lblWrgOpt22 = new JLabel("Opzione scorretta:");
		lblWrgOpt22.setBounds(10, 350, 210, 14);
		lblWrgOpt22.setFont(frutiger16);
		lblWrgOpt22.setForeground(myYellow);
		panelImgTxt.add(lblWrgOpt22);

		JLabel lblWrgOpt32 = new JLabel("Opzione scorretta:");
		lblWrgOpt32.setBounds(10, 411, 210, 14);
		lblWrgOpt32.setFont(frutiger16);
		lblWrgOpt32.setForeground(myYellow);
		panelImgTxt.add(lblWrgOpt32);

		textFieldImgTxtWrg2 = new JTextField();
		textFieldImgTxtWrg2.setColumns(10);
		textFieldImgTxtWrg2.setBounds(10, 375, 467, 25);
		textFieldImgTxtWrg2.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textFieldImgTxtWrg2.getText().length() > maxOptionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelImgTxt.add(textFieldImgTxtWrg2);

		textFieldImgTxtWrg3 = new JTextField();
		textFieldImgTxtWrg3.setColumns(10);
		textFieldImgTxtWrg3.setBounds(10, 436, 467, 25);
		textFieldImgTxtWrg3.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textFieldImgTxtWrg3.getText().length() > maxOptionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelImgTxt.add(textFieldImgTxtWrg3);

		JLabel lblImagePath = new JLabel("Percorso immagine:");
		lblImagePath.setBounds(10, 87, 146, 16);
		lblImagePath.setFont(frutiger16);
		lblImagePath.setForeground(myYellow);
		panelImgTxt.add(lblImagePath);

		FileChooser fileChooserImgTxtQImg = new FileChooser();
		fileChooserImgTxtQImg.setBounds(343, 141, 130, 27);
		panelImgTxt.add(fileChooserImgTxtQImg);

		JLabel btnImgTxtElimina = new JLabel("");
		btnImgTxtElimina.setBorder(null);
		btnImgTxtElimina.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnImgTxtElimina.setIcon(delete);
		btnImgTxtElimina.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Object[] dialog = { "Elimina", "Annulla" };
				int n = JOptionPane.showOptionDialog(contentPane, "Stai per eliminare una domanda, sei sicuro?",
						"Feedback", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, dialog, dialog[0]);
				if (n == 0) {
					try {
						RemoteHandlers.getInstance().getQuestionHandler().deleteQuestion(getQuestionToEdit().getId(),
								author);
						JOptionPane.showMessageDialog(contentPane, "Domanda eliminata!");
						// HomeAuthor.getInstance(contentPane, author);
						new EditQuestion(contentPane, author);
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
						JOptionPane.showMessageDialog(contentPane,
								"Hai inserito un campo non valido per  il Database, ricontrolla!");
						e1.printStackTrace();
					}
				}
			}
		});
		btnImgTxtElimina.setBounds(346, 499, 91, 50);
		panelImgTxt.add(btnImgTxtElimina);

		JComboBox<String> comboBoxImgTxtCategory = new JComboBox<String>();
		comboBoxImgTxtCategory.setBounds(71, 474, 250, 20);
		addCategories(comboBoxImgTxtCategory);
		panelImgTxt.add(comboBoxImgTxtCategory);

		JComboBox<Integer> comboBoxImgTxtLevel = new JComboBox<Integer>();
		comboBoxImgTxtLevel.setBounds(427, 474, 50, 20);
		addLevels(comboBoxImgTxtLevel);
		panelImgTxt.add(comboBoxImgTxtLevel);

		JLabel btnImgTxtModifica = new JLabel("Modifica");
		btnImgTxtModifica.setIcon(edit);
		btnImgTxtModifica.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnImgTxtModifica.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Question question = getQuestionToEdit();
				try {

					QuestionHandlerInterface qh = RemoteHandlers.getInstance().getQuestionHandler();
					LinkedHashMap<Integer, String> opzioni = new LinkedHashMap<>();
					opzioni.put(question.getOptions().get(0).getId(), textFieldImgTxtWrg1.getText());
					opzioni.put(question.getOptions().get(1).getId(), textFieldImgTxtWrg2.getText());
					opzioni.put(question.getOptions().get(2).getId(), textFieldImgTxtWrg3.getText());
					opzioni.put(question.getOptions().get(3).getId(), textFieldImgTxtCrct.getText());
					File image = fileChooserImgTxtQImg.getFile();
					Level level = Level.getLevelFromInt((int) comboBoxImgImgLevel.getSelectedItem());
					Category category = Category
							.getCategoryFromIta(comboBoxImgImgCategory.getSelectedItem().toString());
					qh.editQuestion(question.getId(), textFieldImgTxtText.getText(), level, category, opzioni, null,
							author, image, false);

					JOptionPane.showMessageDialog(contentPane, "Domanda modificata!");
					// HomeAuthor.getInstance(contentPane, author);
					new EditQuestion(contentPane, author);
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
					JOptionPane.showMessageDialog(contentPane,
							"Hai inserito un campo non valido per  il Database, ricontrolla!");
					e1.printStackTrace();
				}

			}
		});
		btnImgTxtModifica.setBounds(50, 499, 91, 50);
		panelImgTxt.add(btnImgTxtModifica);

		JLabel label = new JLabel("Categoria:");
		label.setBounds(10, 475, 67, 16);
		label.setFont(frutiger16);
		label.setForeground(myYellow);
		panelImgTxt.add(label);

		JLabel lblLivello_1 = new JLabel("Livello:");
		lblLivello_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblLivello_1.setBounds(333, 476, 90, 14);
		lblLivello_1.setFont(frutiger16);
		lblLivello_1.setForeground(myYellow);
		panelImgTxt.add(lblLivello_1);

		JLabel lblImgTxTQImg = new JLabel("New label");
		lblImgTxTQImg.setBounds(166, 87, 130, 130);
		panelImgTxt.add(lblImgTxTQImg);
		panelImgTxt.setVisible(false);

		JPanel panelTxtImg = new JPanel();
		panelTxtImg.setOpaque(false);
		panelTxtImg.setLayout(null);
		panelTxtImg.setBounds(10, 11, 487, 560);
		add(panelTxtImg);

		JLabel label_10 = new JLabel("Testo della domanda:");
		label_10.setBounds(10, 11, 467, 14);
		label_10.setFont(frutiger16);
		label_10.setForeground(myYellow);
		panelTxtImg.add(label_10);

		textFieldTxtImgText = new JTextField();
		textFieldTxtImgText.setColumns(10);
		textFieldTxtImgText.setBounds(10, 28, 467, 40);
		textFieldTxtImgText.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textFieldTxtImgText.getText().length() > maxQuestionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelTxtImg.add(textFieldTxtImgText);

		JLabel label_11 = new JLabel("Opzione corretta:");
		label_11.setBounds(40, 79, 109, 14);
		label_11.setFont(frutiger16);
		label_11.setForeground(myYellow);
		panelTxtImg.add(label_11);

		JLabel label_12 = new JLabel("Opzione scorretta:");
		label_12.setBounds(308, 79, 121, 14);
		label_12.setFont(frutiger16);
		label_12.setForeground(myYellow);
		panelTxtImg.add(label_12);

		JLabel label_13 = new JLabel("Opzione scorretta:");
		label_13.setFont(frutiger16);
		label_13.setForeground(myYellow);
		label_13.setBounds(40, 281, 120, 14);
		panelTxtImg.add(label_13);

		JLabel label_14 = new JLabel("Opzione scorretta:");
		label_14.setBounds(308, 281, 121, 14);
		label_14.setFont(frutiger16);
		label_14.setForeground(myYellow);
		panelTxtImg.add(label_14);

		JLabel btnTxtImgElimina = new JLabel("");
		btnTxtImgElimina.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTxtImgElimina.setBorder(null);
		btnTxtImgElimina.setIcon(delete);
		btnTxtImgElimina.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Object[] dialog = { "Elimina", "Annulla" };
				int n = JOptionPane.showOptionDialog(contentPane, "Stai per eliminare una domanda, sei sicuro?",
						"Feedback", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, dialog, dialog[0]);
				if (n == 0) {
					try {
						RemoteHandlers.getInstance().getQuestionHandler().deleteQuestion(getQuestionToEdit().getId(),
								author);
						JOptionPane.showMessageDialog(contentPane, "Domanda eliminata!");
						// HomeAuthor.getInstance(contentPane, author);
						new EditQuestion(contentPane, author);
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
						JOptionPane.showMessageDialog(contentPane,
								"Hai inserito un campo non valido per  il Database, ricontrolla!");
						e1.printStackTrace();
					}
				}
			}
		});
		btnTxtImgElimina.setBounds(346, 499, 91, 50);
		panelTxtImg.add(btnTxtImgElimina);

		JComboBox<String> comboBoxTxtImgCategory = new JComboBox<String>();
		comboBoxTxtImgCategory.setBounds(92, 473, 215, 20);
		addCategories(comboBoxTxtImgCategory);

		JComboBox<Integer> comboBoxTxtImgLevel = new JComboBox<Integer>();
		comboBoxTxtImgLevel.setBounds(428, 473, 49, 20);
		addLevels(comboBoxTxtImgLevel);
		panelTxtImg.add(comboBoxTxtImgLevel);

		FileChooser fileChooserTxtImgCrt = new FileChooser();
		fileChooserTxtImgCrt.setBounds(40, 223, 130, 27);
		panelTxtImg.add(fileChooserTxtImgCrt);

		FileChooser fileChooserTxtImgWrg2 = new FileChooser();
		fileChooserTxtImgWrg2.setBounds(40, 425, 130, 27);
		panelTxtImg.add(fileChooserTxtImgWrg2);

		FileChooser fileChooserTxtImgWrg3 = new FileChooser();
		fileChooserTxtImgWrg3.setBounds(326, 425, 130, 27);
		panelTxtImg.add(fileChooserTxtImgWrg3);

		FileChooser fileChooserTxtImgWrg1 = new FileChooser();
		fileChooserTxtImgWrg1.setBounds(320, 223, 130, 27);
		panelTxtImg.add(fileChooserTxtImgWrg1);

		JLabel btnTxtImgModifica = new JLabel("Modifica");
		btnTxtImgModifica.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTxtImgModifica.setIcon(edit);
		btnTxtImgModifica.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				try {
					QuestionHandlerInterface qh = RemoteHandlers.getInstance().getQuestionHandler();
					Question question = getQuestionToEdit();
					LinkedHashMap<Integer, File> opzioni = new LinkedHashMap<>();

					if (fileChooserTxtImgCrt.getFile() != null) {
						opzioni.put(question.getOptions().get(3).getId(), fileChooserTxtImgCrt.getFile());
					}
					if (fileChooserTxtImgWrg1.getFile() != null) {
						opzioni.put(question.getOptions().get(0).getId(), fileChooserTxtImgWrg1.getFile());
					}
					if (fileChooserTxtImgWrg2.getFile() != null) {
						opzioni.put(question.getOptions().get(1).getId(), fileChooserTxtImgWrg2.getFile());
					}
					if (fileChooserTxtImgWrg3.getFile() != null) {
						opzioni.put(question.getOptions().get(2).getId(), fileChooserTxtImgWrg3.getFile());
					}
					Level level = Level.getLevelFromInt((int) comboBoxImgImgLevel.getSelectedItem());
					Category category = Category
							.getCategoryFromIta(comboBoxImgImgCategory.getSelectedItem().toString());
					qh.editQuestion(question.getId(), textFieldTxtImgText.getText(), level, category, null, opzioni,
							author, null, true);

					JOptionPane.showMessageDialog(contentPane, "Domanda modificata!");
					// HomeAuthor.getInstance(contentPane, author);
					new EditQuestion(contentPane, author);
					setVisible(false);
				} catch (RemoteException e) {
					RemoteHandlers.reset();
					ConnectionIssues.getInstance(contentPane);
					setVisible(false);
				} catch (NotBoundException e) {
					RemoteHandlers.reset();
					ConnectionIssues.getInstance(contentPane);
					setVisible(false);
				} catch (DatabaseProblemException e) {
					JOptionPane.showMessageDialog(contentPane,
							"Hai inserito un campo non valido per  il Database, ricontrolla!");
					e.printStackTrace();
				}

			}
		});
		btnTxtImgModifica.setBounds(50, 499, 91, 50);
		panelTxtImg.add(btnTxtImgModifica);

		JLabel label_16 = new JLabel("Categoria:");
		label_16.setBounds(10, 476, 72, 16);
		label_16.setFont(frutiger16);
		label_16.setForeground(myYellow);
		panelTxtImg.add(label_16);

		panelTxtImg.add(comboBoxTxtImgCategory);

		JLabel lblLivello = new JLabel("Livello:");
		lblLivello.setHorizontalAlignment(SwingConstants.CENTER);
		lblLivello.setBounds(333, 479, 85, 14);
		lblLivello.setFont(frutiger16);
		lblLivello.setForeground(myYellow);
		panelTxtImg.add(lblLivello);

		JLabel lblTxtImgCrct = new JLabel("New label");
		lblTxtImgCrct.setBounds(40, 104, 109, 108);
		panelTxtImg.add(lblTxtImgCrct);

		JLabel lblTxtImgWrg1 = new JLabel("New label");
		lblTxtImgWrg1.setBounds(320, 104, 109, 108);
		panelTxtImg.add(lblTxtImgWrg1);

		JLabel lblTxtImgWrg2 = new JLabel("New label");
		lblTxtImgWrg2.setBounds(40, 306, 109, 108);
		panelTxtImg.add(lblTxtImgWrg2);

		JLabel lblTxtImgWrg3 = new JLabel("New label");
		lblTxtImgWrg3.setBounds(320, 306, 109, 108);
		panelTxtImg.add(lblTxtImgWrg3);
		panelTxtImg.setVisible(false);

		JPanel panelTxtTxt = new JPanel();
		panelTxtTxt.setOpaque(false);
		panelTxtTxt.setBounds(10, 11, 487, 560);
		add(panelTxtTxt);
		panelTxtTxt.setLayout(null);

		JLabel lblTestoDellaDomanda = new JLabel("Testo della domanda:");
		lblTestoDellaDomanda.setBounds(10, 11, 467, 14);
		lblTestoDellaDomanda.setFont(frutiger16);
		lblTestoDellaDomanda.setForeground(myYellow);
		panelTxtTxt.add(lblTestoDellaDomanda);

		JLabel lblOpzioneCorretta = new JLabel("Opzione corretta:");
		lblOpzioneCorretta.setBounds(10, 122, 467, 14);
		lblOpzioneCorretta.setFont(frutiger16);
		lblOpzioneCorretta.setForeground(myYellow);
		panelTxtTxt.add(lblOpzioneCorretta);

		textFieldTxtTxtCrct = new JTextField();
		textFieldTxtTxtCrct.setBounds(10, 147, 467, 50);
		textFieldTxtTxtCrct.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textFieldTxtTxtCrct.getText().length() > maxOptionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelTxtTxt.add(textFieldTxtTxtCrct);
		textFieldTxtTxtCrct.setColumns(10);

		JLabel lblOpzioneScorretta = new JLabel("Opzione scorretta:");
		lblOpzioneScorretta.setBounds(10, 208, 467, 14);
		lblOpzioneScorretta.setFont(frutiger16);
		lblOpzioneScorretta.setForeground(myYellow);
		panelTxtTxt.add(lblOpzioneScorretta);

		textFieldTxtTxtWrg1 = new JTextField();
		textFieldTxtTxtWrg1.setBounds(10, 233, 467, 50);
		textFieldTxtTxtWrg1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textFieldTxtTxtWrg1.getText().length() > maxOptionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelTxtTxt.add(textFieldTxtTxtWrg1);
		textFieldTxtTxtWrg1.setColumns(10);

		JLabel lblOpzioneScorretta_1 = new JLabel("Opzione scorretta:");
		lblOpzioneScorretta_1.setBounds(10, 294, 467, 14);
		lblOpzioneScorretta_1.setFont(frutiger16);
		lblOpzioneScorretta_1.setForeground(myYellow);
		panelTxtTxt.add(lblOpzioneScorretta_1);

		textFieldTxtTxtWrg2 = new JTextField();
		textFieldTxtTxtWrg2.setBounds(10, 319, 467, 50);
		textFieldTxtTxtWrg2.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textFieldTxtTxtWrg2.getText().length() > maxOptionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelTxtTxt.add(textFieldTxtTxtWrg2);
		textFieldTxtTxtWrg2.setColumns(10);

		JLabel lblOpzioneScorretta_2 = new JLabel("Opzione scorretta:");
		lblOpzioneScorretta_2.setBounds(10, 380, 467, 14);
		lblOpzioneScorretta_2.setFont(frutiger16);
		lblOpzioneScorretta_2.setForeground(myYellow);
		panelTxtTxt.add(lblOpzioneScorretta_2);

		textFieldTxtTxtWrg3 = new JTextField();
		textFieldTxtTxtWrg3.setBounds(10, 405, 467, 50);
		textFieldTxtTxtWrg3.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textFieldTxtTxtWrg3.getText().length() > maxOptionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelTxtTxt.add(textFieldTxtTxtWrg3);
		textFieldTxtTxtWrg3.setColumns(10);

		JLabel btnTxtTxtElimina = new JLabel("Elimina");
		btnTxtTxtElimina.setBorder(null);
		btnTxtTxtElimina.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTxtTxtElimina.setIcon(delete);
		btnTxtTxtElimina.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Object[] dialog = { "Elimina", "Annulla" };
				int n = JOptionPane.showOptionDialog(contentPane, "Stai per eliminare una domanda, sei sicuro?",
						"Feedback", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, dialog, dialog[0]);
				if (n == 0) {
					try {
						RemoteHandlers.getInstance().getQuestionHandler().deleteQuestion(getQuestionToEdit().getId(),
								author);
						JOptionPane.showMessageDialog(contentPane, "Domanda eliminata!");
						// HomeAuthor.getInstance(contentPane, author);
						new EditQuestion(contentPane, author);
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
						JOptionPane.showMessageDialog(contentPane,
								"Hai inserito un campo non valido per  il Database, ricontrolla!");
						e1.printStackTrace();
					}
				}
			}
		});
		btnTxtTxtElimina.setBounds(346, 499, 91, 50);
		panelTxtTxt.add(btnTxtTxtElimina);

		JComboBox<String> comboBoxTxtTxtCategory = new JComboBox<String>();
		comboBoxTxtTxtCategory.setBounds(118, 466, 192, 20);
		addCategories(comboBoxTxtTxtCategory);
		panelTxtTxt.add(comboBoxTxtTxtCategory);

		JComboBox<Integer> comboBoxTxtTxtLevel = new JComboBox<Integer>();
		comboBoxTxtTxtLevel.setBounds(428, 466, 49, 20);
		addLevels(comboBoxTxtTxtLevel);
		panelTxtTxt.add(comboBoxTxtTxtLevel);

		JLabel btnTxtTxtModifica = new JLabel("Modifica");
		btnTxtTxtModifica.setIcon(edit);
		btnTxtTxtModifica.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTxtTxtModifica.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Question question = getQuestionToEdit();
				try {
					QuestionHandlerInterface qh = RemoteHandlers.getInstance().getQuestionHandler();
					LinkedHashMap<Integer, String> opzioni = new LinkedHashMap<>();
					opzioni.put(question.getOptions().get(0).getId(), textFieldTxtTxtWrg1.getText());
					opzioni.put(question.getOptions().get(1).getId(), textFieldTxtTxtWrg2.getText());
					opzioni.put(question.getOptions().get(2).getId(), textFieldTxtTxtWrg3.getText());
					opzioni.put(question.getOptions().get(3).getId(), textFieldTxtTxtCrct.getText());
					Level level = Level.getLevelFromInt((int) comboBoxImgImgLevel.getSelectedItem());
					Category category = Category
							.getCategoryFromIta(comboBoxImgImgCategory.getSelectedItem().toString());
					qh.editQuestion(question.getId(), textFieldTxtTxtText.getText(), level, category, opzioni, null,
							author, null, false);

					JOptionPane.showMessageDialog(contentPane, "Domanda modificata!");
					// HomeAuthor.getInstance(contentPane, author);
					new EditQuestion(contentPane, author);
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
					JOptionPane.showMessageDialog(contentPane,
							"Hai inserito un campo non valido per  il Database, ricontrolla!");
					e1.printStackTrace();
				}

			}
		});
		btnTxtTxtModifica.setBounds(50, 499, 91, 50);
		panelTxtTxt.add(btnTxtTxtModifica);

		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setBounds(10, 466, 72, 16);
		lblCategoria.setFont(frutiger16);
		lblCategoria.setForeground(myYellow);
		panelTxtTxt.add(lblCategoria);

		JLabel lblLivelloDifficolt = new JLabel("Livello:");
		lblLivelloDifficolt.setHorizontalAlignment(SwingConstants.CENTER);
		lblLivelloDifficolt.setBounds(320, 466, 85, 14);
		lblLivelloDifficolt.setFont(frutiger16);
		lblLivelloDifficolt.setForeground(myYellow);
		panelTxtTxt.add(lblLivelloDifficolt);

		textFieldTxtTxtText = new JTextField();
		textFieldTxtTxtText.setColumns(10);
		textFieldTxtTxtText.setBounds(10, 36, 467, 75);
		textFieldTxtTxtText.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textFieldTxtTxtText.getText().length() > maxQuestionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelTxtTxt.add(textFieldTxtTxtText);
		panelTxtTxt.setVisible(false);

		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList<?> list1 = (JList<?>) evt.getSource();

				int index = list1.locationToIndex(evt.getPoint());
				int lab = (int) lhm.keySet().toArray()[index];
				System.out.println(lab);
				Question question = null;
				try {
					QuestionHandlerInterface qh = RemoteHandlers.getInstance().getQuestionHandler();
					question = qh.getQuestionById(lab);
					setQuestionToEdit(question);
					if (question.getImage() == null && question.getOptions().get(0).getImage() == null) {
						panelImgImg.setVisible(false);
						panelImgTxt.setVisible(false);
						panelTxtTxt.setVisible(true);
						panelTxtImg.setVisible(false);
						textFieldTxtTxtText.setText(question.getText());
						textFieldTxtTxtCrct.setText(question.getOptions().get(3).getText());
						textFieldTxtTxtWrg1.setText(question.getOptions().get(0).getText());
						textFieldTxtTxtWrg2.setText(question.getOptions().get(1).getText());
						textFieldTxtTxtWrg3.setText(question.getOptions().get(2).getText());
						comboBoxTxtTxtCategory.setSelectedItem(question.getCategory().toIta());
						comboBoxTxtTxtLevel.setSelectedItem(question.getDifficultyLevel().toInt());
					} else if (question.getImage() != null && question.getOptions().get(0).getImage() != null) {
						panelImgImg.setVisible(true);
						panelImgTxt.setVisible(false);
						panelTxtTxt.setVisible(false);
						panelTxtImg.setVisible(false);
						textFieldImgImgText.setText(question.getText());
						lblImgImgQImg.setIcon(new ImageIcon(question.getImage().getImage().getScaledInstance(
								lblImgImgQImg.getWidth(), lblImgImgQImg.getHeight(), Image.SCALE_SMOOTH)));
						lblImgImgCrct.setIcon(
								new ImageIcon(question.getOptions().get(3).getImage().getImage().getScaledInstance(
										lblImgImgCrct.getWidth(), lblImgImgCrct.getHeight(), Image.SCALE_SMOOTH)));
						lblImgImgWrg1.setIcon(
								new ImageIcon(question.getOptions().get(0).getImage().getImage().getScaledInstance(
										lblImgImgWrg1.getWidth(), lblImgImgWrg1.getHeight(), Image.SCALE_SMOOTH)));
						lblImgImgWrg2.setIcon(
								new ImageIcon(question.getOptions().get(1).getImage().getImage().getScaledInstance(
										lblImgImgWrg2.getWidth(), lblImgImgWrg2.getHeight(), Image.SCALE_SMOOTH)));
						lblImgImgWrg3.setIcon(
								new ImageIcon(question.getOptions().get(2).getImage().getImage().getScaledInstance(
										lblImgImgWrg3.getWidth(), lblImgImgWrg3.getHeight(), Image.SCALE_SMOOTH)));
						comboBoxImgImgCategory.setSelectedItem(question.getCategory().toIta());
						comboBoxImgImgLevel.setSelectedItem(question.getDifficultyLevel().toInt());

					} else if (question.getImage() == null && question.getOptions().get(0).getImage() != null) {
						panelImgImg.setVisible(false);
						panelImgTxt.setVisible(false);
						panelTxtTxt.setVisible(false);
						panelTxtImg.setVisible(true);
						textFieldTxtImgText.setText(question.getText());
						lblTxtImgCrct.setIcon(
								new ImageIcon(question.getOptions().get(3).getImage().getImage().getScaledInstance(
										lblTxtImgCrct.getWidth(), lblTxtImgCrct.getHeight(), Image.SCALE_SMOOTH)));
						lblTxtImgWrg1.setIcon(
								new ImageIcon(question.getOptions().get(0).getImage().getImage().getScaledInstance(
										lblTxtImgWrg1.getWidth(), lblTxtImgWrg1.getHeight(), Image.SCALE_SMOOTH)));
						lblTxtImgWrg2.setIcon(
								new ImageIcon(question.getOptions().get(1).getImage().getImage().getScaledInstance(
										lblTxtImgWrg2.getWidth(), lblTxtImgWrg2.getHeight(), Image.SCALE_SMOOTH)));
						lblTxtImgWrg3.setIcon(
								new ImageIcon(question.getOptions().get(2).getImage().getImage().getScaledInstance(
										lblTxtImgWrg3.getWidth(), lblTxtImgWrg3.getHeight(), Image.SCALE_SMOOTH)));
						comboBoxTxtImgCategory.setSelectedItem(question.getCategory().toIta());
						comboBoxTxtImgLevel.setSelectedItem(question.getDifficultyLevel().toInt());

					} else if (question.getImage() != null && question.getOptions().get(0).getImage() == null) {
						panelImgImg.setVisible(false);
						panelImgTxt.setVisible(true);
						panelTxtTxt.setVisible(false);
						panelTxtImg.setVisible(false);
						textFieldImgTxtText.setText(question.getText());
						lblImgTxTQImg.setIcon(new ImageIcon(question.getImage().getImage().getScaledInstance(
								lblImgTxTQImg.getWidth(), lblImgTxTQImg.getHeight(), Image.SCALE_SMOOTH)));
						textFieldImgTxtCrct.setText(question.getOptions().get(3).getText());
						textFieldImgTxtWrg1.setText(question.getOptions().get(0).getText());
						textFieldImgTxtWrg2.setText(question.getOptions().get(1).getText());
						textFieldImgTxtWrg3.setText(question.getOptions().get(2).getText());
						comboBoxImgTxtCategory.setSelectedItem(question.getCategory().toIta());
						comboBoxImgTxtLevel.setSelectedItem(question.getDifficultyLevel().toInt());

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
					JOptionPane.showMessageDialog(contentPane,
							"Hai inserito un campo non valido per  il Database, ricontrolla!");
					e.printStackTrace();
				}

			}
		});
	}

	private void setQuestionToEdit(Question question) {
		this.questionToEdit = question;
	}

	private Question getQuestionToEdit() {
		return this.questionToEdit;
	}

	private void addCategories(JComboBox<String> comboBox) {
		comboBox.addItem("ARTE");
		comboBox.addItem("STORIA");
		comboBox.addItem("SPORT");
		comboBox.addItem("SCIENZE");
		comboBox.addItem("INTRATTENIMENTO");
		comboBox.addItem("GEOGRAFIA");

	}

	private void addLevels(JComboBox<Integer> comboBox) {
		comboBox.addItem(1);
		comboBox.addItem(2);
		comboBox.addItem(3);

	}
}
