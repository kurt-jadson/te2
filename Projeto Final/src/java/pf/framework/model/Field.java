package pf.framework.model;

/**
 *
 * @author kurt
 */
public class Field {

	public String name;
	public String column;
	public FieldType type;

	public Field(String name, FieldType type) {
		this.name = name;
		this.column = name;
		this.type = type;
	}
	
	public Field(String name, String column, FieldType type) {
		this.name = name;
		this.column = column;
		this.type = type;
		
		if(column == null || column.isEmpty()) {
			this.column = name;
		}
	}	
	
	public String getGetterMethodName() {
		String cname = name.substring(0, 1).toUpperCase() + name.substring(1);
		return "get" + cname;
	}
	
}
