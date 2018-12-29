package quooz.shared;

public class DatabaseProblemException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {

		return "E' stato riscontrato un problema col Database!";

	}

}
