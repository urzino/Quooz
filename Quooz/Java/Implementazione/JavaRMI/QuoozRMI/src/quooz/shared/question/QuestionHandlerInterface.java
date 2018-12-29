package quooz.shared.question;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import quooz.shared.DatabaseProblemException;
import quooz.shared.user.Author;
import quooz.shared.user.Player;

public interface QuestionHandlerInterface extends Remote {
	
	/**
	 * Gets an HashMap which contains the list of Question's id 
	 * and their corresponding text
	 *
	 * @return the question list
	 * @throws RemoteException the remote exception
	 * @throws DatabaseProblemException 
	 */
	public LinkedHashMap<Integer, String> getQuestionList(Author author)throws RemoteException, DatabaseProblemException;
	
	/**
	 * Gets the question choosen from the possible player question
	 *
	 * @param player the player
	 * @return the questionID which will be proposed to the player, -1 
	 * 		if there aren't possible question
	 * @throws RemoteException the remote exception
	 * @throws DatabaseProblemException 
	 */
	public int getGameQuestion(Player player) throws RemoteException, DatabaseProblemException;
	
	/**
	 * Gets the question by id from the Database.
	 *
	 * @param question's id
	 * @return the question by id
	 * @throws RemoteException the remote exception
	 * @throws DatabaseProblemException 
	 */
	public Question getQuestionById(int id) throws RemoteException, DatabaseProblemException;
	
    /**
     * Inserts a new completely textual question into the Database.
     *
     * @param text the question's text
     * @param level the question's level
     * @param category the question's category
     * @param wrongOptionsTextual the question's wrong options textual
     * @param correctTextualOption the question's correct textual option
     * @param userAuthor the question's user author
     * @throws RemoteException the remote exception
     * @throws DatabaseProblemException 
     */
    public void createNewQuestion(String text,Level level,Category category,ArrayList<String> wrongOptionsTextual,String correctTextualOption,Author userAuthor) throws RemoteException, DatabaseProblemException;
	
    /**
     * Inserts a new completely image question into the Database.
     *
     * @param text the question's text
     * @param level the question's level
     * @param category the question's category
     * @param wrongOptionsImage the question's wrong options image
     * @param correctImageOption the question's correct image option
     * @param userAuthor the question's user author
     * @param questionImage the question's image
     * @throws RemoteException the remote exception
     * @throws DatabaseProblemException 
     */
    public void createNewQuestion(String text,Level level,Category category,ArrayList<File>wrongOptionsImage,File correctImageOption,Author userAuthor,File questionImage) throws RemoteException, DatabaseProblemException;
	
    /**
     * Inserts a new image question with textual options into the Database
     *
     * @param text the question's text
     * @param level the question's level
     * @param category the question's category
     * @param wrongOptionsTextual the question's wrong options textual
     * @param correctTextualOption the question's correct textual option
     * @param userAuthor the question's user author
     * @param questionImage the question's image
     * @throws RemoteException the remote exception
     * @throws DatabaseProblemException 
     */
    public void createNewQuestion(String text,Level level,Category category,ArrayList<String> wrongOptionsTextual,String correctTextualOption,Author userAuthor,File questionImage)throws RemoteException, DatabaseProblemException;
	
    /**
     * Inserts a new textual question with image options into the Database
     *
     * @param text the question's text
     * @param level the question's level
     * @param category the question's category
     * @param wrongOptionsImage the question's wrong options image
     * @param correctImage the question's correct image
     * @param userAuthor the question's user author
     * @throws RemoteException the remote exception
     * @throws DatabaseProblemException 
     */
    public void createNewQuestion(String text,Level level,Category category,ArrayList<File> wrongOptionsImage, File correctImage,Author userAuthor)throws RemoteException, DatabaseProblemException;
	
	/**
	 * Modify a question into Database
	 * 
	 * @param questionId the question to modify
	 * @param newTxt the updated question's text
	 * @param newLevel the updated question's level
	 * @param newCategory the updated question's category
	 * @param newOptionsTextual the updated textual options
	 * @param newOptionsImage the updated image options
	 * @param newAuthor the author updating the question
	 * @param newImage the updated question's image
	 * @param isImage
	 * @throws RemoteException
	 * @throws DatabaseProblemException 
	 */
	public void editQuestion(int questionId, String newTxt, Level newLevel, Category newCategory,
			LinkedHashMap<Integer, String> newOptionsTextual, LinkedHashMap<Integer, File> newOptionsImage,
			Author newAuthor, File newImage, boolean isImage) throws RemoteException, DatabaseProblemException;
	
	/**
	 * Delete a question from the Database.
	 *
	 * @param questionId the question's id to remove
	 * @throws RemoteException the remote exception
	 * @throws DatabaseProblemException 
	 */
	public void deleteQuestion(Integer questionId, Author author) throws RemoteException, DatabaseProblemException;

}
