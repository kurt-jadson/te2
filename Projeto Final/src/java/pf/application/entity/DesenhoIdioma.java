package pf.application.entity;

import java.util.Objects;
import pf.framework.model.Entity;

/**
 *
 * @author kurt
 */
public class DesenhoIdioma implements Entity {
	
	private Integer desenho;
	private Integer idioma;

	public DesenhoIdioma() {
	}

	public DesenhoIdioma(Integer desenho, Integer idioma) {
		this.desenho = desenho;
		this.idioma = idioma;
	}

	@Override
	public boolean isNew() {
		return desenho == null && idioma == null;
	}

	public Integer getDesenho() {
		return desenho;
	}

	public void setDesenho(Integer desenho) {
		this.desenho = desenho;
	}

	public Integer getIdioma() {
		return idioma;
	}

	public void setIdioma(Integer idioma) {
		this.idioma = idioma;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 29 * hash + Objects.hashCode(this.desenho);
		hash = 29 * hash + Objects.hashCode(this.idioma);
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
		final DesenhoIdioma other = (DesenhoIdioma) obj;
		if (!Objects.equals(this.desenho, other.desenho)) {
			return false;
		}
		if (!Objects.equals(this.idioma, other.idioma)) {
			return false;
		}
		return true;
	}
	
}