package pf.framework.controller;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pf.framework.navigation.URIContext;

/**
 *
 * @author kurt
 */
public abstract class AbstractController extends HttpServlet {

	private static final String DEFAULT_ACTION = "";
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	protected abstract void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public URIContext getURIContext() {
		return (URIContext) request.getAttribute(ApplicationConstants.URI_CONTEXT);
	}
	
	public String getAction() {
		String action = getURIContext().getAction();
		return action == null ? DEFAULT_ACTION : action;
	}
	
	public Map<String, String> getParameters() {
		return getURIContext().getParameters();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
	
}
