package pf.application.entity.enums;

/**
 *
 * @author kurt
 */
public enum FormatoTela {

	RESOLUCAO_4_3("Resolução 4:3"),
	WIDESCREEN_4_3("Widescreen 4:3"),
	RESOLUCAO_16_9("Resolução 16:9"),
	WIDESCREEN_16_6("Widescreen 16:9"),
	FULLSCREEN("Fullscreen");
	
	private final String descricao;

	private FormatoTela(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
