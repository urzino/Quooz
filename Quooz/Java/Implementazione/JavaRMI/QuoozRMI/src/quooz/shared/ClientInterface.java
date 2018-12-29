package quooz.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The Interface ClientInterface represent the client.
 */
public interface ClientInterface extends Remote {
	
	/**
	 * Simple method used to check if the player is still reachable from the server.
	 *
	 * @return true, if is alive
	 * @throws RemoteException the remote exception
	 */
	public boolean isAlive() throws RemoteException;
}
