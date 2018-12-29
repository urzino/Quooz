package quooz.server.user;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.codec.digest.DigestUtils;
import quooz.shared.ClientInterface;
import quooz.shared.DatabaseProblemException;
import quooz.shared.UserAlreadyLoggedException;
import quooz.shared.question.Level;
import quooz.shared.user.Player;
import quooz.shared.user.PlayerHandlerInterface;
import quooz.server.database.DatabaseConnection;
import quooz.server.system.log.Action;
import quooz.server.system.log.LogRecordHandler;


/**
 * The Class PlayerHandler manages everything related to players
 * from their registration to the elimination.
 */
public class PlayerHandler extends UnicastRemoteObject implements PlayerHandlerInterface {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/**
	 * HashMap which contains the list of the players currently logged in the
	 * game, represented by a paif of values <username,remoteClient>.
	 */
	public static HashMap<String, ClientInterface> loggedPlayers = new HashMap<String, ClientInterface>();

	/**
	 * Default constructor
	 * 
	 * @throws RemoteException
	 *             the remote exception
	 */
	protected PlayerHandler() throws RemoteException {
		super();
	}

	/**
	 * Unique instance of the PlayerHandler, it implements the singleton pattern
	 */
	private static PlayerHandler instance = null;

	/**
	 * @return the unique PlayerHandler instance
	 */
	public static synchronized PlayerHandler getInstance() {
		if (instance == null) {
			try {
				instance = new PlayerHandler();
			} catch (RemoteException e) {
				e.printStackTrace();
				return null;
			}
		}
		return instance;
	}

	/**
	 * Create an instance of the Player
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
	 * @param level
	 *            the Player's level
	 * @param score
	 *            the Player's score
	 * @param correctSequence
	 *            a number [-4,2] representing the correct(if positive)/wrong(if
	 *            negative) answer in a row
	 * @return Player
	 */
	public Player createPlayer(String name, String surname, Date date, String username, String pwd, String email,
			Level level, int score, int correctSequence) {
		Player player = new Player(name, surname, date, username, pwd, email, level, score, correctSequence);
		return player;
	}

	/**
	 * Create an instance of the player by his username
	 * 
	 * @param player
	 *            the usernmame of the player
	 * @throws DatabaseProblemException
	 */
	public Player getUpdatedPlayer(Player player) throws DatabaseProblemException {
		if(!PlayerHandler.getInstance().isRealPlayer(player)){
			//TODO
			return null;
		}
		
		Connection c = null;
		try {
			c = DatabaseConnection.getConnection();
			PreparedStatement ps = c.prepareStatement("SELECT * FROM player WHERE username=?");
			ps.setString(1, player.getUsername());

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Level level = Level.getLevelFromInt(rs.getInt("level"));
				player = createPlayer(rs.getString("name"), rs.getString("surname"), rs.getDate("dateOfBirth"),
						rs.getString("username"), rs.getString("password"), rs.getString("email"), level,
						rs.getInt("score"), rs.getInt("correctSequence"));

			} else {
				System.out.println("username sbagliato");
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
		return player;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * quooz.shared.user.PlayerHandlerInterface#signInPlayer(java.lang.String,
	 * java.lang.String, quooz.shared.ClientInterface)
	 */
	public Player signInPlayer(String user, String pwd, ClientInterface client)
			throws RemoteException, UserAlreadyLoggedException, DatabaseProblemException {
		if (loggedPlayers.containsKey(user)) {
			throw new UserAlreadyLoggedException(user, "player");
		}
		Player player = null;
		Connection c = null;
		try {
			c = DatabaseConnection.getConnection();
			PreparedStatement ps = c.prepareStatement("SELECT * FROM player WHERE username=? AND password=?");
			ps.setString(1, user);
			ps.setString(2, DigestUtils.md5Hex(pwd));
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Level level = Level.getLevelFromInt(rs.getInt("level"));
				player = createPlayer(rs.getString("name"), rs.getString("surname"), rs.getDate("dateOfBirth"),
						rs.getString("username"), rs.getString("password"), rs.getString("email"), level,
						rs.getInt("score"), rs.getInt("correctSequence"));
				loggedPlayers.put(user, client);
				LogRecordHandler.getInstance().createLogRecord(Action.SIGNIN, Timestamp.valueOf(LocalDateTime.now()),
						player);
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
		return player;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quooz.shared.user.PlayerHandlerInterface#signUpNewPlayer(java.lang.
	 * String, java.lang.String, java.sql.Date, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public void signUpNewPlayer(String name, String surname, Date date, String username, String password, String email)
			throws RemoteException, DatabaseProblemException {
		Connection c = null;
		try {
			c = DatabaseConnection.getConnection();
			PreparedStatement ps = c.prepareStatement(
					"INSERT INTO player (username,password,name,surname,dateOfBirth,email,score,level,correctSequence) VALUES (?,?,?,?,?,?,?,?,?);");
			ps.setString(1, username);
			ps.setString(2, DigestUtils.md5Hex(password));
			ps.setString(3, name);
			ps.setString(4, surname);
			ps.setDate(5, date);
			ps.setString(6, email);
			ps.setInt(7, 0);
			ps.setInt(8, 1);
			ps.setInt(9, 0);
			ps.executeUpdate();
			System.out.println("Registrazione avvenuta con successo!");
		} catch (SQLException e) {
			System.out.println("Sqlexception caught: " + e.getMessage());
			throw new DatabaseProblemException();
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseProblemException();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quooz.shared.user.PlayerHandlerInterface#isFieldAvailabe(java.lang.
	 * String, java.lang.String)
	 */
	public boolean isFieldAvailabe(String field, String value) throws RemoteException, DatabaseProblemException {
		Connection c = null;
		try {
			c = DatabaseConnection.getConnection();
			PreparedStatement ps = c.prepareStatement("SELECT " + field + " FROM player where " + field
					+ "=? UNION select " + field + " FROM author where " + field + "=?");
			ps.setString(1, value);
			ps.setString(2, value);
			if (ps.executeQuery().next())
				return false;
			else
				return true;
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

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quooz.shared.user.PlayerHandlerInterface#generateVerificationCode()
	 */
	public String generateVerificationCode() throws RemoteException {
		String code = "";
		String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789";
		for (int i = 0; i < 6; i++) {
			int position = (int) (Math.random() * 35);
			code += alphabet.charAt(position);
		}
		return code;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * quooz.shared.user.PlayerHandlerInterface#sendVerificationCode(java.lang.
	 * String, java.lang.String)
	 */
	public void sendVerificationCode(String email, String verificationCode) throws RemoteException {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.libero.it");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("swengquooz2016@libero.it", "Quooz2016");
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("swengquooz2016@libero.it"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("Quooz Activation Code");
			message.setText("This is your activation code: " + verificationCode);

			Transport.send(message);

			System.out.println("Email - sent");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * quooz.shared.user.PlayerHandlerInterface#signOutPlayer(quooz.shared.user.
	 * Player)
	 */
	public void signOutPlayer(Player player) throws RemoteException {
		LogRecordHandler.getInstance().createLogRecord(Action.SIGNOUT, Timestamp.valueOf(LocalDateTime.now()), player);
		loggedPlayers.remove(player.getUsername());
		System.out.println(player.getUsername() + " ha effettuato il logout");
		player = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * quooz.shared.user.PlayerHandlerInterface#unregisterPlayer(quooz.shared.
	 * user.Player)
	 */
	public void unregisterPlayer(Player p) throws RemoteException, DatabaseProblemException {
		Connection c = null;
		try {
			c = DatabaseConnection.getConnection();
			LogRecordHandler.getInstance().createLogRecord(Action.SIGNOUT, Timestamp.valueOf(LocalDateTime.now()), p);
			loggedPlayers.remove(p.getUsername());
			PreparedStatement s = c.prepareStatement("DELETE FROM player WHERE username=?");
			s.setString(1, p.getUsername());
			s.executeUpdate();
			System.out.println("Player eliminato");

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
	}

	/**
	 * Update player inside the database
	 *
	 * @param p
	 *            Player to be update on the db
	 * @throws DatabaseProblemException 
	 */
	public void updatePlayer(Player p) throws DatabaseProblemException {
		Connection c = null;
		try {
			c = DatabaseConnection.getConnection();
			PreparedStatement ps1 = c.prepareStatement("UPDATE player SET score=? where username=? ");
			ps1.setInt(1, p.getScore());
			ps1.setString(2, p.getUsername());
			PreparedStatement ps2 = c.prepareStatement("UPDATE player SET level=? where username=? ");
			ps2.setInt(1, p.getLevel().toInt());
			ps2.setString(2, p.getUsername());
			PreparedStatement ps3 = c.prepareStatement("UPDATE player SET correctSequence=? where username=? ");
			ps3.setInt(1, p.getCorrectSequence());
			ps3.setString(2, p.getUsername());
			ps1.executeUpdate();
			ps2.executeUpdate();
			ps3.executeUpdate();
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
	}
	
	public boolean isRealPlayer(Player player) throws DatabaseProblemException {
		Connection c= null;
		boolean verification=false;
		try {
			c= DatabaseConnection.getConnection();
			PreparedStatement ps= c.prepareStatement("SELECT player.username,player.password FROM player WHERE player.username=? and player.password=?;");
			ps.setString(1, player.getUsername());
			ps.setString(2, player.getPassword());
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
	
	public Player getPlayerByUsername(String playerUsername) throws DatabaseProblemException {
		
		Player player=null;
		Connection c = null;
		try {
			c = DatabaseConnection.getConnection();
			PreparedStatement ps = c.prepareStatement("SELECT * FROM player WHERE username=?");
			ps.setString(1, playerUsername);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Level level = Level.getLevelFromInt(rs.getInt("level"));
				player = createPlayer(rs.getString("name"), rs.getString("surname"), rs.getDate("dateOfBirth"),
						rs.getString("username"), rs.getString("password"), rs.getString("email"), level,
						rs.getInt("score"), rs.getInt("correctSequence"));

			} else {
				System.out.println("username sbagliato");
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
		return player;

	}

}
