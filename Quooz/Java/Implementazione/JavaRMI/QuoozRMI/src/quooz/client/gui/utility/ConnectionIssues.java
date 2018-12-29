package quooz.client.gui.utility;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import quooz.client.Quooz;
import quooz.client.gui.shared.Home;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 * The Class ConnectionIssues is a simple panel used to show the user some kind
 * of problem in the connection.
 */
public class ConnectionIssues extends JPanel {

	private static final long serialVersionUID = 1L;
	private static ConnectionIssues instance;
	private Timer t = null;

	public static ConnectionIssues getInstance(JPanel contentPane) {
		instance = new ConnectionIssues(contentPane);
		contentPane.add(instance);
		instance.setVisible(true);
		return instance;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(UIGraphicalUtils.getBackground(), 0, 0, null);
	}

	/**
	 * Create the panel.
	 */
	public ConnectionIssues(JPanel contentPane) {
		setLayout(null);
		setBounds(100, 100, 800, 600);
		Quooz.resetClient();
		JLabel lblL = new JLabel("");
		lblL.setHorizontalAlignment(SwingConstants.CENTER);
		lblL.setBounds(0, 0, 800, 600);
		lblL.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/signal-2.png")).getImage()));
		add(lblL);
		t = new Timer(500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				t.stop();

				JOptionPane.showMessageDialog(contentPane,
						"Sono stati riscontrati problemi di connessione...\r\n" + "Tornerai alla pagina principale");
				Home.getInstance(contentPane);
				setVisible(false);

			}
		});
		t.start();

	}
}
