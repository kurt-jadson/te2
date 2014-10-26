package pf.framework.filter;

import java.io.IOException;
import java.sql.Connection;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pf.framework.controller.ApplicationConstants;
import pf.framework.factory.ConnectionFactory;
import pf.framework.navigation.URLHandler;
import pf.framework.navigation.URIContext;

/**
 *
 * @author kurt
 */
public class Router implements Filter {

	private static final Logger logger = Logger.getLogger(Router.class.getName());
	private URLHandler urlHandler;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			urlHandler = new URLHandler(filterConfig.getServletContext());
		} catch(Exception ex) {
			throw new ServletException(ex.getMessage());
		}
	}

	@Override
	public void doFilter(ServletRequest rq, ServletResponse rp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) rq;
		HttpServletResponse response = (HttpServletResponse) rp;
		initDefaults(request, response); //Inicializa os parâmetros padrões
		
		URIContext uriContext = new URIContext(request.getRequestURI());
		String outcome = urlHandler.parseUrl(uriContext); //Decisão de navegação
		
		try (Connection connection = ConnectionFactory.getConnection()) {
			Objects.requireNonNull(connection, "Não foi possível estabelecer conexão com banco de dados.");
			request.setAttribute(ApplicationConstants.CONNECTION, connection);
			request.setAttribute(ApplicationConstants.URI_CONTEXT, uriContext);
			request.setAttribute(ApplicationConstants.PARAMETERS, uriContext.getParameters());
			dispatchTo(outcome, request, response);
		} catch (Exception ex) {
			request.setAttribute(ApplicationConstants.ERROR, ex);
			dispatchToErrorPage(request, response);
		}
	}

	@Override
	public void destroy() {
	}

	private void initDefaults(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8"); //Setting content type
	}

	private void dispatchToErrorPage(HttpServletRequest request, HttpServletResponse response) {
		try {
			dispatchTo(urlHandler.getDefaultRoute(), request, response);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
		}
	}

	private void dispatchTo(String outcome, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/" + outcome);
		dispatcher.forward(request, response);
	}

}
