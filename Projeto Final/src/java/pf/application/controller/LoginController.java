package pf.application.controller;

import javax.servlet.annotation.WebServlet;
import pf.framework.controller.AbstractController;
import pf.framework.exception.WebException;
import pf.framework.web.WebContext;

/**
 *
 * @author kurt
 */
@WebServlet(urlPatterns = {"/LoginController"})
public class LoginController extends AbstractController {

	@Override
	protected void processRequest(WebContext context) throws WebException {
		switch (context.getAction()) {
			case "login":
				break;
			default:
				load(context);
		}
	}
	
	private void load(WebContext webContext) throws WebException {
		webContext.forwardTo("/login.jsp");
	}

}
