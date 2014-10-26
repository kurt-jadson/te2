package pf.framework.exception;

/**
 *
 * @author kurt
 */
public class UnknownActionRequest extends RuntimeException {

	public UnknownActionRequest() {
		super("Ação não conhecida.");
	}
	
	public UnknownActionRequest(String action) {
		super(action + " não encontrada.");
	}
	
	
}
