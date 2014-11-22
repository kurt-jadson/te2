package pf.framework.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

/**
 *
 * @author kurt
 */
public enum FieldType {
	
	BIG_DECIMAL,
	DATE,
	INTEGER,
	LONG,
	STRING;
	
	public static PreparedStatement set(FieldType type, Object value, int pos, PreparedStatement ps) 
			throws SQLException {
		switch(type) {
			case BIG_DECIMAL:
				ps.setBigDecimal(pos, (BigDecimal) value);
				break;
			case DATE:
				Date date = new Date(((java.util.Date) value).getTime());
				ps.setDate(pos, date);
				break;
			case INTEGER:
				ps.setInt(pos, (Integer) value);
				break;
			case LONG:
				ps.setLong(pos, (Long) value);
				break;
			case STRING:
				ps.setString(pos, (String) value);
				break;
		}

		return ps;
	}
	
}
