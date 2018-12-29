package quooz.shared.question;

import java.io.Serializable;
import javax.swing.ImageIcon;


/**
 * The Class Option
 * represents the informations of an option
 */
public class Option implements Serializable{
	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The option's id. */
	private int id;
	
	/** The option's text. */
	private String text;
	
	/** The option's correctness. */
	private boolean isCorrect;
	
	/** The option's image. */
	private ImageIcon image;

	/**
	 * Instantiates a new option.
	 *
	 * @param id the option's id
	 * @param text the option's text
	 * @param isCorrect the option's correctness
	 * @param image the option's image
	 */
	public Option(int id,String text, boolean isCorrect,ImageIcon image) {
		setId(id);
		setText(text);
		setCorrect(isCorrect);
		setImage(image);
	}

	

	/**
	 * Gets the option's id.
	 *
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Sets the option's id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the option's text.
	 *
	 * @return the text
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Sets the option's text.
	 *
	 * @param text the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Checks the option's correctness.
	 *
	 * @return true, if is correct
	 */
	public boolean isCorrect() {
		return this.isCorrect;
	}

	/**
	 * Sets the option's correctness.
	 *
	 * @param isCorrect the new correct
	 */
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	
	/**
	 * Sets the option's image.
	 *
	 * @param image the new image
	 */
	public void setImage(ImageIcon image){
		this.image=image;
	}
	
	/**
	 * Gets the option's image.
	 *
	 * @return the image
	 */
	public ImageIcon getImage(){
		return this.image;
	}

}
