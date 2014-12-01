package pf.framework.model;

/**
 *
 * @author kurt
 */
public class DBConnection {
	
	private final String driverName;
	private final String url;
	private final String user;
	private final String pass;

	public DBConnection(String driverName, String url, String user, String pass) {
		this.driverName = driverName;
		this.url = url;
		this.user = user;
		this.pass = pass;
	}

	public String getDriverName() {
		return driverName;
	}
	
	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getPass() {
		return pass;
	}
	
}