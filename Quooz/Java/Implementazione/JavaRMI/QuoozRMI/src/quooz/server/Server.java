package quooz.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import quooz.server.question.QuestionHandler;
import quooz.server.system.GameHandler;
import quooz.server.user.AuthorHandler;
import quooz.server.user.PlayerHandler;
import quooz.shared.question.QuestionHandlerInterface;
import quooz.shared.system.GameHandlerInterface;
import quooz.shared.user.AuthorHandlerInterface;
import quooz.shared.user.PlayerHandlerInterface;

/**
 * The Class Server which allows clients to access game's functionalities.
 */
public class Server {

	/**
	 * Instantiates a new server.
	 */
	public Server() {
		
	}

	/**
	 * The main method used to launch the Server.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			//puts the instance of the handler on the registry
			Registry reg = LocateRegistry.createRegistry(12345);
			GameHandlerInterface ghi = GameHandler.getInstance();
			PlayerHandlerInterface phi = PlayerHandler.getInstance();
			AuthorHandlerInterface ahi = AuthorHandler.getInstance();
			QuestionHandlerInterface qhi = QuestionHandler.getInstance();
			reg.rebind("gameHandler", ghi);
			reg.rebind("playerHandler", phi);
			reg.rebind("authorHandler", ahi);
			reg.rebind("questionHandler", qhi);
			System.out.println("Server started!!!");
			UserObserver timerTask = new UserObserver();
			Timer timer = new Timer(true);
			
			//execute task every 10 seconds
			timer.scheduleAtFixedRate(timerTask, 0, timerTask.getInterval() * 1000);
		} catch (RemoteException e) {
			
			e.printStackTrace();
		}

	}

}