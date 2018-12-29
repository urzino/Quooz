package quooz.shared.system;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;

import quooz.shared.DatabaseProblemException;
import quooz.shared.question.Category;
import quooz.shared.question.Option;
import quooz.shared.question.Question;
import quooz.shared.user.Player;

public interface GameHandlerInterface extends Remote {
	
	/**
	 * Gets the question to be submitted to the player.
	 *
	 * @param player the player which want to play
	 * @return the chosen question
	 * @throws RemoteException the remote exception
	 * @throws DatabaseProblemException 
	 */
	public Question getQuestion(Player player)throws RemoteException, DatabaseProblemException;
	
	/**
	 * Answer question
	 * allows the player to answer a question
	 *
	 * @param question the question submitted to the player
	 * @param option the option chosen by the player
	 * @param player the player who answered the question
	 * @return true, if the answer is correct
	 * @throws RemoteException the remote exception
	 * @throws DatabaseProblemException 
	 */
	public boolean answerQuestion(Question question, Option option, Player player) throws RemoteException, DatabaseProblemException;
	
	/**
	 * Withdraws from the database the quetions answered
	 * correctly from the player. The map key is the questionID, 
	 * the String is the Question's text
	 * 
	 * @param player which make the request
	 * @return the list of questions with their ids
	 * @throws RemoteException
	 * @throws DatabaseProblemException 
	 */
	public LinkedHashMap<Integer, String> getCorrectAnswerHistory(Player player)throws RemoteException, DatabaseProblemException;
	
	/**
	 * Gets the leaderboard represented in the form <username,score>
	 *
	 * @return the leaderboard
	 * @throws RemoteException the remote exception
	 * @throws DatabaseProblemException 
	 */
	public LinkedHashMap<String, Integer> getLeaderboard()throws RemoteException, DatabaseProblemException;
	
	/**
	 * Gets Player correctness percentage for each Category of the game.
	 *
	 * @param player the player which requires to see his stats
	 * @return the categories stats
	 * @throws RemoteException the remote exception
	 * @throws DatabaseProblemException 
	 */
	public LinkedHashMap<Category,Integer> getCategoriesStats(Player player)throws RemoteException, DatabaseProblemException;
}
