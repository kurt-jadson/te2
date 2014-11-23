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
import pf.application.entity.enums.Recomendacao;
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
			case "editar":
				editar(context);
				break;
			case "salvar":
				salvar(context);
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
		carregarListas(webContext);
		webContext.setAttribute("desenho", new Desenho());
		webContext.forwardTo("/pages/formulario.jsp");
	}
	
	private void editar(WebContext webContext) throws WebException {
		try {
			Connection connection = WebUtils.getConnection(webContext.getRequest());
			DesenhoRepositorio repositorio = new DesenhoRepositorio(connection);
			
			Integer codigo = webContext.getParameterInteger("codigo");
			Desenho desenho = repositorio.buscarPorId(codigo);

			carregarListas(webContext);
			webContext.setAttribute("desenho", desenho);
			webContext.forwardTo("/pages/formulario.jsp");
		} catch (Exception ex) {
			throw new WebException(ex.getLocalizedMessage(), ex);
		}
	}
	
	private void carregarListas(WebContext webContext) {
		webContext.setAttribute("cores", Cor.values());
		webContext.setAttribute("recomendacoes", Recomendacao.values());
		webContext.setAttribute("legendas", Legenda.values());
		webContext.setAttribute("formatosTela", FormatoTela.values());
		webContext.setAttribute("paisesOrigem", Pais.values());
	}

	private void salvar(WebContext ctx) throws WebException {
		try {
			Connection connection = WebUtils.getConnection(ctx.getRequest());
			DesenhoRepositorio desenhoRepositorio = new DesenhoRepositorio(connection);

			Desenho desenho = new Desenho();
			desenho.setTitulo(ctx.getParameter("titulo"));
			desenho.setVolume(ctx.getParameterInteger("volume"));
			desenho.setTempo(ctx.getParameterInteger("tempo"));
			desenho.setCor(Cor.valueOf(ctx.getParameter("cor")));
			desenho.setAnoLancamento(ctx.getParameterInteger("ano"));
			desenho.setRecomendacao(ctx.getParameterEnum("recomendacao", Recomendacao.class));
			desenho.setRegiaoDvd(ctx.getParameterInteger("regiao"));
			desenho.setLegenda(ctx.getParameterEnum("legenda", Legenda.class));
			desenho.setFormatoTela(ctx.getParameterEnum("formatoTela", FormatoTela.class));
			desenho.setPaisOrigem(ctx.getParameterEnum("paisOrigem", Pais.class));
			desenho.setDescricao(ctx.getParameter("descricao"));
			desenho.setPreco(ctx.getParameterBigDecimal("preco"));
			desenhoRepositorio.salvar(desenho);
			ctx.redirectTo(ctx, "/acervo");
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

			DesenhoRepositorio repositorio = new DesenhoRepositorio(connection);
			Desenho desenho = repositorio.buscarPorId(codigo);
			repositorio.remover(desenho);
			webContext.redirectTo(webContext, "/acervo");
		} catch (Exception ex) {
			throw new WebException(ex.getLocalizedMessage(), ex);
		}
	}

}
