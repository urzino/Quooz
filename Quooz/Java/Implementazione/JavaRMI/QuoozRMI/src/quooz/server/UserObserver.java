package quooz.server;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;

import quooz.server.system.log.Action;
import quooz.server.system.log.LogRecordHandler;
import quooz.server.user.AuthorHandler;
import quooz.server.user.PlayerHandler;
import quooz.shared.ClientInterface;
import quooz.shared.DatabaseProblemException;
import quooz.shared.user.Player;

/**
 * A task that allows the server to
 * know if the connected clients are still reachable
 * 
 */
public class UserObserver extends TimerTask {
	
	/** The interval to wait before checking user's presence. */
	private final int interval=10;

	public UserObserver() {
		
	}

	@Override
	public  void run() {
		//For each Client logged into the game, a simple method is called to verify if 
		// he is still connected: if not, the client is removed from the list
		Iterator<Map.Entry<String, ClientInterface>> entries = PlayerHandler.loggedPlayers.entrySet().iterator();
		while (entries.hasNext()) {
		    Map.Entry<String, ClientInterface> entry = entries.next();		   
		    try{
		    	entry.getValue().isAlive();
		    }catch(RemoteException e){ //The player has lost his connection to the server
		    	Player removedPlayer = null;
				try {
					removedPlayer = PlayerHandler.getInstance().getPlayerByUsername(entry.getKey());
				} catch (DatabaseProblemException e1) {
					System.out.println("E' stato riscontrato un problema nell'ottenere un Player tentando di rimuoverlo dalla lista perché irraggiungibile");
				}
		    	LogRecordHandler.getInstance().createLogRecord(Action.SIGNOUT, new Timestamp(System.currentTimeMillis()), removedPlayer);
		    	entries.remove();
		    	System.out.println(entry.getKey()+" è stato rimosso perchè irraggiungibile!");
		    }
		}
		Iterator<Map.Entry<String, ClientInterface>> entries2 = AuthorHandler.loggedAuthors.entrySet().iterator();
		while (entries2.hasNext()) {
		    Map.Entry<String, ClientInterface> entry = entries2.next();		   
		    try{
		    	entry.getValue().isAlive();
		    }catch(RemoteException e){ //The player has lost his connection to the server
		    	entries2.remove();
		    	System.out.println(entry.getKey()+" è stato rimosso perchè irraggiungibile!");
		    }
		}
	}

	public int getInterval() {
		return interval;
	}

}
