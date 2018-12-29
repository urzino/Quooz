package quooz.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import quooz.shared.ClientInterface;


public class Client extends UnicastRemoteObject implements ClientInterface{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	private String username;
	
	public Client(String username) throws RemoteException {
		this.setUsername(username);
	}

	@Override
	public boolean isAlive() throws RemoteException {
		return false;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
