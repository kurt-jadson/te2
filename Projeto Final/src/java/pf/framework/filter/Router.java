package pf.framework.filter;

import java.io.IOException;
import java.sql.Connection;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pf.framework.controller.ApplicationConstants;
import pf.framework.exception.UnforwardException;
import pf.framework.factory.ConnectionFactory;
import pf.framework.web.URLHandler;
import pf.framework.web.WebContext;

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
		
		WebContext webContext = new WebContext(request, response);
		String outcome = urlHandler.parseUrl(webContext); //Decisão de navegação
		
		try (Connection connection = ConnectionFactory.getConnection()) {
			Objects.requireNonNull(connection, "Não foi possível estabelecer conexão com banco de dados.");
			request.setAttribute(ApplicationConstants.CONNECTION, connection);
			request.setAttribute(ApplicationConstants.WEB_CONTEXT, webContext);
			webContext.forwardTo("/" + outcome);
		} catch (Exception ex) {
			request.setAttribute(ApplicationConstants.ERROR, ex);
			dispatchToErrorPage(webContext);
		}
	}

	@Override
	public void destroy() {
	}

	private void initDefaults(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8"); //Setting content type
	}

	private void dispatchToErrorPage(WebContext webContext) {
		try {
			webContext.forwardTo("/" + urlHandler.getDefaultRoute());
		} catch (UnforwardException ex) {
			logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
		}
	}

}