package quooz.shared.user;

import java.io.Serializable;
import java.sql.Date;

public class Author extends User implements Serializable {
	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new author.
	 *
	 * @param name the author's name
	 * @param surname the author's surname
	 * @param date the author's date
	 * @param username the author's username
	 * @param password the author's password
	 * @param email the author's email
	 */
	public Author(String name, String surname, Date date, String username, String password, String email) {
		
		setName(name);
		setSurname(surname);
		setDateOfBirth(date); 
		setUsername(username);
		setPassword(password);
		setEmail(email);
	}
}
