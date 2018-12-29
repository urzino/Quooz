package quooz.shared.question;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The Class Answer
 * represents the answer given by a player
 */
public class Answer implements Serializable {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The timestamp representing when the question has been answered. */
	private Timestamp timestamp;
	
	/** The option representing the chosen option. */
	private Option option;

	/**
	 * Instantiates a new answer.
	 *
	 * @param option the chosen option
	 */
	public Answer(Option option) {
		this.option = option;
		timestamp = Timestamp.valueOf(LocalDateTime.now());
	}

	/**
	 * Gets the answered option.
	 *
	 * @return the option
	 */
	public Option getOption() {
		return option;
	}

	/**
	 * Sets the answered option.
	 *
	 * @param option the new option
	 */
	public void setOption(Option option) {
		this.option = option;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
