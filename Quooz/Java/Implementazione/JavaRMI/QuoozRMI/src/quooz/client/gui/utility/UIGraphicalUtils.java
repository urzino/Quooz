package quooz.client.gui.utility;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

/**
 * Contains some utility used in the GUI, like preferred Colors,Fonts and others.
 */
public class UIGraphicalUtils {

	private static Font practique = null;
	private static Font frutiger = null;
	private static Color myYellow=new Color(255, 212, 88);
	private static Color myBlue= new Color(5,56,87);
	
	/** The background. */
	private static Image background=new ImageIcon("img/wallpaper840x640.jpg").getImage(); 

	/**
	 * Gets the my blue.
	 *
	 * @return the my blue
	 */
	public static Color getMyBlue() {
		return myBlue;
	}

	private UIGraphicalUtils() {	
	}

	/**
	 * Load practique font of the specified size.
	 *
	 * @param size the size
	 * @return the derived font 
	 */
	public static Font loadPractiqueFont(float size) {
		try {
			//create the Font if it doesn't exist
			if(practique==null){
				InputStream is;
				is = new FileInputStream(new File("font/Practique.ttf"));				
				practique = Font.createFont(Font.TRUETYPE_FONT, is);
			}
			Font sizedFont = practique.deriveFont(size);
			return sizedFont;
		} catch (FontFormatException | IOException e) {
			return null; //if the font couldn't be loaded, return null
		}
	}

	/**
	 * Load frutiger font of the specified size.
	 *
	 * @param size the size for the font
	 * @return the derived font 
	 */
	public static Font loadFrutigerFont(float size) {
		try {
			if(frutiger==null){ //create the Font if it doesn't exist
				InputStream is;
				is = new FileInputStream(new File("font/frutiger.otf"));
				frutiger= Font.createFont(Font.TRUETYPE_FONT, is);
				
			}
			Font sizedFont = frutiger.deriveFont(size);
			return sizedFont;
		} catch (FontFormatException | IOException e) {
			return null; //if the font couldn't be loaded, return null
		}
	}

	/**
	 * Gets the my yellow.
	 *
	 * @return the my yellow
	 */
	public static Color getMyYellow() {
		return myYellow;
	}

	/**
	 * Gets the image for the GUI background.
	 *
	 * @return the background
	 */
	public static Image getBackground() {
		return background;
	}

	/**
	 * Sets the background.
	 *
	 * @param background the new background
	 */
	public static void setBackground(Image background) {
		UIGraphicalUtils.background = background;
	}

}
