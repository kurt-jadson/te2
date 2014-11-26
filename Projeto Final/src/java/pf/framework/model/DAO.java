package pf.framework.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kurt
 */
public class DAO {

	private final StringBuilder sql;
	private boolean insert;
	private Field[] fields;
	private Object[] values;
	private final List<Field> fieldConditions;
	private final List valueConditions;

	private DAO() {
		sql = new StringBuilder();
		fields = new Field[0];
		values = new Object[0];
		fieldConditions = new ArrayList<>();
		valueConditions = new ArrayList();
		insert = false;
	}
	
	public static DAO createInsertOrUpdate(Entity entity, String table) {
		if(entity.isNew()) {
			return insert(table);
		}
		return update(table);
	}

	public StringBuilder getSql() {
		return sql;
	}

	public void inserting() {
		insert = true;
	}

	public static DAO insert(String table) {
		DAO dao = new DAO();
		dao.getSql().append("INSERT INTO ").append(table);
		dao.inserting();
		return dao;
	}

	public static DAO update(String table) {
		DAO dao = new DAO();
		dao.getSql().append("UPDATE ").append(table);
		return dao;
	}

	public static DAO delete(String table) {
		DAO dao = new DAO();
		dao.getSql().append("DELETE FROM ").append(table);
		return dao;
	}

	public static DAO select(String... tables) {
		DAO dao = new DAO();
		dao.getSql().append("SELECT %s FROM ");

		String comma = "";
		for (String s : tables) {
			dao.getSql().append(comma).append(s);
			comma = ", ";
		}

		return dao;
	}
	
	public static DAO selectMax(String table, Field field) {
		DAO dao = new DAO();
		dao.getSql().append("SELECT MAX(").append(field.name).append(") FROM ").append(table);
		return dao;
	}

	public DAO fields(Field... fields) {
		this.fields = fields;
		return this;
	}

	public DAO values(Object... values) {
		this.values = values;

		if (!insert) {
			String prefix = " SET ";
			for (int i = 0, j = fields.length; i < j; i++) {
				Field field = fields[i];
				
				if(values[i] != null) {
					sql.append(prefix).append(field.name).append(" = ?");
					prefix = ", ";
				}
			}
		}

		return this;
	}

	public DAO whereEquals(Field field, Object value) {
		sql.append(" WHERE ").append(field.name).append(" = ?");
		fieldConditions.add(field);
		valueConditions.add(value);
		return this;
	}
	
	public DAO whereIn(Field field, Object... values) {
		sql.append(" WHERE ").append(field.name).append("IN (");
		fieldConditions.add(field);
		
		String prefix = "";
		for(Object value : values) {
			sql.append(prefix).append("?");
			prefix = ",";
			valueConditions.add(value);
		}
		sql.append(")");
		
		return this;
	}
	
	public DAO whereLike(Field field, Object value) {
		sql.append(" WHERE ").append(field.name).append(" LIKE ?");
		fieldConditions.add(field);
		valueConditions.add(value);
		return this;
	}
	
	public DAO andEquals(Field field, Object value) {
		sql.append(" AND ").append(field.name).append(" = ?");
		fieldConditions.add(field);
		valueConditions.add(value);
		return this;
	}

	public boolean execute(Connection connection) throws Exception {

		if (insert) {
			String prefix = " (";
			for (int i = 0, j = fields.length; i < j; i++) {
				Field field = fields[i];
				
				if(values[i] != null) {
					sql.append(prefix).append(field.name);
					prefix = ", ";
				}
				
				if(i == j - 1) {
					sql.append(")");
				}
				
			}

			prefix = " VALUES (";
			for (int i = 0, j = fields.length; i < j; i++) {
				
				if(values[i] != null) {
					sql.append(prefix).append("?");
					prefix = ", ";
				}
				
				if (i == j - 1) {
					sql.append(")");
				}
			}
		}
		
		try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
			int k = 0;
			for (int i = 0, j = fields.length; i < j; i++) {
				if(values[i] != null) {
					k++;
					FieldType.set(fields[i].type, values[i], k, ps);
				}
			}

			for (int i = 0, j = fieldConditions.size(); i < j; i++) {
				FieldType.set(fieldConditions.get(i).type, valueConditions.get(i), i + k + 1, ps);
			}

			return ps.execute();
		}
	}
	
	public <T> T getSingleResult(Connection connection, Class<T> type) throws Exception {
		List<T> entities = getResult(connection, type);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public <T> List<T> getResult(Connection connection, Class<T> type) throws Exception {
		String sqlStr = sql.toString();
		StringBuilder projection = new StringBuilder();

		String comma = "";
		for (Field field : fields) {
			projection.append(comma).append(field.name);
			comma = ", ";
		}

		sqlStr = String.format(sqlStr, projection);
		try (PreparedStatement ps = connection.prepareStatement(sqlStr)) {
			for (int i = 0, j = fieldConditions.size(); i < j; i++) {
				FieldType.set(fieldConditions.get(i).type, valueConditions.get(i), i + 1, ps);
			}

			ResultSet rs = ps.executeQuery();
			return organize(type, rs);
		}
	}

	private <T> List<T> organize(Class<T> type, ResultSet rs) throws Exception {
		List<T> list = new ArrayList<>();
		java.lang.reflect.Field[] dfields = type.getDeclaredFields();
		
		while (rs.next()) {
			T entity = type.newInstance();
			for(int i = 0, j = fields.length; i < j; i++) {
				Field field = fields[i];
				Object obj = rs.getObject(i + 1);
				
				if(FieldType.ENUM.equals(field.type) && obj != null) {
					EnumField ef = (EnumField) field;
					obj = Enum.valueOf(ef.getEnumType(), (String) obj);
				}
				
				java.lang.reflect.Field rf = getFieldIgnoreCase(field, dfields);
				rf.setAccessible(true);
				rf.set(entity, obj);
				rf.setAccessible(false);
			}
			
			list.add(entity);
		}
		
		return list;
	}
	
	private java.lang.reflect.Field getFieldIgnoreCase(Field field, java.lang.reflect.Field[] fields) {
		for(java.lang.reflect.Field f : fields) {
			if(field.name.equalsIgnoreCase(f.getName())) {
				return f;
			}
		}
		
		return null;
	}

}
