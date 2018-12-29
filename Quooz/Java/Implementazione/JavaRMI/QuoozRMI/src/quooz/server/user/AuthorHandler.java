package quooz.server.user;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import org.apache.commons.codec.digest.DigestUtils;
import quooz.shared.ClientInterface;
import quooz.shared.DatabaseProblemException;
import quooz.shared.UserAlreadyLoggedException;
import quooz.shared.user.Author;
import quooz.shared.user.AuthorHandlerInterface;
import quooz.server.database.DatabaseConnection;

/**
 * The Class AuthorHandler manages everything related to the authors from the
 * login to the logout
 */
public class AuthorHandler extends UnicastRemoteObject implements AuthorHandlerInterface {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * HashMap which contains the list of the author currently
	 * logged in the game, represented by a pair of values <username,remoteClient>.
	 */
	public static HashMap<String, ClientInterface> loggedAuthors = new HashMap<String, ClientInterface>();

	/**
	 * Instantiates a new author handler.
	 *
	 * @throws RemoteException
	 *             the remote exception
	 */
	protected AuthorHandler() throws RemoteException {
		super();
	}

	/** The unique instance. */
	private static AuthorHandler instance = null;

	/**
	 * Gets the single instance of AuthorHandler it implements the singleton
	 * pattern
	 *
	 * @return single instance of AuthorHandler
	 */
	public static synchronized AuthorHandler getInstance() {

		if (instance == null) {
			try {
				instance = new AuthorHandler();
			} catch (RemoteException e) {

				e.printStackTrace();
				return null;
			}
		}
		return instance;
	}

	/**
	 * Creates the author.
	 *
	 * @param name
	 *            the author's name
	 * @param surname
	 *            the author's surname
	 * @param date
	 *            the author's date
	 * @param username
	 *            the author's username
	 * @param password
	 *            the author's password
	 * @param email
	 *            the author's email
	 * @return the author instance
	 */
	public Author createAuthor(String name, String surname, Date date, String username, String password, String email) {

		Author author = new Author(name, surname, date, username, password, email);
		return author;
	}
	
	/* (non-Javadoc)
	 * @see quooz.shared.user.AuthorHandlerInterface#signIn(java.lang.String, java.lang.String, quooz.shared.ClientInterface)
	 */
	public Author signIn(String user, String pwd, ClientInterface client)
			throws RemoteException, UserAlreadyLoggedException, DatabaseProblemException {
		if (loggedAuthors.containsKey(user)) { //check if the author is already logged from another host
			throw new UserAlreadyLoggedException(user, "author");
		}
		Author author = null;
		Connection c = null;
		try {
			c = DatabaseConnection.getConnection();
			PreparedStatement ps = c.prepareStatement("SELECT * FROM author WHERE username=? AND password=?");
			ps.setString(1, user);
			ps.setString(2, DigestUtils.md5Hex(pwd));
			ResultSet rs = ps.executeQuery();
			if (rs.next()) { //the author corresponding to the username and password
				author = createAuthor(rs.getString("name"), rs.getString("surname"), rs.getDate("dateOfBirth"),
						rs.getString("username"), rs.getString("password"), rs.getString("email"));
				loggedAuthors.put(user, client);
				System.out.println(user + " ha appena effettuato il login");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseProblemException();
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseProblemException();
			}
		}

		return author; //null if the author doesn't exists
	}

	/* (non-Javadoc)
	 * @see quooz.shared.user.AuthorHandlerInterface#signOutAuthor(quooz.shared.user.Author)
	 */
	public void signOutAuthor(Author author) throws RemoteException {
		System.out.println(author.getUsername() + " ha effettuato il logout");
		loggedAuthors.remove(author.getUsername());//remove author from the host logged in
		author = null;
	}
	
	public boolean isRealAuthor(Author author) throws DatabaseProblemException {
		Connection c= null;
		boolean verification=false;
		try {
			c= DatabaseConnection.getConnection();
			PreparedStatement ps= c.prepareStatement("SELECT author.username,author.password FROM author WHERE author.username=? and author.password=?;");
			ps.setString(1, author.getUsername());
			ps.setString(2, author.getPassword());
			verification = ps.executeQuery().next();
		} catch (SQLException e) {			
			e.printStackTrace();
			throw new DatabaseProblemException();
		}finally{
			try {
				c.close();
			} catch (SQLException e) {				
				e.printStackTrace();
				throw new DatabaseProblemException();
			}
		}
		
		return verification;
	}
}
