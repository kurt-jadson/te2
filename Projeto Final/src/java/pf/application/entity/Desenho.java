package pf.application.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import pf.application.entity.enums.Cor;
import pf.application.entity.enums.FormatoTela;
import pf.application.entity.enums.Legenda;
import pf.application.entity.enums.Pais;
import pf.application.entity.enums.Recomendacao;
import pf.framework.model.Entity;

/**
 *
 * @author kurt
 */
public class Desenho implements Entity {

	public static final String[] TODOS = new String[]{
		"id", "titulo", "volume", "tempo", "cor", "anoLancamento", "recomendacao",
		"regiaoDvd", "legenda", "formatoTela", "paisOrigem", "descricao", "preco"
	};

	public static final String[] TODOS_SEM_ID = new String[]{
		"titulo", "volume", "tempo", "cor", "anoLancamento", "recomendacao",
		"regiaoDvd", "legenda", "formatoTela", "paisOrigem", "descricao", "preco"
	};

	private Integer id;
	private String titulo;
	private Integer volume;
	private Integer tempo;
	private Cor cor;
	private Integer anoLancamento;
	private Recomendacao recomendacao;
	private Integer regiaoDvd;
	private Legenda legenda;
	private FormatoTela formatoTela;
	private Pais paisOrigem;
	private String descricao;
	private BigDecimal preco;
	private final List<Idioma> idiomas;
	private final List<Episodio> episodios;

	public Desenho() {
		idiomas = new ArrayList<>();
		episodios = new ArrayList<>();
	}

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

	public Recomendacao getRecomendacao() {
		return recomendacao;
	}

	public void setRecomendacao(Recomendacao recomendacao) {
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

	public void addAllIdiomas(List<Idioma> idiomas) {
		this.idiomas.addAll(idiomas);
	}

	public void addIdioma(Idioma idioma) {
		idiomas.add(idioma);
	}

	public void addAllEpisodios(List<Episodio> episodios) {
		this.episodios.addAll(episodios);
	}

	public void addEpisodio(Episodio episodio) {
		episodios.add(episodio);
	}

	public List<Idioma> getIdiomas() {
		return Collections.unmodifiableList(idiomas);
	}

	public List<Episodio> getEpisodios() {
		return Collections.unmodifiableList(episodios);
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
