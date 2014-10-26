package pf.application.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author kurt
 */
public class Atividade {

	private Integer codigo;
	private String descricao;
	private Date dataCadastro;
	private Date dataConclusao;
	private Integer estagio;

	public Atividade() {
		dataCadastro = Calendar.getInstance().getTime();
	}

	public Atividade(Integer codigo) {
		this();
		this.codigo = codigo;
	}

	public Atividade(Integer codigo, String descricao, Integer estagio) {
		this();
		this.codigo = codigo;
		this.descricao = descricao;
		this.estagio = estagio;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataConclusao() {
		return dataConclusao;
	}

	public void setDataConclusao(Date dataConclusao) {
		this.dataConclusao = dataConclusao;
	}

	public Integer getEstagio() {
		return estagio;
	}

	public void setEstagio(Integer estagio) {
		this.estagio = estagio;
	}

	public boolean isConcluida() {
		return this.estagio == 100;
	}

	public void concluir() {
		this.estagio = 100;
		dataConclusao = Calendar.getInstance().getTime();
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 97 * hash + Objects.hashCode(this.codigo);
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
		final Atividade other = (Atividade) obj;
		if (!Objects.equals(this.codigo, other.codigo)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Atividade{" + "codigo=" + codigo + ", descricao=" + descricao + '}';
	}

}
