package pf.application.controller;

import java.sql.Connection;
import javax.servlet.annotation.WebServlet;
import pf.application.entity.Usuario;
import pf.application.repository.UsuarioRepositorio;
import pf.framework.controller.AbstractController;
import pf.framework.exception.WebException;
import pf.framework.util.WebUtils;
import pf.framework.web.WebContext;

/**
 *
 * @author kurt
 */
@WebServlet(urlPatterns = {"/AuthenticationController"})
public class AuthenticationController extends AbstractController {

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
		try {
			Connection connection = WebUtils.getConnection(webContext.getRequest());
			UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio(connection);
			String username = webContext.getParameter("usuario");
			String password = webContext.getParameter("senha");
			Usuario usuario = usuarioRepositorio.buscarPorUsernamePassword(username, password);

			if (usuario == null) {
				webContext.redirectTo(webContext, "/login");
			} else {
				webContext.setLoggedUser(usuario);
				webContext.redirectTo(webContext, "/acervo");
			}
		} catch (Exception ex) {
			throw new WebException(ex.getLocalizedMessage(), ex);
		}
	}

	private void logout(WebContext webContext) throws WebException {
		webContext.getRequest().getSession().invalidate();
		webContext.redirectTo(webContext, "/login");
	}

	private void load(WebContext webContext) throws WebException {
		webContext.forwardTo("/login.jsp");
	}

}
