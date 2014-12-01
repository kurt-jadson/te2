package pf.framework.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class ORMManager {

	private static final Logger logger = Logger.getLogger(ORMManager.class.getName());
	private Document document;
	private final Set<String> enumTypes;
	private final List<EntityConfig> configurations;
	private DBConnection connection;

	public ORMManager(ServletContext servletContext) throws Exception {
		configurations = new ArrayList<>();
		enumTypes = new HashSet<>();
		
		for(FieldType f : FieldType.values()) {
			enumTypes.add(f.name());
		}
		
		try {
			document = readXml(servletContext);
			configureConnection();
			configureEntities();
		} catch (ParserConfigurationException | SAXException | IOException ex) {
			String mensagem = "Configuração da unidade de persitência não pode ser lida.";
			logger.log(Level.SEVERE, mensagem, ex);
			throw new Exception(mensagem);
		}
		
		ORM.getInstance().setConnection(connection);
		ORM.getInstance().setConfigs(configurations);
	}
	
	private Document readXml(ServletContext servletContext)
			throws ParserConfigurationException, SAXException, IOException {
		File xmlRoutes = new File(servletContext.getRealPath("/WEB-INF/persistence.xml"));
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlRoutes);
		doc.getDocumentElement().normalize();
		return doc;
	}
	
	private void configureConnection() {
		Element unit = (Element) document.getElementsByTagName("unit").item(0);
		Element drivername = (Element) unit.getElementsByTagName("drivername").item(0);
		Element connectionUrl = (Element) unit.getElementsByTagName("connection-url").item(0);
		Element username = (Element) unit.getElementsByTagName("username").item(0);
		Element password = (Element) unit.getElementsByTagName("password").item(0);
		connection = new DBConnection(
				drivername.getAttribute("value"),
				connectionUrl.getAttribute("value"), 
				username.getAttribute("value"),
				password.getAttribute("value"));
	}
	
	private void configureEntities() throws Exception {
		NodeList entityList = document.getElementsByTagName("entity");
		for(int i = 0, j = entityList.getLength(); i < j; i++) {
			Element entityElement = (Element) entityList.item(i);
			String table = getTable(entityElement);
			Class<Entity> clazz = getEntityClass(entityElement);
			
			EntityConfig entityConfig = new EntityConfig(table, clazz);
			NodeList fields = entityElement.getElementsByTagName("field");
			for(int k = 0, l = fields.getLength(); k < l; k++) {
				Element fieldElement = (Element) fields.item(k);
				entityConfig.addField(buildField(fieldElement));
			}
			
			configurations.add(entityConfig);
		}
	}
	
	private String getTable(Element el) {
		return el.getAttribute("table");
	}
	
	private Class<Entity> getEntityClass(Element el) throws Exception {
		Class<Entity> cz = (Class<Entity>) Class.forName(el.getAttribute("class"));
		return cz;
	}
	
	private Field buildField(Element el) throws Exception {
		String name = el.getAttribute("name");
		String column = el.getAttribute("column");
		String type = el.getAttribute("type");
		Field field;
		
		if(enumTypes.contains(type)) {
			FieldType fieldType = FieldType.valueOf(type);
			field = new Field(name, column, fieldType);
		} else {
			Class clazz = Class.forName(type);
			field = new EnumField(name, column, clazz);
		}
		
		return field;
	}

	public List<EntityConfig> getConfigurations() {
		return Collections.unmodifiableList(configurations);
	}
	
}