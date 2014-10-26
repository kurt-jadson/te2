package pf.framework.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author kurt
 */
public class URLHandler {

	private static final Logger logger = Logger.getLogger(URLHandler.class.getName());
	private final Document document;
	private String defaultRoute;
	private List<Route> routes;

	public URLHandler(ServletContext servletContext) throws Exception {
		defaultRoute = "DecodeErrorController";
		routes = new ArrayList<>();

		try {
			document = readXml(servletContext);
			configureDefaultRoute();
			readRoutes();
		} catch (ParserConfigurationException | SAXException | IOException ex) {
			String mensagem = "Configuração de rotas não foi possível de ser interpretada.";
			logger.log(Level.SEVERE, mensagem, ex);
			throw new Exception(mensagem);
		}
	}

	private Document readXml(ServletContext context)
			throws ParserConfigurationException, SAXException, IOException {
		File xmlRoutes = new File(context.getRealPath("/WEB-INF/routes.xml"));
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlRoutes);
		doc.getDocumentElement().normalize();
		return doc;
	}

	private void configureDefaultRoute() {
		Node errorNode = document.getElementsByTagName("error").item(0);
		Element errorElement = (Element) errorNode;
		Node defaultErrorNavigation = errorElement.getElementsByTagName("default").item(0);
		Element defaultErrorNavigationElement = (Element) defaultErrorNavigation;
		defaultRoute = defaultErrorNavigationElement.getFirstChild().getNodeValue();
	}

	private void readRoutes() {
		NodeList nodeList = document.getElementsByTagName("route");
		for (int i = 0, j = nodeList.getLength(); i < j; i++) {
			Element routeNode = (Element) nodeList.item(i);
			Node from = routeNode.getElementsByTagName("from").item(0);
			Node to = routeNode.getElementsByTagName("to").item(0);
			
			String caseSensitive = routeNode.getAttribute("case-sensitive");
			boolean ignoreCase = !caseSensitive.equals("") && !Boolean.parseBoolean(caseSensitive);
			String fromStr = from.getFirstChild().getNodeValue();
			String toStr = to.getFirstChild().getNodeValue();
			Route route = new Route(fromStr, toStr, ignoreCase);
			routes.add(route);
		}
	}

	public String parseUrl(WebContext context) {
		String requestString = context.getRequestString();
		String[] paths = requestString.split("/");
		int action = 1;
		int controller = 0; 

		for (int i = 0, j = paths.length; i < j; i++) {
			String outcomeString = getOutcomeString(i, paths);
			for (int k = 0, l = routes.size(); k < l; k++) {
				Route route = routes.get(k);
				String from = route.getFrom();
				String outcome = outcomeString;
				
				if(route.isIgnoreCase()) {
					from = from.toUpperCase();
					outcome = outcome.toUpperCase();
				}
				
				if (from.equals(outcome)) {
					controller = k + 1;
					action = i + 1;
					break;
				}
			}
			
		}

		if (controller == 0) {
			return defaultRoute;
		}

		if(action < paths.length) {
			context.setAction(paths[action]);
		} else {
			context.setAction(null);
		}
		
		Map<String, String> parameters = new HashMap<>();
		for(int i = action + 1, j = paths.length; i < j; i+=2) {
			String value = null;
			if(i + 1 < j) {
				value = paths[i+1];
			}
			parameters.put(paths[i], value);
		}
		context.setParameters(parameters);
		
		return routes.get(controller - 1).getTo();
	}

	private String getOutcomeString(int index, String[] paths) {
		if(index >= paths.length) {
			return "";
		}
		
		StringBuilder outcomeString = new StringBuilder();
		String p = "";
		for (int i = 1, j = index + 1; i < j; i++) {
			outcomeString.append(p).append(paths[i]);
			p = "/";
		}

		return outcomeString.toString();
	}

	public String getDefaultRoute() {
		return defaultRoute;
	}

}