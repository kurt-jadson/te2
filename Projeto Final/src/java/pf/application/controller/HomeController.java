package pf.application.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pf.framework.controller.ApplicationConstants;
import pf.framework.navigation.URIContext;

/**
 *
 * @author kurt
 */
@WebServlet(urlPatterns = {"/HomeController"})
public class HomeController extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		URIContext context = (URIContext) request.getAttribute(ApplicationConstants.URI_CONTEXT);
		switch(context.getAction()) {
			case "listar":
				listar(request, response);
				break;
			default:
				defaultAction(request, response);
		}
	}

	private void listar(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.getRequestDispatcher("/listar.jsp").forward(request, response);
	}

	private void defaultAction(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		try (PrintWriter out = response.getWriter()) {
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Application</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Bem vindo ao projeto!</h1>");
			out.println("</body>");
			out.println("</html>");
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

}