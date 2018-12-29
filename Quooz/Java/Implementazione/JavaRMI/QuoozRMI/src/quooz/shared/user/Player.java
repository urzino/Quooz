package quooz.shared.user;

import java.io.Serializable;
import java.sql.Date;

import quooz.shared.question.Level;

public class Player extends User implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The player's level. */
	private Level level;

	/** The player's score. */
	private int score;

	/** The player's correct sequence. */
	private int correctSequence;

	/**
	 * Instantiates a new player.
	 *
	 * @param name
	 *            the player's name
	 * @param surname
	 *            the player's surname
	 * @param date
	 *            the player's date
	 * @param username
	 *            the player's username
	 * @param password
	 *            the player's password
	 * @param email
	 *            the player's email
	 * @param level
	 *            the player's level
	 * @param score
	 *            the player's score
	 * @param correctSequence
	 *            a number [-4,2] representing the correct(if positive)/wrong(if
	 *            negative) answer in a row
	 */
	public Player(String name, String surname, Date date, String username, String password, String email, Level level,
			int score, int correctSequence) {
		setName(name);
		setSurname(surname);
		setDateOfBirth(date);
		setUsername(username);
		setPassword(password);
		setEmail(email);
		setLevel(level);
		setScore(score);
		setCorrectSequence(correctSequence);
	}

	/**
	 * Gets the player's level.
	 *
	 * @return the level
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * Sets the player's level.
	 *
	 * @param level
	 *            the new level
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

	/**
	 * Gets the player's score.
	 *
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Sets the player's score.
	 *
	 * @param score
	 *            the new score
	 */
	public void setScore(int score) {
		this.score = score;
		if (this.score < 0)
			this.score = 0;
	}

	/**
	 * Gets the correct player's sequence.
	 *
	 * @return the correct sequence
	 */
	public int getCorrectSequence() {
		return correctSequence;
	}

	/**
	 * Sets the correct player's sequence.
	 *
	 * @param correctSequence
	 *            the new correct sequence
	 */
	public void setCorrectSequence(int correctSequence) {
		this.correctSequence = correctSequence;
	}
}
