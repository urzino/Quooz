package quooz.client.gui.utility;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import java.awt.Cursor;

public class FileChooser extends JPanel {

	private static final long serialVersionUID = 1L;
	private File file;
	private JButton currentButton;

	public FileChooser() {
		setOpaque(false);
		createPanel();
		setFile(null);
		
	}
	public JButton getCurrentButton(){
		return this.currentButton;
		
	}
	public void setCurrentButton(JButton button){
		this.currentButton=button;
		
	}

	public File getFile() {
		return this.file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	private void createPanel() {
		setLayout(new BorderLayout(0, 0));
		JButton openFileChooser = new JButton("");
		openFileChooser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		openFileChooser.setIcon(new ImageIcon(this.getClass().getResource("/apri_immagini.png")));
		setCurrentButton(openFileChooser);
		openFileChooser.setBorderPainted(false);
		openFileChooser.setBackground(UIGraphicalUtils.getMyBlue());
		add(openFileChooser);
		openFileChooser.addActionListener(new OpenFileChooser());
	}
	private class OpenFileChooser implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new ImgFileFilter());
				int n = fileChooser.showOpenDialog(FileChooser.this);
				if (n == JFileChooser.APPROVE_OPTION) {
					setFile(fileChooser.getSelectedFile());		
					getCurrentButton().setIcon(new ImageIcon(this.getClass().getResource("/apri_immagini_ok.png")));
				}
			} catch (Exception ex) {
			}
		}
	}

	private class ImgFileFilter extends FileFilter {

		public boolean accept(File file) {
			if (file.isDirectory())
				return true;
			String fname = file.getName().toLowerCase();
			return fname.endsWith("jpeg") || fname.endsWith("jpg") || fname.endsWith("png");
		}

		public String getDescription() {
			return "File immagine";
		}
	}
}
