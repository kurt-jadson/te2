package pf.application.entity.enums;

/**
 *
 * @author kurt
 */
public enum Pais {
	
	BRASIL("Brasil"),
	ESTADOS_UNIDOS("Estados Unidos"),
	JAPAO("Japão");
	
	private final String descricao;

	private Pais(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
