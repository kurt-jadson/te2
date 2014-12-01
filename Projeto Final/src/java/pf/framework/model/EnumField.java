package pf.framework.model;

/**
 *
 * @author kurt
 */
public class EnumField<T extends Enum> extends Field {

	private final Class<T> enumType;

	public EnumField(String name, Class<T> enumType) {
		super(name, FieldType.ENUM);
		this.enumType = enumType;
	}

	public EnumField(String name, String column, Class<T> enumType) {
		super(name, column, FieldType.ENUM);
		this.enumType = enumType;
	}

	public Class<T> getEnumType() {
		return enumType;
	}

}
