package quooz.server.system;

import quooz.shared.DatabaseProblemException;
import quooz.shared.question.*;
import quooz.shared.system.GameHandlerInterface;
import quooz.server.database.DatabaseConnection;
import quooz.server.question.QuestionHandler;
import quooz.server.system.log.Action;
import quooz.server.system.log.LogRecordHandler;
import quooz.server.user.PlayerHandler;
import quooz.shared.user.Player;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.LinkedHashMap;

/**
 * Charged to manage everything related to the game itself sending questions to
 * the player and generating the statistics he requires
 * 
 */
public class GameHandler extends UnicastRemoteObject implements GameHandlerInterface {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The unique instance. */
	private static GameHandler instance;

	/**
	 * Instantiates a new game handler.
	 *
	 * @throws RemoteException
	 *             the remote exception
	 */
	protected GameHandler() throws RemoteException {
		super();

	}

	/**
	 * Gets the single instance of GameHandler it implements the singleton
	 * pattern
	 *
	 * @return single instance of GameHandler
	 */
	public static GameHandler getInstance() {
		if (instance == null) {
			try {
				instance = new GameHandler();
			} catch (RemoteException e) {
				e.printStackTrace();
				return null;
			}
		}
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * quooz.shared.system.GameHandlerInterface#getQuestion(quooz.shared.user.
	 * Player)
	 */
	public Question getQuestion(Player player) throws RemoteException, DatabaseProblemException {
		if(!PlayerHandler.getInstance().isRealPlayer(player)){
			//TODO
			return null;
		}

		QuestionHandler qh = QuestionHandler.getInstance();
		// get the question for the player
		int questionId = qh.getGameQuestion(player);
		if (questionId == -1)// there are no question available
			return null;
		return qh.getQuestionById(questionId);
	}

	/**
	 * Creates the feedback regarding the answer's correctness.
	 *
	 * @param answer
	 *            the answer
	 * @return true, if the answer is correct, false otherwise
	 */
	protected boolean createFeedback(Answer answer) {
		boolean player = answer.getOption().isCorrect();
		if (player)
			return true;
		else
			return false;
	}

	/**
	 * Update the player's stats accordingly to the answer's result.
	 *
	 * @param player
	 *            the player who answered a a question
	 * @param level
	 *            the level of the answered question
	 * @param result
	 *            the result
	 * @throws DatabaseProblemException
	 */

	protected void updateStats(Player player, Level level, boolean result) throws DatabaseProblemException {
		if(!PlayerHandler.getInstance().isRealPlayer(player)){
			//TODO
			return;
		}
		int score = player.getScore();
		int sequence = player.getCorrectSequence(); // returns an int [-4,2]
													// representing a sequence
													// of correct(if
													// positive)/wrong(if
													// negative) answers in a
													// row
		if (result == false) {// if the answer is wrong
			score -= level.toInt();
			player.setScore(score);
			if (sequence > 0) // if the sequence is positive
				player.setCorrectSequence(-1);
			else // if the sequence is negative
				player.setCorrectSequence(sequence - 1);
			if (player.getCorrectSequence() == -5) { // if the level needs to be
														// decreased
				player.setCorrectSequence(0);
				player.setLevel(player.getLevel().decrease());
			}
		} else { // if the answer is correct
			score += level.toInt() * 2;
			player.setScore(score);
			if (sequence < 0) // if the sequence is negative
				player.setCorrectSequence(1);
			else // if the sequence is positive
				player.setCorrectSequence(sequence + 1);
			if (player.getCorrectSequence() == 3) { // if the level needs to be
													// increased
				player.setCorrectSequence(0);
				player.setLevel(player.getLevel().increase());
			}
		}
		PlayerHandler ph = PlayerHandler.getInstance();
		ph.updatePlayer(player);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * quooz.shared.system.GameHandlerInterface#answerQuestion(quooz.shared.
	 * question.Question, quooz.shared.question.Option,
	 * quooz.shared.user.Player)
	 */
	public boolean answerQuestion(Question question, Option option, Player player)
			throws RemoteException, DatabaseProblemException {
		if(!PlayerHandler.getInstance().isRealPlayer(player)){
			//TODO
			return false;
		}
		boolean result = false;
		Answer answer = new Answer(option);
		Connection c = null;
		try {
			c = DatabaseConnection.getConnection();
			// Insert answer into the db
			PreparedStatement ps = c
					.prepareStatement("INSERT INTO answer (timestamp, userPlayer, optionId) VALUES (?,?,?);");
			ps.setTimestamp(1, answer.getTimestamp());
			ps.setString(2, player.getUsername());
			ps.setInt(3, option.getId());
			ps.executeUpdate();
			// Check the player's answer correctness
			result = createFeedback(answer);

			updateStats(player, question.getDifficultyLevel(), result);
			LogRecordHandler.getInstance().createLogRecord(Action.ANSWER, answer.getTimestamp(), player);
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
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * quooz.shared.system.GameHandlerInterface#getCorrectAnswerHistory(quooz.
	 * shared.user.Player)
	 */
	public LinkedHashMap<Integer, String> getCorrectAnswerHistory(Player player) throws RemoteException, DatabaseProblemException {
		if(!PlayerHandler.getInstance().isRealPlayer(player)){
			//TODO
			return null;
		}
		LinkedHashMap<Integer, String> questions = new LinkedHashMap<>();
		Connection c = null;
		try {
			c = DatabaseConnection.getConnection();
			PreparedStatement ps = c.prepareStatement("SELECT question.id,question.text FROM "
					+ "((`option` JOIN  answer ON `option`.id=answer.optionId) JOIN question "
					+ "ON question.id=`option`.questionId) " + "WHERE answer.userPlayer=? and `option`.correct=1 ;");
			ps.setString(1, player.getUsername());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				questions.put(rs.getInt("id"), rs.getString("text"));
			}
			return questions;
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
	 * @see
	 * quooz.shared.system.GameHandlerInterface#getCategoriesStats(quooz.shared.
	 * user.Player)
	 */
	public LinkedHashMap<Category, Integer> getCategoriesStats(Player player) throws RemoteException, DatabaseProblemException {
		if(!PlayerHandler.getInstance().isRealPlayer(player)){
			//TODO
			return null;
		}
		Connection c = null;
		LinkedHashMap<Category, Integer> categoryStats = new LinkedHashMap<Category, Integer>();
		try {
			c = DatabaseConnection.getConnection();
			// Gets the stats related to a player from a view
			PreparedStatement ps = c.prepareStatement(
					"SELECT playeranswers.category,(countAllCorrect/countAll)*100 as percentage FROM (playeranswers JOIN playeranswerscorrect ON (playeranswers.category=playeranswerscorrect.category and playeranswers.userPlayer=playeranswerscorrect.userPlayer)) where playeranswers.userPlayer=?;");
			ps.setString(1, player.getUsername());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				categoryStats.put(Category.valueOf(rs.getString("category")), rs.getInt("percentage"));
			}

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
		return categoryStats;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quooz.shared.system.GameHandlerInterface#getLeaderboard()
	 */
	public LinkedHashMap<String, Integer> getLeaderboard() throws RemoteException, DatabaseProblemException {
		LinkedHashMap<String, Integer> leaderboard = new LinkedHashMap<>();
		Connection c = null;
		try {
			c = DatabaseConnection.getConnection();
			PreparedStatement ps = c.prepareStatement("SELECT username, score FROM  player ORDER BY score DESC;");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				leaderboard.put(rs.getString("username"), rs.getInt("score"));
			}
			return leaderboard;
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

}
