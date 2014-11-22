package pf.application.entity;

import java.math.BigDecimal;
import java.util.Objects;
import pf.application.entity.enums.Cor;
import pf.application.entity.enums.FormatoTela;
import pf.application.entity.enums.Legenda;
import pf.application.entity.enums.Pais;

/**
 *
 * @author kurt
 */
public class Desenho {

	private Integer id;
	private String titulo;
	private Integer volume;
	private Integer tempo;
	private Cor cor;
	private Integer anoLancamento;
	private String recomendacao;
	private Integer regiaoDvd;
	private Legenda legenda;
	private FormatoTela formatoTela;
	private Pais paisOrigem;
	private String descricao;
	private BigDecimal preco;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public Integer getTempo() {
		return tempo;
	}

	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

	public Cor getCor() {
		return cor;
	}

	public void setCor(Cor cor) {
		this.cor = cor;
	}

	public Integer getAnoLancamento() {
		return anoLancamento;
	}

	public void setAnoLancamento(Integer anoLancamento) {
		this.anoLancamento = anoLancamento;
	}

	public String getRecomendacao() {
		return recomendacao;
	}

	public void setRecomendacao(String recomendacao) {
		this.recomendacao = recomendacao;
	}

	public Integer getRegiaoDvd() {
		return regiaoDvd;
	}

	public void setRegiaoDvd(Integer regiaoDvd) {
		this.regiaoDvd = regiaoDvd;
	}

	public Legenda getLegenda() {
		return legenda;
	}

	public void setLegenda(Legenda legenda) {
		this.legenda = legenda;
	}

	public FormatoTela getFormatoTela() {
		return formatoTela;
	}

	public void setFormatoTela(FormatoTela formatoTela) {
		this.formatoTela = formatoTela;
	}

	public Pais getPaisOrigem() {
		return paisOrigem;
	}

	public void setPaisOrigem(Pais paisOrigem) {
		this.paisOrigem = paisOrigem;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + Objects.hashCode(this.id);
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
		final Desenho other = (Desenho) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}
	
}