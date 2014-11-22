package pf.application.controller;

import java.sql.Connection;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import pf.application.entity.Atividade;
import pf.application.entity.Desenho;
import pf.application.entity.enums.Cor;
import pf.application.entity.enums.FormatoTela;
import pf.application.entity.enums.Legenda;
import pf.application.entity.enums.Pais;
import pf.application.repository.AtividadeRepositorio;
import pf.application.repository.DesenhoRepositorio;
import pf.framework.controller.AbstractController;
import pf.framework.exception.UnknownActionRequest;
import pf.framework.exception.WebException;
import pf.framework.util.WebUtils;
import pf.framework.web.WebContext;

/**
 *
 * @author kurt
 */
@WebServlet(urlPatterns = {"/DesenhoController"})
public class DesenhoController extends AbstractController {

	@Override
	protected void processRequest(WebContext context) throws WebException {
		switch (context.getAction()) {
			case "listar":
				listar(context);
				break;
			case "novo":
				novo(context);
				break;
			case "salvar":
				salvar(context);
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
			DesenhoRepositorio desenhoRepositorio = new DesenhoRepositorio(connection);
//			Integer codigo = webContext.getParameterInteger("codigo");
//			
			List<Desenho> desenhos;
//			if(codigo == null) {
				desenhos = desenhoRepositorio.buscarAcervo();
//			} else {
//				desenhos = new ArrayList<>();
//				atividades.add(repositorio.getAtividade(codigo));
//			}
//			
			webContext.setAttribute("desenhos", desenhos);
			webContext.forwardTo("/pages/listar.jsp");
		} catch (Exception ex) {
			throw new WebException(ex.getLocalizedMessage(), ex);
		}
	}
	
	private void novo(WebContext webContext) throws WebException {
		webContext.setAttribute("legendas", Legenda.values());
		webContext.setAttribute("cores", Cor.values());
		webContext.setAttribute("formatosTela", FormatoTela.values());
		webContext.setAttribute("paisesOrigem", Pais.values());
		webContext.forwardTo("/pages/novo.jsp");
	}

	private void salvar(WebContext webContext) throws WebException {
		try {
			Connection connection = WebUtils.getConnection(webContext.getRequest());
			DesenhoRepositorio desenhoRepositorio = new DesenhoRepositorio(connection);

			Desenho desenho = new Desenho();
			desenho.setTitulo(webContext.getParameter("titulo"));
			desenho.setVolume(webContext.getParameterInteger("volume"));
			desenho.setPreco(webContext.getParameterBigDecimal("preco"));
			desenho.setCor(Cor.valueOf(webContext.getParameter("cor")));
			desenho.setLegenda(Legenda.valueOf(webContext.getParameter("legenda")));
			desenho.setFormatoTela(FormatoTela.valueOf(webContext.getParameter("formatoTela")));
			desenho.setPaisOrigem(Pais.valueOf(webContext.getParameter("paisOrigem")));
			desenhoRepositorio.salvar(desenho);
			webContext.redirectTo(webContext, "/acervo");
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
