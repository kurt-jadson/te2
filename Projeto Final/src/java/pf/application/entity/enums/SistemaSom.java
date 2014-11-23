package pf.application.entity.enums;

/**
 *
 * @author kurt
 */
public enum SistemaSom {
	
	DOLBY_DIGITAL_2("Dolby Digital 2.0"),
	TRUE_HD("TrueHD"),
	DTS("DTS");
	
	private final String descricao;

	private SistemaSom(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
