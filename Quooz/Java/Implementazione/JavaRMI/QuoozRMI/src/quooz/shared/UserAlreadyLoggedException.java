package quooz.shared;

/**
 * The Class UserAlreadyLoggedExcpetion
 * represents the exception generated
 * when an user tries to make an access
 * with the credentials of an already logged user.
 */
public class UserAlreadyLoggedException extends Exception {
	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The user which is attempting to login. */
	private String user;
	
	/** The user's role (author/player). */
	private String role;
	
	public UserAlreadyLoggedException(String user,String role) {
		
		this.user=user;
		this.role=role;
	}
	
	@Override
	public String getMessage() {
		
		if(role.equals("author")){
			return user + " è gia connesso da un'altra postazione!\r\nNon puoi loggare!";
		}
		else{
			return user + " sta gia giocando";
			
		}
	}
	

}
