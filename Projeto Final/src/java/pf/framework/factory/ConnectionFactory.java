package pf.framework.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {

	private static final Logger logger = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String URL = "jdbc:hsqldb:hsql://localhost/data";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    
    public static Connection getConnection() {
        Connection c = null;
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch(Exception ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
        return c;
    }
    
    public static void main(String[] args) {
        System.out.println(ConnectionFactory.getConnection());
    }
    
}
