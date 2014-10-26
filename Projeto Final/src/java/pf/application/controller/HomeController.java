package pf.application.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pf.framework.controller.AbstractController;

/**
 *
 * @author kurt
 */
@WebServlet(urlPatterns = {"/HomeController"})
public class HomeController extends AbstractController {

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setRequestResponse(request, response);
		
		switch(getAction()) {
			case "listar":
				listar();
				break;
			default:
				defaultAction();
		}
	}

	private void listar() throws ServletException, IOException {
		getRequest().getRequestDispatcher("/listar.jsp").forward(getRequest(), getResponse());
	}

	private void defaultAction() throws IOException {
		try (PrintWriter out = getResponse().getWriter()) {
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

}
