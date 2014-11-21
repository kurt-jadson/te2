package pf.application.controller;

import javax.servlet.annotation.WebServlet;
import pf.framework.controller.AbstractController;
import pf.framework.exception.WebException;
import pf.framework.web.User;
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
			case "signin":
				signin(context);
				break;
			case "logout":
				logout(context);
				break;
			default:
				load(context);
		}
	}

	private void signin(WebContext webContext) throws WebException {
		//TODO: Procurar no banco
		if ("Jadson".equals(webContext.getParameter("usuario"))
				&& "1".equals(webContext.getParameter("senha"))) {
			User u = new User() {
			};
			webContext.setLoggedUser(u);
			webContext.redirectTo(webContext, "/home/listar");
			return;
		}

		webContext.redirectTo(webContext, "/login");
	}

	private void logout(WebContext webContext) throws WebException {
		webContext.getRequest().getSession().invalidate();
		webContext.redirectTo(webContext, "/login");
	}

	private void load(WebContext webContext) throws WebException {
		webContext.forwardTo("/login.jsp");
	}

}
