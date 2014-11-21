package pf.framework.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pf.framework.controller.ApplicationConstants;
import pf.framework.exception.UnforwardException;
import pf.framework.exception.UnredirectException;

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
	
	public User getLoggedUser() {
		return (User) getRequest().getSession().getAttribute(ApplicationConstants.USER_LOGGED_IN);
	}
	
	public void setLoggedUser(User user) {
		getRequest().getSession().setAttribute(ApplicationConstants.USER_LOGGED_IN, user);
	}

	public String getAction() {
		return action == null ? "" : action;
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
	
	public Integer getParameterInteger(String name) {
		String param = getParameter(name);
		return (param == null ? null : Integer.valueOf(param));
	}
	
	public Long getParameterLong(String name) {
		String param = getParameter(name);
		return (param == null ? null : Long.valueOf(param));
	}
	
	public BigDecimal getParameterBigDecimal(String name) {
		String param = getParameter(name);
		return (param == null ? null : BigDecimal.valueOf(Double.valueOf(param)));
	}
	
	public String getParameter(String name) {
		String param = parameters.get(name);
		return param == null ? getRequest().getParameter(name) : param;
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

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}
	
	public void forwardTo(String outcome) throws UnforwardException {
		try {
			request.getRequestDispatcher(outcome).forward(request, response);
		} catch(ServletException | IOException ex) {
			throw new UnforwardException(outcome, ex);
		}
	}
	
	public void redirectTo(WebContext webContext, String outcome) throws UnredirectException {
		try {
			if(outcome.charAt(0) == '/') {
				response.sendRedirect("/" + webContext.getApplicationContext() + outcome);
			} else {
				response.sendRedirect(outcome);
			}
		} catch (IOException ex) {
			throw new UnredirectException(outcome, ex);
		}
	}
	
}
