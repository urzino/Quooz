package quooz.server.question;

import javax.swing.ImageIcon;
import quooz.shared.question.Option;

/**
 * The Class OptionCreator is charged to create a new Option.
 */
public class OptionCreator {

	/** The unique instance. */
	private static OptionCreator instance;

	/**
	 * Gets the single instance of OptionCreator. it implements the singleton
	 * pattern
	 *
	 * @return single instance of OptionCreator
	 */
	public static OptionCreator getInstance() {
		if (instance == null)
			instance = new OptionCreator();
		return instance;
	}

	/**
	 * Creates the option.
	 *
	 * @param id
	 *            the option's id
	 * @param text
	 *            the option's text
	 * @param isCorrect
	 *            the option's correctness
	 * @param image
	 *            the option's image
	 * @return the option
	 */
	public Option createOption(int id, String text, boolean isCorrect, ImageIcon image) {
		Option option = new Option(id, text, isCorrect, image);

		return option;
	}

}
