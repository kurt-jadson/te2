package pf.framework.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
	
	private StringBuilder sql;
	private boolean insert;
	private boolean update;
	private Field[] fields;
	
	private final EntityConfig config;
	private Entity entity;
	
	private final List<Field> fieldConditions;
	private final List valueConditions;
	
	private DAO(Entity entity) {
		this(entity.getClass());
		this.entity = entity;
		this.fields = new Field[0];
	}
	
	private DAO(Class<? extends Entity> entityClass) {
		this.fields = new Field[0];
		config = ORM.getInstance().getConfig(entityClass);
		fieldConditions = new ArrayList<>();
		valueConditions = new ArrayList<>();
		insert = false;
		update = false;
	}
	
	public StringBuilder getSql() {
		return sql;
	}
	
	public void setSql(StringBuilder sql) {
		this.sql = sql;
	}
	
	public String getTable() {
		return config.getTable();
	}
	
	public void inserting() {
		insert = true;
	}
	
	public void updating() {
		update = true;
	}
	
	public static DAO createInsertOrUpdate(Entity entity) {
		if (entity.isNew()) {
			return insert(entity);
		}
		return update(entity);
	}
	
	public static DAO insert(Entity entity) {
		DAO dao = new DAO(entity);
		dao.setSql(new StringBuilder("INSERT INTO ").append(dao.getTable()));
		dao.inserting();
		return dao;
	}
	
	public static DAO update(Entity entity) {
		DAO dao = new DAO(entity);
		dao.setSql(new StringBuilder("UPDATE ")
				.append(dao.getTable())
				.append("%s"));
		dao.updating();
		return dao;
	}
	
	public static DAO delete(Class<? extends Entity> entityClass) {
		DAO dao = new DAO(entityClass);
		dao.setSql(new StringBuilder("DELETE FROM ").append(dao.getTable()));
		return dao;
	}	
	
	public static DAO select(Class<? extends Entity> entityClass) {
		DAO dao = new DAO(entityClass);
		dao.setSql(new StringBuilder("SELECT %s FROM ").append(dao.getTable()));
		return dao;
	}
	
	public static DAO selectMax(Class<? extends Entity> entityClass, String fieldName) {
		Field field = ORM.getInstance().getConfig(entityClass).getFieldByName(fieldName);
		DAO dao = new DAO(entityClass);
		StringBuilder sb = new StringBuilder("SELECT MAX(")
				.append(field.name).append(") FROM ")
				.append(dao.getTable());
		dao.setSql(sb);
		return dao;
	}	
	
	public DAO fields(String... fields) {
		this.fields = new Field[fields.length];
		for (int i = 0, j = fields.length; i < j; i++) {
			this.fields[i] = config.getFieldByName(fields[i]);
		}
		return this;
	}
	
	public DAO whereEquals(String fieldName, Object value) {
		Field field = config.getFieldByName(fieldName);
		sql.append(" WHERE ").append(field.column).append(" = ?");
		fieldConditions.add(field);
		valueConditions.add(value);
		return this;
	}
	
	public DAO whereLike(String fieldName, Object value) {
		Field field = config.getFieldByName(fieldName);
		sql.append(" WHERE ").append(field.name).append(" LIKE ?");
		fieldConditions.add(field);
		valueConditions.add(value);
		return this;
	}
	
	public DAO whereIn(String fieldName, Object[] values) {
		Field field = config.getFieldByName(fieldName);
		sql.append(" WHERE ").append(field.name).append(" IN (");
		
		String prefix = "";
		for (Object value : values) {
			sql.append(prefix).append("?");
			prefix = ",";
			fieldConditions.add(field);
			valueConditions.add(value);
		}
		sql.append(")");
		
		return this;
	}
	
	public DAO andEquals(String fieldName, Object value) {
		Field field = config.getFieldByName(fieldName);
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
				
				if (getValue(field) != null) {
					sql.append(prefix).append(field.column);
					prefix = ", ";
				}
				
				if (i == j - 1) {
					sql.append(")");
				}
				
			}
			
			prefix = " VALUES (";
			for (int i = 0, j = fields.length; i < j; i++) {
				Field field = fields[i];
				
				if (getValue(field) != null) {
					sql.append(prefix).append("?");
					prefix = ", ";
				}
				
				if (i == j - 1) {
					sql.append(")");
				}
			}
		} else if (update) {
			StringBuilder setSql = new StringBuilder();
			String prefix = " SET ";
			for (int i = 0, j = fields.length; i < j; i++) {
				Field field = fields[i];
				if (getValue(field) != null) {
					setSql.append(prefix).append(field.column).append(" = ?");
					prefix = ", ";
				}
			}
			sql = new StringBuilder(String.format(sql.toString(), setSql));
		}
		
		try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
			int k = 0;
			for (int i = 0, j = fields.length; i < j; i++) {
				Object value = getValue(fields[i]);
				if (value != null) {
					k++;
					FieldType.set(fields[i].type, value, k, ps);
				}
			}
			
			for (int i = 0, j = fieldConditions.size(); i < j; i++) {
				FieldType.set(fieldConditions.get(i).type, valueConditions.get(i), i + k + 1, ps);
			}
			
			return ps.execute();
		}
	}
	
	private Object getValue(Field field) {
		try {
			Method m = config.getEntityClass().getDeclaredMethod(field.getGetterMethodName(), new Class[]{});
			return m.invoke(entity, new Object[]{});
		} catch (NoSuchMethodException ex) {
		} catch (SecurityException ex) {
		} catch (IllegalAccessException ex) {
		} catch (IllegalArgumentException ex) {
		} catch (InvocationTargetException ex) {
		}
		return null;
	}
	
	public <T> T getSingleResult(Connection connection) throws Exception {
		List<T> entities = getResult(connection);
		return entities.isEmpty() ? null : entities.get(0);
	}
	
	public <T> List<T> getResult(Connection connection) throws Exception {
		Class<T> type = (Class<T>) config.getEntityClass();
		String sqlStr = sql.toString();
		StringBuilder projection = new StringBuilder();
		
		String comma = "";
		for (Field field : fields) {
			projection.append(comma).append(field.column);
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
			for (int i = 0, j = fields.length; i < j; i++) {
				Field field = fields[i];
				Object obj = rs.getObject(i + 1);
				
				if (FieldType.ENUM.equals(field.type) && obj != null) {
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
		for (java.lang.reflect.Field f : fields) {
			if (field.name.equalsIgnoreCase(f.getName())) {
				return f;
			}
		}
		
		return null;
	}
	
}
