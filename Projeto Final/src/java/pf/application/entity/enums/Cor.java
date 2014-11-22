package pf.application.entity.enums;

/**
 *
 * @author kurt
 */
public enum Cor {
	
	COLORIDO("Colorido"),
	PRETO_E_BRANCO("Preto e Branco");
	
	private final String descricao;
	
	private Cor(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
