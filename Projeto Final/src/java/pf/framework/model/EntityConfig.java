package pf.framework.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kurt
 */
public class EntityConfig {

	private final String table;
	private final Class<Entity> clazz;
	private final List<Field> fields;

	public EntityConfig(String table, Class<Entity> clazz) {
		this.table = table;
		this.clazz = clazz;
		fields = new ArrayList<>();
	}

	public String getTable() {
		return table;
	}
	
	public Field getFieldByName(String name) {
		for(Field field : fields) {
			if(name.equals(field.name)) {
				return field;
			}
		}
		return null;
	}

	public Class<Entity> getEntityClass() {
		return clazz;
	}
	
	public void addField(Field field) {
		fields.add(field);
	}
	
}