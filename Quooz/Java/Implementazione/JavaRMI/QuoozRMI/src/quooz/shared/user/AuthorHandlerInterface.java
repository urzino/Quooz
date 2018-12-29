package quooz.shared.user;

import java.rmi.Remote;
import java.rmi.RemoteException;

import quooz.shared.ClientInterface;
import quooz.shared.DatabaseProblemException;
import quooz.shared.UserAlreadyLoggedException;

public interface AuthorHandlerInterface extends Remote {

	/**
	 * Allows to the author to access the game
	 *
	 * @param user
	 *            the author's username
	 * @param pwd
	 *            the author's password
	 * @param client
	 *            the author's client
	 * @return the author
	 * @throws RemoteException
	 *             the remote exception
	 * @throws UserAlreadyLoggedException
	 *             the user already logged exception
	 * @throws DatabaseProblemException 
	 */
	public Author signIn(String user, String pwd, ClientInterface client)
			throws RemoteException, UserAlreadyLoggedException, DatabaseProblemException;

	/**
	 * Sign out author.
	 *
	 * @param author
	 *            the author who wants to logout
	 * @throws RemoteException
	 *             the remote exception
	 */
	public void signOutAuthor(Author author) throws RemoteException;
}
