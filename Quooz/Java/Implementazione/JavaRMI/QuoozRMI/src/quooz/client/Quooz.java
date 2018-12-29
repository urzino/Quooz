
package quooz.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import quooz.client.gui.shared.Home;

import java.awt.Toolkit;
import java.rmi.RemoteException;

public class Quooz extends JFrame {
	private JPanel contentPane;
	/**
	 * 
	 */
	private static Client client=null;
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Quooz frame = new Quooz();
					frame.setVisible(true);
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public Quooz() {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(this.getClass().getResource("/logo_icona.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 820, 650);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		Home.getInstance(contentPane);

	}
	
	public static void setClient(String user){
		try {
			if(client==null) client=new Client(user);
		} catch (RemoteException e) {
			
			e.printStackTrace();
		}
	}
	public static void resetClient(){
		client = null;
	}
	
	public static Client getClient(){
		return client;
	}

}
