package pf.application.entity;

import java.util.Objects;
import pf.framework.model.Entity;

/**
 *
 * @author kurt
 */
public class DesenhoEpisodio implements Entity {
	
	private Integer id;
	private String nome;
	private Integer desenho;
	
	//Framework DAO
	private Integer desenho_id;

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

	public Integer getDesenho() {
		if(desenho == null) {
			desenho = desenho_id;
		}
		return desenho;
	}

	public void setDesenho(Integer desenho) {
		this.desenho = desenho;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 67 * hash + Objects.hashCode(this.id);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DesenhoEpisodio other = (DesenhoEpisodio) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}
	
}