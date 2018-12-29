package quooz.server.question;

import java.util.ArrayList;
import javax.swing.ImageIcon;

import quooz.shared.question.Category;
import quooz.shared.question.Level;
import quooz.shared.question.Option;
import quooz.shared.question.Question;

/**
 * The Class QuestionCreator
 * is charged to create a new Question.
 */
public class QuestionCreator {

	/** The unique instance. */
	private static QuestionCreator instance = null;

	/**
	 * Gets the single instance of QuestionCreator.
	 * it implements the singleton pattern
	 *
	 * @return single instance of QuestionCreator
	 */
	public static QuestionCreator getInstance() {
		if (instance == null)
			instance= new QuestionCreator();
		return instance;
	}

	/**
	 * Creates the question.
	 *
	 * @param id the question's id
	 * @param txt the question's txt
	 * @param level the question's level
	 * @param category the question's category
	 * @param option the question's options
	 * @param author the question's author
	 * @param image the question's image
	 * @return the question
	 */
	public Question createQuestion(int id, String txt, Level level, Category category, ArrayList<Option> option,String author, ImageIcon image) {
		return new Question(id, txt, level, category, option,author,image);
	}

	
}
