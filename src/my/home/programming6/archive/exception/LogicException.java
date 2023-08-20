package my.home.programming6.archive.exception;

public class LogicException extends Exception{

	private static final long serialVersionUID = 1L;

public LogicException() {
	super();
}
public LogicException(String message) {
	super(message);
}

public LogicException(Exception cause) {
	super(cause);
}

public LogicException(String message, Exception cause) {
	super(message, cause);
}
}
