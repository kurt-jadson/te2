package pf.application.controller;

import java.sql.Connection;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import pf.application.entity.Idioma;
import pf.application.entity.enums.SistemaSom;
import pf.application.repository.IdiomaRepositorio;
import pf.framework.controller.AbstractController;
import pf.framework.exception.UnknownActionRequest;
import pf.framework.exception.WebException;
import pf.framework.util.WebUtils;
import pf.framework.web.WebContext;

/**
 *
 * @author kurt
 */
@WebServlet(urlPatterns = {"/IdiomaController"})
public class IdiomaController extends AbstractController {

	private static final String LISTAR = "/pages/idioma/listar.jsp";
	private static final String FORMULARIO = "/pages/idioma/formulario.jsp";

	@Override
	protected void processRequest(WebContext webContext) throws WebException {
		switch (webContext.getAction()) {
			case "listar":
				listar(webContext);
				break;
			case "novo":
				novo(webContext);
				break;
			case "editar":
				editar(webContext);
				break;
			case "salvar":
				salvar(webContext);
				break;
			case "remover":
				remover(webContext);
				break;
			default:
				throw new UnknownActionRequest(webContext.getAction());
		}
	}

	private void listar(WebContext context) throws WebException {
		try {
			Connection connection = WebUtils.getConnection(context.getRequest());
			IdiomaRepositorio repositorio = new IdiomaRepositorio(connection);

			List<Idioma> idiomas = repositorio.buscarTodos();
			context.setAttribute("idiomas", idiomas);
			context.setAttribute("pageHeaderTitle", "Idiomas");
			context.forwardTo(LISTAR);
		} catch (Exception ex) {
			throw new WebException(ex.getLocalizedMessage(), ex);
		}
	}

	private void novo(WebContext webContext) throws WebException {
		try {
			webContext.setAttribute("sistemasSom", SistemaSom.values());
			webContext.setAttribute("idioma", new Idioma());
			webContext.setAttribute("pageHeaderTitle", "Novo idioma");
			webContext.forwardTo(FORMULARIO);
		} catch (Exception ex) {
			throw new WebException(ex.getLocalizedMessage(), ex);
		}
	}
	
	private void editar(WebContext webContext) throws WebException {
		try {
			Connection connection = WebUtils.getConnection(webContext.getRequest());
			IdiomaRepositorio repositorio = new IdiomaRepositorio(connection);
			Idioma idioma = repositorio.buscarPorId(webContext.getParameterInteger("codigo"));
			
			webContext.setAttribute("sistemasSom", SistemaSom.values());
			webContext.setAttribute("idioma", idioma);
			webContext.setAttribute("pageHeaderTitle", "Editando " + idioma.getNome());
			webContext.forwardTo(FORMULARIO);
		} catch (Exception ex) {
			throw new WebException(ex.getLocalizedMessage(), ex);
		}
	}	
	
	private void salvar(WebContext ctx) throws WebException {
		try {
			Connection connection = WebUtils.getConnection(ctx.getRequest());
			IdiomaRepositorio repositorio = new IdiomaRepositorio(connection);

			Idioma idioma = new Idioma();
			idioma.setId(ctx.getParameterInteger("codigo"));
			idioma.setNome(ctx.getParameter("nome"));
			idioma.setSistemaSom(ctx.getParameterEnum("sistemaSom", SistemaSom.class));
			repositorio.salvar(idioma);
			ctx.redirectTo(ctx, "/idiomas/listar");
		} catch (Exception ex) {
			throw new WebException(ex.getLocalizedMessage(), ex);
		}
	}	
	
	private void remover(WebContext webContext) throws WebException {
		try {
			Connection connection = WebUtils.getConnection(webContext.getRequest());
			IdiomaRepositorio repositorio = new IdiomaRepositorio(connection);
			Idioma idioma = repositorio.buscarPorId(webContext.getParameterInteger("codigo"));
			repositorio.remover(idioma);
			webContext.redirectTo(webContext, "/idiomas/listar");
		} catch (Exception ex) {
			throw new WebException(ex.getLocalizedMessage(), ex);
		}
	}	
	
}
