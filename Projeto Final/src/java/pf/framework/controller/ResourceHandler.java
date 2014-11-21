package pf.framework.controller;

import javax.servlet.annotation.WebServlet;
import pf.framework.exception.WebException;
import pf.framework.web.WebContext;

/**
 *
 * @author kurt
 */
@WebServlet(urlPatterns = {"/ResourceHandler"})
public class ResourceHandler extends AbstractController {

	@Override
	protected void processRequest(WebContext context) throws WebException {
		//TODO: Futuramente os arquivos serão manipulados por este controlador
	}

}
