package pf.framework.exception;

/**
 *
 * @author kurt
 */
public class UnforwardException extends WebException {

	public UnforwardException(String outcome) {
		super("Não foi possível redirecionar para " + outcome);
	}
	
	public UnforwardException(String outcome, Throwable ex) {
		super("Não foi possível redirecionar para " + outcome, ex);
	}

}
