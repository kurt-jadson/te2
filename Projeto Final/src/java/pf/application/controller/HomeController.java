package pf.application.controller;

import javax.servlet.annotation.WebServlet;
import pf.framework.controller.AbstractController;
import pf.framework.exception.UnknownActionRequest;
import pf.framework.exception.WebException;
import pf.framework.web.WebContext;

/**
 *
 * @author kurt
 */
@WebServlet(urlPatterns = {"/HomeController"})
public class HomeController extends AbstractController {

	@Override
	protected void processRequest(WebContext context) throws WebException {
		switch(context.getAction()) {
			case "listar":
				listar(context);
				break;
			default:
				throw new UnknownActionRequest(context.getAction());
		}
	}

	private void listar(WebContext webContext) throws WebException {
		webContext.setAttribute("aluno", webContext.getParameter("aluno"));
		webContext.forwardTo("/listar.jsp");
	}

}