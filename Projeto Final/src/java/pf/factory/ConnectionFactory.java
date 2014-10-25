package pf.factory;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    private static final String URL = "jdbc:hsqldb:hsql://localhost/data";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    
    public static Connection getConnection() {
        Connection c = null;
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return c;
    }
    
    public static void main(String[] args) {
        System.out.println(ConnectionFactory.getConnection());
    }
    
}
