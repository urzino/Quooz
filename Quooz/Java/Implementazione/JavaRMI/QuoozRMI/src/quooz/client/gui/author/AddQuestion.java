package quooz.client.gui.author;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import quooz.shared.DatabaseProblemException;
import quooz.shared.question.Category;
import quooz.shared.question.Level;
import quooz.shared.question.QuestionHandlerInterface;
import quooz.client.gui.utility.RemoteHandlers;
import quooz.client.gui.utility.UIGraphicalUtils;
import quooz.client.gui.utility.ConnectionIssues;
import quooz.client.gui.utility.FileChooser;
import quooz.shared.user.Author;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JLabel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class AddQuestion extends JPanel {

	private static final long serialVersionUID = 1L;
	// Textfield of the 4 question cases:
	private JTextField textTxtTxtCrt;
	private JTextField textTxtTxtWrg1;
	private JTextField textTxtTxtWrg2;
	private JTextField textTxtTxtWrg3;
	private JTextField textImgTxtQText;
	private JTextField textImgTxtCrt;
	private JTextField textImgTxtWrg1;
	private JTextField textImgTxtWrg2;
	private JTextField textImgTxtWrg3;
	private JTextField textImgImgQText;
	private JTextField textTxtTxtQText;
	private JTextField textTxtImgQText;
	private static AddQuestion instance;
	private final int maxQuestionLength = 139;
	// 159 is limited by the dimension of the label which contains the question
	// text
	private final int maxOptionLength = 59;

	// 159 is limited by the dimension of the label which contains the option
	// text
	public static AddQuestion getInstance(JPanel contentPane, Author a) {
		if (instance == null) {
			instance = new AddQuestion(contentPane, a);
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

	private AddQuestion(JPanel contentPane, Author author) {
		setBounds(100, 100, 800, 600);
		setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBorder(null);
		tabbedPane.setBackground(new Color(5, 56, 87));
		tabbedPane.setBounds(25, 73, 750, 521);
		JComponent panelTxt = new JPanel(false);
		panelTxt.setBackground(UIGraphicalUtils.getMyBlue());
		tabbedPane.addTab("Completamente testuale", panelTxt);
		panelTxt.setLayout(null);

		Font frutiger16 = UIGraphicalUtils.loadFrutigerFont(16f);
		Color myYellow = UIGraphicalUtils.getMyYellow();
		ImageIcon save = new ImageIcon(this.getClass().getResource("/salva.png"));

		JLabel lblQuestionTxt = new JLabel("Testo della domanda:");
		lblQuestionTxt.setBounds(10, 8, 630, 20);
		lblQuestionTxt.setFont(frutiger16);
		lblQuestionTxt.setForeground(myYellow);
		panelTxt.add(lblQuestionTxt);

		textTxtTxtQText = new JTextField();
		textTxtTxtQText.setBounds(10, 30, 713, 60);
		textTxtTxtQText.setColumns(10);
		textTxtTxtQText.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textTxtTxtQText.getText().length() > maxQuestionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelTxt.add(textTxtTxtQText);

		JLabel lblCrctOpt = new JLabel("Opzione corretta:");
		lblCrctOpt.setBounds(10, 101, 630, 20);
		lblCrctOpt.setFont(frutiger16);
		lblCrctOpt.setForeground(myYellow);
		panelTxt.add(lblCrctOpt);

		textTxtTxtCrt = new JTextField();
		textTxtTxtCrt.setBounds(10, 126, 713, 40);
		textTxtTxtCrt.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textTxtTxtCrt.getText().length() > maxOptionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		textTxtTxtCrt.setColumns(10);
		panelTxt.add(textTxtTxtCrt);

		textTxtTxtWrg1 = new JTextField();
		textTxtTxtWrg1.setBounds(10, 202, 713, 40);
		textTxtTxtWrg1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textTxtTxtWrg1.getText().length() > maxOptionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelTxt.add(textTxtTxtWrg1);
		textTxtTxtWrg1.setColumns(10);

		textTxtTxtWrg2 = new JTextField();
		textTxtTxtWrg2.setBounds(10, 278, 713, 40);
		textTxtTxtWrg2.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textTxtTxtWrg2.getText().length() > maxOptionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelTxt.add(textTxtTxtWrg2);
		textTxtTxtWrg2.setColumns(10);

		textTxtTxtWrg3 = new JTextField();
		textTxtTxtWrg3.setBounds(10, 354, 713, 40);
		textTxtTxtWrg3.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textTxtTxtWrg3.getText().length() > maxOptionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelTxt.add(textTxtTxtWrg3);
		textTxtTxtWrg3.setColumns(10);

		JLabel lblNotCrctOpt = new JLabel("Opzione scorretta:");
		lblNotCrctOpt.setBounds(10, 177, 630, 20);
		lblNotCrctOpt.setFont(frutiger16);
		lblNotCrctOpt.setForeground(myYellow);
		panelTxt.add(lblNotCrctOpt);

		JLabel lblNotCrctOpt1 = new JLabel("Opzione scorretta:");
		lblNotCrctOpt1.setBounds(10, 253, 630, 20);
		lblNotCrctOpt1.setForeground(myYellow);
		lblNotCrctOpt1.setFont(frutiger16);
		panelTxt.add(lblNotCrctOpt1);

		JLabel lblNotCrctOpt2 = new JLabel("Opzione scorretta:");
		lblNotCrctOpt2.setBounds(10, 329, 655, 20);
		lblNotCrctOpt2.setForeground(myYellow);
		lblNotCrctOpt2.setFont(frutiger16);
		panelTxt.add(lblNotCrctOpt2);

		JComboBox<String> comboBoxTxtTxtCat = new JComboBox<String>();
		addCategories(comboBoxTxtTxtCat);

		panelTxt.add(comboBoxTxtTxtCat);

		JComboBox<Integer> comboBoxTxtTxtLvl = new JComboBox<Integer>();
		comboBoxTxtTxtLvl.setBounds(649, 406, 60, 20);
		addLevels(comboBoxTxtTxtLvl);
		panelTxt.add(comboBoxTxtTxtLvl);

		JLabel lblCategory = new JLabel("Categoria:");
		lblCategory.setBounds(193, 404, 70, 20);
		lblCategory.setForeground(myYellow);
		lblCategory.setFont(frutiger16);
		panelTxt.add(lblCategory);

		JLabel lblDiffLevel = new JLabel("Livello difficolt\u00E0:");
		lblDiffLevel.setBounds(537, 405, 100, 20);
		lblDiffLevel.setForeground(myYellow);
		lblDiffLevel.setFont(frutiger16);
		panelTxt.add(lblDiffLevel);

		JLabel btnTxtTxtAdd = new JLabel("Aggiungi");
		btnTxtTxtAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTxtTxtAdd.setIcon(save);
		btnTxtTxtAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					QuestionHandlerInterface qh = RemoteHandlers.getInstance().getQuestionHandler();

					Level level = Level.getLevelFromInt((int) comboBoxTxtTxtLvl.getSelectedItem());
					Category category = Category.getCategoryFromIta(comboBoxTxtTxtCat.getSelectedItem().toString());
					ArrayList<String> wrongOptionsTextual = new ArrayList<>();
					wrongOptionsTextual.add(textTxtTxtWrg1.getText());
					wrongOptionsTextual.add(textTxtTxtWrg2.getText());
					wrongOptionsTextual.add(textTxtTxtWrg3.getText());
					// fields check
					if (textTxtTxtCrt.getText().equals("") || textTxtTxtQText.getText().equals("")
							|| textTxtTxtWrg1.getText().equals("") || textTxtTxtWrg2.getText().equals("")
							|| textTxtTxtWrg3.getText().equals("")) {
						JOptionPane.showMessageDialog(contentPane, "Completa tutti i campi!");
					} else {

						qh.createNewQuestion(textTxtTxtQText.getText(), level, category, wrongOptionsTextual,
								textTxtTxtCrt.getText(), author);

						textTxtTxtWrg1.setText("");
						textTxtTxtWrg2.setText("");
						textTxtTxtWrg3.setText("");
						textTxtTxtCrt.setText("");
						textTxtTxtQText.setText("");
						JOptionPane.showMessageDialog(contentPane, "Domanda aggiunta con successo!");
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
					JOptionPane.showMessageDialog(contentPane, "Hai inserito un campo non valido per  il Database, ricontrolla!");
					e.printStackTrace();
				}
			}
		});
		btnTxtTxtAdd.setBounds(636, 436, 87, 30);
		panelTxt.add(btnTxtTxtAdd);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		JComponent panelTxtOptImg = new JPanel(false);
		panelTxtOptImg.setBackground(UIGraphicalUtils.getMyBlue());
		tabbedPane.addTab("Completamente Immagine", panelTxtOptImg);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_4);
		panelTxtOptImg.setLayout(null);

		JLabel lblQuestionTxt3 = new JLabel("Testo della domanda:");
		lblQuestionTxt3.setBounds(10, 11, 160, 20);
		lblQuestionTxt3.setForeground(myYellow);
		lblQuestionTxt3.setFont(frutiger16);
		panelTxtOptImg.add(lblQuestionTxt3);

		textImgImgQText = new JTextField();
		textImgImgQText.setBounds(180, 11, 350, 100);
		textImgImgQText.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textImgImgQText.getText().length() > maxQuestionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelTxtOptImg.add(textImgImgQText);
		textImgImgQText.setColumns(10);

		FileChooser fileChooserImgImgQImg = new FileChooser();
		fileChooserImgImgQImg.setBounds(550, 11, 130, 27);
		panelTxtOptImg.add(fileChooserImgImgQImg);

		FileChooser fileChooserImgImgCrt = new FileChooser();
		fileChooserImgImgCrt.setBounds(79, 158, 130, 27);
		panelTxtOptImg.add(fileChooserImgImgCrt);

		JLabel lblCrctOpt3 = new JLabel("Opzione corretta:");
		lblCrctOpt3.setBounds(79, 133, 201, 20);
		lblCrctOpt3.setForeground(myYellow);
		lblCrctOpt3.setFont(frutiger16);
		panelTxtOptImg.add(lblCrctOpt3);

		JLabel lblWrgOpt14 = new JLabel("Opzione scorretta:");
		lblWrgOpt14.setBounds(503, 133, 174, 20);
		lblWrgOpt14.setForeground(myYellow);
		lblWrgOpt14.setFont(frutiger16);
		panelTxtOptImg.add(lblWrgOpt14);

		FileChooser fileChooserImgImgWrg1 = new FileChooser();
		fileChooserImgImgWrg1.setBounds(503, 161, 130, 27);
		panelTxtOptImg.add(fileChooserImgImgWrg1);

		JLabel lblWrgOpt24 = new JLabel("Opzione scorretta:");
		lblWrgOpt24.setBounds(79, 251, 257, 14);
		lblWrgOpt24.setForeground(myYellow);
		lblWrgOpt24.setFont(frutiger16);
		panelTxtOptImg.add(lblWrgOpt24);

		FileChooser fileChooserImgImgWrg2 = new FileChooser();
		fileChooserImgImgWrg2.setBounds(503, 272, 130, 27);
		panelTxtOptImg.add(fileChooserImgImgWrg2);

		FileChooser fileChooserImgImgWrg3 = new FileChooser();
		fileChooserImgImgWrg3.setBounds(79, 276, 130, 27);
		panelTxtOptImg.add(fileChooserImgImgWrg3);

		JLabel lblWrgOpt34 = new JLabel("Opzione scorretta:");
		lblWrgOpt34.setBounds(510, 245, 167, 20);
		lblWrgOpt34.setForeground(myYellow);
		lblWrgOpt34.setFont(frutiger16);
		panelTxtOptImg.add(lblWrgOpt34);

		JComboBox<String> comboBoxImgImgCat = new JComboBox<String>();
		addCategories(comboBoxImgImgCat);
		comboBoxImgImgCat.setBounds(297, 406, 250, 20);
		panelTxtOptImg.add(comboBoxImgImgCat);

		JLabel lblCategory3 = new JLabel("Categoria:");
		lblCategory3.setBounds(215, 408, 70, 14);
		lblCategory3.setForeground(myYellow);
		lblCategory3.setFont(frutiger16);
		panelTxtOptImg.add(lblCategory3);

		JLabel lblDiffLevel3 = new JLabel("Livello difficolt\u00E0:");
		lblDiffLevel3.setBounds(550, 408, 100, 14);
		lblDiffLevel3.setForeground(myYellow);
		lblDiffLevel3.setFont(frutiger16);
		panelTxtOptImg.add(lblDiffLevel3);

		JComboBox<Integer> comboBoxImgImgLvl = new JComboBox<Integer>();
		addLevels(comboBoxImgImgLvl);
		comboBoxImgImgLvl.setBounds(662, 406, 60, 20);
		panelTxtOptImg.add(comboBoxImgImgLvl);

		JLabel btnImgImgAdd = new JLabel("Aggiungi");
		btnImgImgAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnImgImgAdd.setIcon(save);
		btnImgImgAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					QuestionHandlerInterface qh = RemoteHandlers.getInstance().getQuestionHandler();

					Level level = Level.getLevelFromInt((int) comboBoxTxtTxtLvl.getSelectedItem());
					Category category = Category.getCategoryFromIta(comboBoxImgImgCat.getSelectedItem().toString());
					ArrayList<File> wrongOptionsImage = new ArrayList<>();
					// add file only if they are selected
					if (fileChooserImgImgWrg1.getFile() != null) {
						wrongOptionsImage.add(fileChooserImgImgWrg1.getFile());
					}
					if (fileChooserImgImgWrg2.getFile() != null) {
						wrongOptionsImage.add(fileChooserImgImgWrg2.getFile());
					}
					if (fileChooserImgImgWrg3.getFile() != null) {
						wrongOptionsImage.add(fileChooserImgImgWrg3.getFile());
					}

					File correctImage = null;
					if (fileChooserImgImgCrt.getFile() != null) {
						correctImage = fileChooserImgImgCrt.getFile();
					}
					File questionImage = null;
					if (fileChooserImgImgQImg.getFile() != null) {
						questionImage = fileChooserImgImgQImg.getFile();
					}
					// field check
					if (textImgImgQText.getText().equals("") || correctImage == null || wrongOptionsImage.size() != 3
							|| questionImage == null) {
						JOptionPane.showMessageDialog(contentPane, "Completa tutti i campi!");
					} else {

						qh.createNewQuestion(textImgImgQText.getText(), level, category, wrongOptionsImage,
								correctImage, author, questionImage);

						textImgImgQText.setText("");
						fileChooserImgImgWrg1.setFile(null);
						fileChooserImgImgWrg2.setFile(null);
						fileChooserImgImgWrg3.setFile(null);
						fileChooserImgImgCrt.setFile(null);
						fileChooserImgImgQImg.setFile(null);

						fileChooserImgImgWrg1.getCurrentButton()
								.setIcon(new ImageIcon(this.getClass().getResource("/apri_immagini.png")));
						fileChooserImgImgWrg2.getCurrentButton()
								.setIcon(new ImageIcon(this.getClass().getResource("/apri_immagini.png")));
						fileChooserImgImgWrg3.getCurrentButton()
								.setIcon(new ImageIcon(this.getClass().getResource("/apri_immagini.png")));
						fileChooserImgImgCrt.getCurrentButton()
								.setIcon(new ImageIcon(this.getClass().getResource("/apri_immagini.png")));
						fileChooserImgImgQImg.getCurrentButton()
								.setIcon(new ImageIcon(this.getClass().getResource("/apri_immagini.png")));
						correctImage = null;
						questionImage = null;

						JOptionPane.showMessageDialog(contentPane, "Domanda aggiunta con successo!");
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
					JOptionPane.showMessageDialog(contentPane, "Hai inserito un campo non valido per  il Database, ricontrolla!");
					e1.printStackTrace();
				}

			}
		});

		btnImgImgAdd.setBounds(636, 437, 87, 30);
		panelTxtOptImg.add(btnImgImgAdd);

		JComponent panelTxtImg = new JPanel(false);
		panelTxtImg.setBackground(UIGraphicalUtils.getMyBlue());
		tabbedPane.addTab("Domanda Immagine", panelTxtImg);
		panelTxtImg.setLayout(null);

		JLabel lblQuestionTxt1 = new JLabel("Testo della domanda:");
		lblQuestionTxt1.setBounds(10, 10, 516, 20);
		lblQuestionTxt1.setForeground(myYellow);
		lblQuestionTxt1.setFont(frutiger16);
		panelTxtImg.add(lblQuestionTxt1);

		textImgTxtQText = new JTextField();
		textImgTxtQText.setBounds(10, 36, 516, 120);
		textImgTxtQText.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textImgTxtQText.getText().length() > maxQuestionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelTxtImg.add(textImgTxtQText);
		textImgTxtQText.setColumns(10);

		FileChooser fileChooserImgTxtQimage = new FileChooser();
		fileChooserImgTxtQimage.setBounds(565, 36, 130, 27);
		panelTxtImg.add(fileChooserImgTxtQimage);

		textImgTxtCrt = new JTextField();
		textImgTxtCrt.setBounds(10, 204, 713, 20);
		textImgTxtCrt.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textImgTxtCrt.getText().length() > maxOptionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelTxtImg.add(textImgTxtCrt);
		textImgTxtCrt.setColumns(10);

		textImgTxtWrg1 = new JTextField();
		textImgTxtWrg1.setBounds(10, 260, 713, 20);
		textImgTxtWrg1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textImgTxtWrg1.getText().length() > maxOptionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelTxtImg.add(textImgTxtWrg1);
		textImgTxtWrg1.setColumns(10);

		textImgTxtWrg2 = new JTextField();
		textImgTxtWrg2.setBounds(10, 316, 713, 20);
		textImgTxtWrg2.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textImgTxtWrg2.getText().length() > maxOptionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelTxtImg.add(textImgTxtWrg2);
		textImgTxtWrg2.setColumns(10);

		textImgTxtWrg3 = new JTextField();
		textImgTxtWrg3.setBounds(10, 372, 713, 20);
		textImgTxtWrg3.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textImgTxtWrg1.getText().length() > maxOptionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelTxtImg.add(textImgTxtWrg3);
		textImgTxtWrg3.setColumns(10);

		JComboBox<String> comboBoxImgTxtCat = new JComboBox<String>();
		addCategories(comboBoxImgTxtCat);
		comboBoxImgTxtCat.setBounds(276, 406, 250, 20);
		panelTxtImg.add(comboBoxImgTxtCat);

		JComboBox<Integer> comboBoxImgTxtLvl = new JComboBox<Integer>();
		addLevels(comboBoxImgTxtLvl);
		comboBoxImgTxtLvl.setBounds(663, 406, 60, 20);
		panelTxtImg.add(comboBoxImgTxtLvl);

		JLabel lblDiffLevel1 = new JLabel("Livello difficolt\u00E0:");
		lblDiffLevel1.setBounds(549, 405, 100, 20);
		lblDiffLevel1.setForeground(myYellow);
		lblDiffLevel1.setFont(frutiger16);
		panelTxtImg.add(lblDiffLevel1);

		JLabel lblCategory1 = new JLabel("Categoria:");
		lblCategory1.setBounds(205, 406, 70, 20);
		lblCategory1.setForeground(myYellow);
		lblCategory1.setFont(frutiger16);
		panelTxtImg.add(lblCategory1);

		JLabel lblWrgOpt32 = new JLabel("Opzione scorretta:");
		lblWrgOpt32.setBounds(10, 347, 725, 20);
		lblWrgOpt32.setForeground(myYellow);
		lblWrgOpt32.setFont(frutiger16);
		panelTxtImg.add(lblWrgOpt32);

		JLabel lblWrgOpt22 = new JLabel("Opzione scorretta:");
		lblWrgOpt22.setBounds(10, 291, 725, 20);
		lblWrgOpt22.setForeground(myYellow);
		lblWrgOpt22.setFont(frutiger16);
		panelTxtImg.add(lblWrgOpt22);

		JLabel lblWrgOpt12 = new JLabel("Opzione scorretta:");
		lblWrgOpt12.setBounds(10, 235, 725, 20);
		lblWrgOpt12.setForeground(myYellow);
		lblWrgOpt12.setFont(frutiger16);
		panelTxtImg.add(lblWrgOpt12);

		JLabel lblCrctOpt1 = new JLabel("Opzione corretta:");
		lblCrctOpt1.setBounds(10, 179, 725, 20);
		lblCrctOpt1.setForeground(myYellow);
		lblCrctOpt1.setFont(frutiger16);
		panelTxtImg.add(lblCrctOpt1);

		JLabel btnImgTxtAdd = new JLabel("Aggiungi");
		btnImgTxtAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnImgTxtAdd.setIcon(save);
		btnImgTxtAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					QuestionHandlerInterface qh = RemoteHandlers.getInstance().getQuestionHandler();

					Level level = Level.getLevelFromInt((int) comboBoxTxtTxtLvl.getSelectedItem());
					Category category = Category.getCategoryFromIta(comboBoxImgTxtCat.getSelectedItem().toString());
					ArrayList<String> wrongOptionsTextual = new ArrayList<>();
					wrongOptionsTextual.add(textImgTxtWrg1.getText());
					wrongOptionsTextual.add(textImgTxtWrg2.getText());
					wrongOptionsTextual.add(textImgTxtWrg3.getText());
					File image = null;
					if (fileChooserImgTxtQimage.getFile() != null) {
						image = fileChooserImgTxtQimage.getFile();
					}
					// fields check
					if (textImgTxtCrt.getText().equals("") || textImgTxtQText.getText().equals("")
							|| textImgTxtWrg1.getText().equals("") || textImgTxtWrg2.getText().equals("")
							|| textImgTxtWrg3.getText().equals("") || image == null) {
						JOptionPane.showMessageDialog(contentPane, "Completa tutti i campi!");
					} else {

						qh.createNewQuestion(textImgTxtQText.getText(), level, category, wrongOptionsTextual,
								textImgTxtCrt.getText(), author, image);

						textImgTxtWrg1.setText("");
						textImgTxtWrg2.setText("");
						textImgTxtWrg3.setText("");
						textImgTxtCrt.setText("");
						textImgTxtQText.setText("");
						fileChooserImgTxtQimage.setFile(null);
						fileChooserImgTxtQimage.getCurrentButton()
								.setIcon(new ImageIcon(this.getClass().getResource("/apri_immagini.png")));
						JOptionPane.showMessageDialog(contentPane, "Domanda aggiunta con successo!");
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
					JOptionPane.showMessageDialog(contentPane, "Hai inserito un campo non valido per  il Database, ricontrolla!");
					e1.printStackTrace();
				}

			}
		});
		btnImgTxtAdd.setBounds(636, 437, 87, 30);
		panelTxtImg.add(btnImgTxtAdd);
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_2);

		JComponent panelOptImg = new JPanel(false);
		panelOptImg.setBackground(UIGraphicalUtils.getMyBlue());
		tabbedPane.addTab("Opzioni Immagine", panelOptImg);
		panelOptImg.setLayout(null);

		JLabel lblQuestionTxt2 = new JLabel("Testo della domanda:");
		lblQuestionTxt2.setBounds(10, 11, 725, 20);
		lblQuestionTxt2.setForeground(myYellow);
		lblQuestionTxt2.setFont(frutiger16);
		panelOptImg.add(lblQuestionTxt2);

		textTxtImgQText = new JTextField();
		textTxtImgQText.setBounds(10, 34, 713, 20);
		textTxtImgQText.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (textTxtImgQText.getText().length() > maxQuestionLength) {
					getToolkit().beep();
					e.consume();
				}

			}
		});
		panelOptImg.add(textTxtImgQText);
		textTxtImgQText.setColumns(10);

		FileChooser fileChooserTxtImgCrt = new FileChooser();
		fileChooserTxtImgCrt.setBounds(20, 90, 130, 27);
		panelOptImg.add(fileChooserTxtImgCrt);

		JLabel lblCrctOpt2 = new JLabel("Opzione corretta:");
		lblCrctOpt2.setBounds(10, 65, 285, 20);
		lblCrctOpt2.setForeground(myYellow);
		lblCrctOpt2.setFont(frutiger16);
		panelOptImg.add(lblCrctOpt2);

		JLabel lblWrgOpt33 = new JLabel("Opzione scorretta:");
		lblWrgOpt33.setBounds(380, 65, 292, 20);
		lblWrgOpt33.setForeground(myYellow);
		lblWrgOpt33.setFont(frutiger16);
		panelOptImg.add(lblWrgOpt33);

		FileChooser fileChooserTxtImgWrg1 = new FileChooser();
		fileChooserTxtImgWrg1.setBounds(380, 90, 130, 27);
		panelOptImg.add(fileChooserTxtImgWrg1);

		JLabel lblWrgOpt13 = new JLabel("Opzione scorretta:");
		lblWrgOpt13.setBounds(10, 234, 285, 20);
		lblWrgOpt13.setForeground(myYellow);
		lblWrgOpt13.setFont(frutiger16);
		panelOptImg.add(lblWrgOpt13);

		FileChooser fileChooserTxtImgWrg3 = new FileChooser();
		fileChooserTxtImgWrg3.setBounds(20, 259, 130, 27);
		panelOptImg.add(fileChooserTxtImgWrg3);

		JLabel lblWrgOpt23 = new JLabel("Opzione scorretta:");
		lblWrgOpt23.setBounds(380, 234, 292, 20);
		lblWrgOpt23.setForeground(myYellow);
		lblWrgOpt23.setFont(frutiger16);
		panelOptImg.add(lblWrgOpt23);

		FileChooser fileChooserTxtImgWrg2 = new FileChooser();
		fileChooserTxtImgWrg2.setBounds(380, 259, 130, 27);
		panelOptImg.add(fileChooserTxtImgWrg2);

		JLabel lblCategory2 = new JLabel("Categoria:");
		lblCategory2.setBounds(196, 409, 70, 20);
		lblCategory2.setForeground(myYellow);
		lblCategory2.setFont(frutiger16);
		panelOptImg.add(lblCategory2);

		JComboBox<String> comboBoxTxtImgCat = new JComboBox<String>();
		addCategories(comboBoxTxtImgCat);
		comboBoxTxtTxtCat.setBounds(275, 406, 250, 20);
		comboBoxTxtImgCat.setBounds(278, 410, 250, 20);
		panelOptImg.add(comboBoxTxtImgCat);

		JLabel lblDiffLevel2 = new JLabel("Livello difficolt\u00E0:");
		lblDiffLevel2.setBounds(540, 409, 100, 20);
		lblDiffLevel2.setForeground(myYellow);
		lblDiffLevel2.setFont(frutiger16);
		panelOptImg.add(lblDiffLevel2);

		JComboBox<Integer> comboBoxTxtImgLvl = new JComboBox<Integer>();
		addLevels(comboBoxTxtImgLvl);
		comboBoxTxtImgLvl.setBounds(652, 410, 60, 20);
		panelOptImg.add(comboBoxTxtImgLvl);

		JLabel btnTxtImgAdd = new JLabel("Aggiungi");
		btnTxtImgAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTxtImgAdd.setIcon(save);
		btnTxtImgAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					QuestionHandlerInterface qh = RemoteHandlers.getInstance().getQuestionHandler();

					Level level = Level.getLevelFromInt((int) comboBoxTxtTxtLvl.getSelectedItem());
					Category category = Category.getCategoryFromIta(comboBoxTxtImgCat.getSelectedItem().toString());
					ArrayList<File> wrongOptionsImage = new ArrayList<>();
					if (fileChooserTxtImgWrg1.getFile() != null) {
						wrongOptionsImage.add(fileChooserTxtImgWrg1.getFile());
					}
					if (fileChooserTxtImgWrg2.getFile() != null) {
						wrongOptionsImage.add(fileChooserTxtImgWrg2.getFile());
					}
					if (fileChooserTxtImgWrg3.getFile() != null) {
						wrongOptionsImage.add(fileChooserTxtImgWrg3.getFile());
					}

					File correctImage = null;
					if (fileChooserTxtImgCrt.getFile() != null) {
						correctImage = fileChooserTxtImgCrt.getFile();
					}
					// fields check
					if (textTxtImgQText.getText().equals("") || correctImage == null || wrongOptionsImage.size() != 3) {
						JOptionPane.showMessageDialog(contentPane, "Completa tutti i campi!");
					} else {

						qh.createNewQuestion(textTxtImgQText.getText(), level, category, wrongOptionsImage,
								correctImage, author);

						textTxtImgQText.setText("");
						fileChooserTxtImgWrg1.setFile(null);
						fileChooserTxtImgWrg2.setFile(null);
						fileChooserTxtImgWrg3.setFile(null);
						fileChooserTxtImgCrt.setFile(null);
						fileChooserTxtImgWrg1.getCurrentButton()
								.setIcon(new ImageIcon(this.getClass().getResource("/apri_immagini.png")));
						fileChooserTxtImgWrg2.getCurrentButton()
								.setIcon(new ImageIcon(this.getClass().getResource("/apri_immagini.png")));
						fileChooserTxtImgWrg3.getCurrentButton()
								.setIcon(new ImageIcon(this.getClass().getResource("/apri_immagini.png")));
						fileChooserTxtImgCrt.getCurrentButton()
								.setIcon(new ImageIcon(this.getClass().getResource("/apri_immagini.png")));

						correctImage = null;

						JOptionPane.showMessageDialog(contentPane, "Domanda aggiunta con successo!");
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
					JOptionPane.showMessageDialog(contentPane, "Hai inserito un campo non valido per  il Database, ricontrolla!");
					e1.printStackTrace();
				}

			}
		});
		btnTxtImgAdd.setBounds(636, 437, 87, 30);
		panelOptImg.add(btnTxtImgAdd);
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_3);
		UIManager.put("TabbedPane.selected", Color.BLACK);
		UIManager.put("TabbedPane.selectHighlight", UIGraphicalUtils.getMyYellow());
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {

			tabbedPane.setForegroundAt(i, UIGraphicalUtils.getMyYellow());
			tabbedPane.setBackgroundAt(i, UIGraphicalUtils.getMyBlue());
		}

		add(tabbedPane);

		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		JLabel lblPreviousPage = new JLabel("INDIETRO");
		lblPreviousPage.setBounds(668, 11, 122, 58);
		lblPreviousPage.setFont(UIGraphicalUtils.loadPractiqueFont(33f));
		lblPreviousPage.setForeground(myYellow);
		lblPreviousPage.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblPreviousPage.setIcon(new ImageIcon(this.getClass().getResource("/left_yellow.png")));
		lblPreviousPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				HomeAuthor.getInstance(contentPane, author);
				setVisible(false);
			}
		});
		add(lblPreviousPage);
		JLabel lbTitle = new JLabel("Per creare una nuova domanda, scegli la tipologia e riempi i campi necessari:");
		lbTitle.setBounds(25, 31, 625, 18);
		lbTitle.setFont(UIGraphicalUtils.loadFrutigerFont(18f));
		lbTitle.setForeground(myYellow);
		add(lbTitle);

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
