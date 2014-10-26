package pf.framework.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pf.framework.exception.WebException;
import pf.framework.util.WebUtils;
import pf.framework.web.WebContext;

/**
 *
 * @author kurt
 */
public abstract class AbstractController extends HttpServlet {

	protected abstract void processRequest(WebContext webContext) throws WebException;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			WebContext context = WebUtils.getWebContext(request);
			processRequest(context);
		} catch (WebException ex) {
			throw new ServletException(ex.getMessage(), ex);
		}
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
