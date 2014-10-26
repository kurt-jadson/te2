package pf.framework.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kurt
 */
@WebServlet(name = "DecodeErrorController", urlPatterns = {"/DecodeErrorController"})
public class DecodeErrorController extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Exception ex = (Exception) request.getAttribute(ApplicationConstants.ERROR);
		try (PrintWriter out = response.getWriter()) {
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Erro!</title>");			
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Um erro ocorreu!</h1>");
			out.println("<div>");
			out.println("Detalhes do erro: " + ex.getLocalizedMessage());
			out.println("</div>");
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
