package pf.application.entity;

import pf.application.entity.enums.SistemaSom;
import pf.framework.model.Entity;

/**
 *
 * @author kurt
 */
public class Idioma implements Entity {

	public static final String[] TODOS = new String[] {
		"id", "nome", "sistemaSom"
	};
	
	public static final String[] TODOS_SEM_ID = new String[] {
		"nome", "sistemaSom"
	};
	
	private Integer id;
	private String nome;
	private SistemaSom sistemaSom;

	@Override
	public boolean isNew() {
		return id == null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public SistemaSom getSistemaSom() {
		return sistemaSom;
	}

	public void setSistemaSom(SistemaSom sistemaSom) {
		this.sistemaSom = sistemaSom;
	}

}
