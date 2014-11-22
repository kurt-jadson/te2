package pf.application.entity.enums;

/**
 *
 * @author kurt
 */
public enum Legenda {
	
	PORTUGUES("Português"),
	INGLES("Inglês"),
	ESPANHOL("Espanhol");
	
	private final String descricao;

	private Legenda(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
