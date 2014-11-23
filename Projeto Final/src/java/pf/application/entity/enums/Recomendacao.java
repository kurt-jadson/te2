package pf.application.entity.enums;

/**
 *
 * @author kurt
 */
public enum Recomendacao {

	ANOS_12("12 anos"),
	ANOS_18("18 anos"),
	ANOS_21("21 anos");
	
	private final String descricao;

	private Recomendacao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
