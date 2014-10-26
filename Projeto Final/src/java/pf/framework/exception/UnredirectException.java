package pf.framework.exception;

/**
 *
 * @author kurt
 */
public class UnredirectException extends WebException {

	public UnredirectException(String outcome) {
		super("Não foi possível redirecionar para " + outcome);
	}
	
	public UnredirectException(String outcome, Throwable ex) {
		super("Não foi possível redirecionar para " + outcome, ex);
	}

}
