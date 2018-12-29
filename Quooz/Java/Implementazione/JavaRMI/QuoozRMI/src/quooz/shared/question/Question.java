
package quooz.shared.question;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 * The Class Question
 * represents the informations of a question
 */
public class Question implements Serializable {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The question's id. */
	private int id;
	
	/** The question's text. */
	private String text;
	
	/** The question's difficulty level. */
	private Level difficultyLevel;
	
	/** The question's category. */
	private Category category;
	
	/** The question's options. */
	private ArrayList<Option> option;
	
	/** The question's author. */
	private String author;
	
	/** The question's image. */
	private ImageIcon image;

	/**
	 * Instantiates a new question.
	 *
	 * @param id the question's id
	 * @param txt the question's txt
	 * @param level the question's level
	 * @param category the question's category
	 * @param options the question'soptions
	 * @param author the question's author
	 * @param image the question's image
	 */
	public Question(int id, String txt, Level level, Category category, ArrayList<Option> options, String author,
			ImageIcon image) {
		setId(id);
		setText(txt);
		setDifficultyLevel(level);
		setCategory(category);
		setOptions(options);
		setImage(image);
		setAuthor(author);
	}

	/**
	 * Gets the question's author.
	 *
	 * @return the author
	 */
	public String getAuthor() {
		return this.author;
	}

	/**
	 * Sets the question's author.
	 *
	 * @param author the new author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * Gets the question's id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the question's id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the question's text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 *
	 * @param text t question'se new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the question's difficulty level.
	 *
	 * @return the difficulty level
	 */
	public Level getDifficultyLevel() {
		return difficultyLevel;
	}

	/**
	 * Sets the question's difficulty level.
	 *
	 * @param difficultyLevel the new difficulty level
	 */
	public void setDifficultyLevel(Level difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	/**
	 * Gets the question's category.
	 *
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * Sets the question's category.
	 *
	 * @param category the new category
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * Gets the question's options.
	 *
	 * @return the options
	 */
	public ArrayList<Option> getOptions() {
		return option;
	}

	/**
	 * Sets the question's options.
	 *
	 * @param options the new options
	 */
	public void setOptions(ArrayList<Option> options) {
		this.option = options;
	}

	/**
	 * Sets the question's image.
	 *
	 * @param image the new image
	 */
	public void setImage(ImageIcon image) {
		this.image = image;
	}

	/**
	 * Gets the question's image.
	 *
	 * @return the image
	 */
	public ImageIcon getImage() {
		return this.image;
	}

}
