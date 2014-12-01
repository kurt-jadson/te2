package pf.framework.model;

import java.util.List;

/**
 *
 * @author kurt
 */
public class ORM {

	private static ORM instance;
	private DBConnection connection;
	private List<EntityConfig> configs;
	
	private ORM() {
		
	}
	
	public static ORM getInstance() {
		if(instance == null) {
			instance = new ORM();
		}
		return instance;
	}

	public void setConfigs(List<EntityConfig> configs) {
		this.configs = configs;
	}
	
	public EntityConfig getConfig(Class<? extends Entity> entity) {
		for(EntityConfig ec : configs) {
			if(ec.getEntityClass().equals(entity)) {
				return ec;
			}
		}
		return null;
	}

	public DBConnection getConnection() {
		return connection;
	}

	public void setConnection(DBConnection connection) {
		this.connection = connection;
	}
	
}
