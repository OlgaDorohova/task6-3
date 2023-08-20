package my.home.programming6.archive.exception;

public class DaoException extends Exception{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public DaoException() {
super();	
}

public DaoException(String message) {
	super(message);
}

public DaoException(Exception cause) {
	super(cause);
}

public DaoException(String message, Exception cause) {
	super(message, cause);
}
}
