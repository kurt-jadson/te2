package pf.application.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import pf.application.entity.Atividade;
import pf.application.repository.AtividadeRepositorio;
import pf.framework.controller.AbstractController;
import pf.framework.exception.UnknownActionRequest;
import pf.framework.exception.WebException;
import pf.framework.util.WebUtils;
import pf.framework.web.WebContext;

/**
 *
 * @author kurt
 */
@WebServlet(urlPatterns = {"/HomeController"})
public class HomeController extends AbstractController {

	@Override
	protected void processRequest(WebContext context) throws WebException {
		switch (context.getAction()) {
			case "listar":
				listar(context);
				break;
			case "inserir":
				inserir(context);
				break;
			case "atualizar":
				atualizar(context);
				break;
			case "remover":
				remover(context);
				break;
			default:
				throw new UnknownActionRequest(context.getAction());
		}
	}

	private void listar(WebContext webContext) throws WebException {
		try {
			Connection connection = WebUtils.getConnection(webContext.getRequest());
			Integer codigo = webContext.getParameterInteger("codigo");
			AtividadeRepositorio repositorio = new AtividadeRepositorio(connection);
			
			List<Atividade> atividades;
			if(codigo == null) {
				atividades = repositorio.getAtividades();
			} else {
				atividades = new ArrayList<>();
				atividades.add(repositorio.getAtividade(codigo));
			}
			
			webContext.setAttribute("atividades", atividades);
			webContext.forwardTo("/pages/listar.jsp");
		} catch (Exception ex) {
			throw new WebException(ex.getLocalizedMessage(), ex);
		}
	}

	private void inserir(WebContext webContext) throws WebException {
		try {
			Connection connection = WebUtils.getConnection(webContext.getRequest());
			Integer codigo = webContext.getParameterInteger("codigo");

			AtividadeRepositorio repositorio = new AtividadeRepositorio(connection);
			Atividade atividade = new Atividade(codigo, "Atividade " + codigo, 10);
			repositorio.adicionarAtividade(atividade);
			webContext.redirectTo(webContext, "/home/listar");
		} catch (Exception ex) {
			throw new WebException(ex.getLocalizedMessage(), ex);
		}
	}

	private void atualizar(WebContext webContext) throws WebException {
		try {
			Connection connection = WebUtils.getConnection(webContext.getRequest());
			Integer codigo = webContext.getParameterInteger("codigo");

			AtividadeRepositorio repositorio = new AtividadeRepositorio(connection);
			Atividade atividade = repositorio.getAtividade(codigo);
			atividade.setDescricao("Atividade Atualizada " + atividade.getCodigo());
			repositorio.adicionarAtividade(atividade);
			webContext.redirectTo(webContext, "/home/listar");
		} catch (Exception ex) {
			throw new WebException(ex.getLocalizedMessage(), ex);
		}
	}

	private void remover(WebContext webContext) throws WebException {
		try {
			Connection connection = WebUtils.getConnection(webContext.getRequest());
			Integer codigo = webContext.getParameterInteger("codigo");

			AtividadeRepositorio repositorio = new AtividadeRepositorio(connection);
			Atividade atividade = repositorio.getAtividade(codigo);
			repositorio.removerAtividade(atividade);
			webContext.redirectTo(webContext, "/home/listar");
		} catch (Exception ex) {
			throw new WebException(ex.getLocalizedMessage(), ex);
		}
	}

}
