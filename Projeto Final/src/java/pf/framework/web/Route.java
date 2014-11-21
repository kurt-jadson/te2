package pf.framework.web;

/**
 *
 * @author kurt
 */
public class Route {

	private String from;
	private String to;
	private String action;
	private boolean ignoreCase;

	public Route(String from, String to, boolean ignoreCase) {
		this.from = from;
		this.to = to;
		this.ignoreCase = ignoreCase;
		this.action = "";
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}
	
}
