package quooz.client.gui.utility;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Scanner;

import quooz.shared.question.QuestionHandlerInterface;
import quooz.shared.system.GameHandlerInterface;
import quooz.shared.user.AuthorHandlerInterface;
import quooz.shared.user.PlayerHandlerInterface;

/**
 * The Class RemoteHandlers contains the remote object that will be called from the client.
 */
public class RemoteHandlers {
	private AuthorHandlerInterface authorHandler;
	private PlayerHandlerInterface playerHandler;
	private GameHandlerInterface gameHandler;
	private QuestionHandlerInterface questionHandler;
	private static RemoteHandlers instance;

	public static RemoteHandlers getInstance() throws RemoteException, NotBoundException {
		if (instance == null)
			instance = new RemoteHandlers();
		return instance;
	}
	
	private RemoteHandlers() throws RemoteException, NotBoundException {
		Registry reg;
		reg = LocateRegistry.getRegistry(12345);
		Scanner s=new Scanner(System.in);
		String a = s.nextLine();
		HashMap<Integer, String> map=new HashMap<>();
		this.authorHandler = (AuthorHandlerInterface) reg.lookup("authorHandler");
		this.playerHandler = (PlayerHandlerInterface) reg.lookup("playerHandler");
		this.gameHandler = (GameHandlerInterface) reg.lookup("gameHandler");
		this.questionHandler = (QuestionHandlerInterface) reg.lookup("questionHandler");
	}

	public AuthorHandlerInterface getAuthorHandler() {
		return authorHandler;
	}


	public PlayerHandlerInterface getPlayerHandler() {
		return playerHandler;
	}


	public GameHandlerInterface getGameHandler() {
		return gameHandler;
	}


	public QuestionHandlerInterface getQuestionHandler() {
		return questionHandler;
	}

	
	/**
	 * Reset the remote instance: called every time there's a connection issue.
	 */
	public static void reset(){
		instance=null;
	}

}
