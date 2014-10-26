package pf.framework.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pf.framework.exception.UnforwardException;

/**
 *
 * @author kurt
 */
public class WebContext {

	private final HttpServletRequest request;
	private final HttpServletResponse response;
	
	private final String[] sections;
	private final String applicationContext;
	private String action;
	private Map<String, String> parameters;
	
	public WebContext(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		parameters = new HashMap<>();
		sections = request.getRequestURI().split("/");
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
	
	public String getAttributeString(String name) {
		return (String) getAttribute(name);
	}
	
	public Object getAttribute(String name) {
		return request.getAttribute(name);
	}
	
	public void setAttribute(String name, Object attribute) {
		request.setAttribute(name, attribute);
	}
	
	public String getParameter(String name) {
		return parameters.get(name);
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
	
	public void forwardTo(String outcome) throws UnforwardException {
		try {
			request.getRequestDispatcher(outcome).forward(request, response);
		} catch(ServletException | IOException ex) {
			throw new UnforwardException(outcome, ex);
		}
	}
	
}
