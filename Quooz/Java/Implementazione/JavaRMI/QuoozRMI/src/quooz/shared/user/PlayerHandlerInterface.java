package quooz.shared.user;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;

import quooz.shared.ClientInterface;
import quooz.shared.DatabaseProblemException;
import quooz.shared.UserAlreadyLoggedException;

public interface PlayerHandlerInterface extends Remote {

	/**
	 * Permit the player login
	 *
	 * @param user
	 *            the username for the login
	 * @param pwd
	 *            the password for the login
	 * @param client
	 *            the Client who make the access
	 * @return Player instance if the login succeed,null otherwise
	 * @throws RemoteException
	 *             the remote excpetion
	 * @throws UserAlreadyLoggedException,
	 *             if the Player is already logged from another host
	 * @throws DatabaseProblemException 
	 */
	public Player signInPlayer(String user, String pwd, ClientInterface client)
			throws RemoteException, UserAlreadyLoggedException, DatabaseProblemException;

	/**
	 * Sing up a new Player into the db
	 *
	 * @param name
	 *            the player's name
	 * @param surname
	 *            the player's surname
	 * @param date
	 *            the player's date of birth
	 * @param username
	 *            the player's authentication username
	 * @param pwd
	 *            the Player's authentication password
	 * @param email
	 *            the Player's email
	 * @throws RemoteException
	 *             the remote exception
	 * @throws DatabaseProblemException 
	 */
	public void signUpNewPlayer(String name, String surname, Date date, String username, String password, String email)
			throws RemoteException, DatabaseProblemException;

	/**
	 * check if the field marked is available for the specified value in the
	 * database
	 * 
	 * @param field
	 *            the field you want to check availability
	 * @param value
	 *            the value assigned at the field
	 * @return true, if the value is available for the specified field
	 * @throws RemoteException
	 *             the remote exception
	 * @throws DatabaseProblemException 
	 */
	public boolean isFieldAvailabe(String field, String value) throws RemoteException, DatabaseProblemException;

	/**
	 * Sign out player, removing it from the Client's list
	 * 
	 * @param player
	 *            the player who needs to be disconnected
	 * @throws RemoteException
	 *             the remote exception
	 */
	public void signOutPlayer(Player player) throws RemoteException;

	/**
	 * Send verification code to the specified email address.
	 *
	 * @param email
	 *            the receiver's email
	 * @param verificationCode
	 *            the code to be sent
	 * @throws RemoteException
	 *             the remote exception
	 */
	public void sendVerificationCode(String email, String verificationCode) throws RemoteException;

	/**
	 * Unregister the player from the game, removing it from the database
	 * 
	 * @param player
	 *            the player to be removed
	 * @throws RemoteException
	 *             the remote exception
	 * @throws DatabaseProblemException 
	 */
	public void unregisterPlayer(Player player) throws RemoteException, DatabaseProblemException;

	/**
	 * Generate the verification code, which is composed of 6 character choosen
	 * randomly from the following alphabet
	 * "abcdefghijklmnopqrstuvwxyz0123456789"
	 *
	 * @return the string
	 * @throws RemoteException
	 *             the remote exception
	 */
	public String generateVerificationCode() throws RemoteException;

	/**
	 * Gets current instance of the player in the database.
	 *
	 * @param username
	 *            the username
	 * @return the updated player
	 * @throws RemoteException
	 *             the remote exception
	 * @throws DatabaseProblemException 
	 */
	public Player getUpdatedPlayer(Player player) throws RemoteException, DatabaseProblemException;
}
