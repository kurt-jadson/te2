package pf.framework.exception;

/**
 *
 * @author kurt
 */
public class WebException extends Exception {

	public WebException() {
	}

	public WebException(String message) {
		super(message);
	}

	public WebException(String message, Throwable ex) {
		super(message, ex);
	}

}
