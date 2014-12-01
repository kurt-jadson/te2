package pf.application.controller;

import java.sql.Connection;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import pf.application.entity.Desenho;
import pf.application.entity.Episodio;
import pf.application.entity.Idioma;
import pf.application.entity.enums.Cor;
import pf.application.entity.enums.FormatoTela;
import pf.application.entity.enums.Legenda;
import pf.application.entity.enums.Pais;
import pf.application.entity.enums.Recomendacao;
import pf.application.repository.DesenhoRepositorio;
import pf.application.repository.DesenhoRepositorio;
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
@WebServlet(urlPatterns = {"/DesenhoController"})
public class DesenhoController extends AbstractController {

	private static final String ACERVO = "/acervo";
	private static final String LISTAR = "/pages/desenho/listar.jsp";
	private static final String FORMULARIO = "/pages/desenho/formulario.jsp";

	@Override
	protected void processRequest(WebContext context) throws WebException {
		context.setAttribute("menu", "acervo");
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
			case "buscar":
				buscar(context);
				break;
			default:
				throw new UnknownActionRequest(context.getAction());
		}
	}

	private void listar(WebContext webContext) throws WebException {
		try {
			Connection connection = WebUtils.getConnection(webContext.getRequest());
			DesenhoRepositorio desenhoRepositorio = new DesenhoRepositorio(connection);

			List<Desenho> desenhos = desenhoRepositorio.buscarAcervo();
			webContext.setAttribute("desenhos", desenhos);
			webContext.setAttribute("pageHeaderTitle", "Acervo");
			webContext.forwardTo(LISTAR);
		} catch (Exception ex) {
			throw new WebException(ex.getLocalizedMessage(), ex);
		}
	}

	private void novo(WebContext webContext) throws WebException {
		carregarListas(webContext);
		webContext.setAttribute("desenho", new Desenho());
		webContext.setAttribute("pageHeaderTitle", "Novo");
		webContext.forwardTo(FORMULARIO);
	}

	private void editar(WebContext webContext) throws WebException {
		try {
			Connection connection = WebUtils.getConnection(webContext.getRequest());
			DesenhoRepositorio repositorio = new DesenhoRepositorio(connection);

			Integer codigo = webContext.getParameterInteger("codigo");
			Desenho desenho = repositorio.buscarPorIdFetchListas(codigo);

			carregarListas(webContext);
			webContext.setAttribute("desenho", desenho);
			webContext.setAttribute("pageHeaderTitle", "Editando " + desenho.getTitulo());
			webContext.forwardTo(FORMULARIO);
		} catch (Exception ex) {
			throw new WebException(ex.getLocalizedMessage(), ex);
		}
	}

	private void carregarListas(WebContext webContext) throws WebException {
		try {
			Connection connection = WebUtils.getConnection(webContext.getRequest());
			IdiomaRepositorio idiomaRepositorio = new IdiomaRepositorio(connection);

			webContext.setAttribute("cores", Cor.values());
			webContext.setAttribute("recomendacoes", Recomendacao.values());
			webContext.setAttribute("legendas", Legenda.values());
			webContext.setAttribute("formatosTela", FormatoTela.values());
			webContext.setAttribute("paisesOrigem", Pais.values());
			webContext.setAttribute("idiomas", idiomaRepositorio.buscarTodos());
		} catch (Exception ex) {
			throw new WebException(ex.getLocalizedMessage(), ex);
		}
	}

	private void salvar(WebContext ctx) throws WebException {
		try {
			Connection connection = WebUtils.getConnection(ctx.getRequest());
			DesenhoRepositorio desenhoRepositorio = new DesenhoRepositorio(connection);
			IdiomaRepositorio idiomaRepositorio = new IdiomaRepositorio(connection);

			Desenho desenho = new Desenho();
			desenho.setId(ctx.getParameterInteger("codigo"));
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

			String[] idiomasString = ctx.getRequest().getParameterValues("idiomaId");
			if (idiomasString != null) {
				for (String s : idiomasString) {
					Idioma idioma = new Idioma();
					idioma.setId(Integer.valueOf(s));
					desenho.addIdioma(idioma);
				}
			}
			
			String[] episodiosNomeString = ctx.getRequest().getParameterValues("episodioNome");
			if(episodiosNomeString != null) {
				for(String s : episodiosNomeString) {
					Episodio episodio = new Episodio();
					episodio.setNome(s);
					desenho.addEpisodio(episodio);
				}
			}

			desenhoRepositorio.salvar(desenho);
			ctx.redirectTo(ctx, ACERVO);
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
			webContext.redirectTo(webContext, ACERVO);
		} catch (Exception ex) {
			throw new WebException(ex.getLocalizedMessage(), ex);
		}
	}

	private void buscar(WebContext webContext) throws WebException {
		try {
			Connection connection = WebUtils.getConnection(webContext.getRequest());
			String titulo = webContext.getParameter("qtitulo");

			DesenhoRepositorio repositorio = new DesenhoRepositorio(connection);
			List<Desenho> desenhos = repositorio.buscarPorTitulo(titulo);
			webContext.setAttribute("desenhos", desenhos);
			webContext.setAttribute("pageHeaderTitle", "Busca: " + titulo);
			webContext.forwardTo(LISTAR);
		} catch (Exception ex) {
			throw new WebException(ex.getLocalizedMessage(), ex);
		}
	}

}
