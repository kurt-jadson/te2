package pf.framework.filter;

import java.io.IOException;
import java.sql.Connection;
import java.util.Map.Entry;
import java.util.Objects;
import static javax.management.Query.value;
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
import pf.framework.navigation.NavigationHandler;
import pf.framework.navigation.URIContext;

/**
 *
 * @author kurt
 */
public class Router implements Filter {

	private NavigationHandler navigationHandler;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			navigationHandler = new NavigationHandler(filterConfig.getServletContext());
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
		String outcome = navigationHandler.parseNavigate(uriContext); //Decisão de navegação
		
		try (Connection connection = ConnectionFactory.getConnection()) {
			Objects.requireNonNull(connection, "Não foi possível estabelecer conexão com banco de dados.");
			request.setAttribute("connection", connection);
			request.setAttribute(ApplicationConstants.URI_CONTEXT, uriContext);
			request.setAttribute("parameters", uriContext.getParameters());
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
			dispatchTo("/DecodeErrorController", request, response);
		} catch (Exception ex) {
			//Simplesmente loga, não há mais o que fazer
			ex.printStackTrace();
		}
	}

	private void dispatchTo(String outcome, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestDispatcher dispatcher = request.getRequestDispatcher(outcome);
		dispatcher.forward(request, response);
	}

}
