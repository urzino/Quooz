package quooz.server.system.log;

import java.sql.Timestamp;

/**
 * The Class LogRecord
 * contains the informations of an action made
 * by the player
 */
public class LogRecord {
	
	/** The date when the action is accomplished. */
	private Timestamp date;
	
	/** The action made by the player. */
	private Action action;

	/**
	 * Instantiates a new log record.
	 *
	 * @param action the action made by the player
	 * @param date the date when the action is accomplished
	 */
	public LogRecord(Action action, Timestamp date) {
		this.date = date;
		this.action = action;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Timestamp getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}

	/**
	 * Gets the action.
	 *
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * Sets the action.
	 *
	 * @param action the new action
	 */
	public void setAction(Action action) {
		this.action = action;
	}

}
