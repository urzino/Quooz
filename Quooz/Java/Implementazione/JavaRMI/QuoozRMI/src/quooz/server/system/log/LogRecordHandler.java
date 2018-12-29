package quooz.server.system.log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import quooz.server.database.DatabaseConnection;
import quooz.shared.DatabaseProblemException;
import quooz.shared.user.Player;

/**
 * The Class LogRecordHandler is charged of the log records management from the
 * creation to their insertion to the Database
 */
public class LogRecordHandler {

	/** The unique instance. */
	private static LogRecordHandler instance;

	/**
	 * Gets the single instance of LogRecordHandler. implements the singleton
	 * pattern
	 *
	 * @return single instance of LogRecordHandler
	 */
	public static LogRecordHandler getInstance() {
		if (instance == null)
			instance = new LogRecordHandler();
		return instance;
	}

	/**
	 * Creates the log record.
	 *
	 * @param action
	 *            the action accomplished
	 * @param date
	 *            the date representing when the action is accomplished
	 * @param player
	 *            the player who make the action
	 * @return the log record
	 */
	public LogRecord createLogRecord(Action action, Timestamp date, Player player) {
		LogRecord logrecord = new LogRecord(action, date);
		addLogRecord(logrecord, player);
		return logrecord;
	}

	/**
	 * Adds the logrecord to the Database.
	 *
	 * @param logrecord
	 *            the logrecord to be added
	 * @param player
	 *            the player who made the action
	 */
	private void addLogRecord(LogRecord logrecord, Player player) {
		Connection c = null;
		try {
			c = DatabaseConnection.getConnection();
			PreparedStatement ps = c.prepareStatement("INSERT INTO log (userPlayer,date,action) VALUES (?,?,?);");
			ps.setString(1, player.getUsername());
			ps.setTimestamp(2, logrecord.getDate());
			ps.setString(3, logrecord.getAction().toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Sqlexception caught: " + e.getMessage());
		} catch (DatabaseProblemException e) {
			System.out.println("E' stato riscontrato un problema nell'inserimento nel Database di un LOG");
		} finally {
			try {
				c.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return;
	}
}
