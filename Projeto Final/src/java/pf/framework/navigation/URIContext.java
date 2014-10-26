package pf.framework.navigation;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kurt
 */
public class URIContext {

	private final String[] sections;
	private final String applicationContext;
	private String action;
	private Map<String, String> parameters;
	
	public URIContext(String uri) {
		parameters = new HashMap<>();
		sections = uri.split("/");
		applicationContext = sections[1];
	}

	public String getApplicationContext() {
		return applicationContext;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
	
	public String getRequestString() {
		StringBuilder requestString = new StringBuilder();
		for(int i = 2, j = sections.length; i < j; i++) {
			requestString.append("/").append(sections[i]);
		}
		
		return requestString.toString();
	}
	
}
