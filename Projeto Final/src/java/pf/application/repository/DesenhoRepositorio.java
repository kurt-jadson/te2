package pf.application.repository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import pf.application.entity.Desenho;
import pf.application.entity.DesenhoIdioma;
import pf.application.entity.Idioma;
import pf.application.entity.enums.Cor;
import pf.application.entity.enums.FormatoTela;
import pf.application.entity.enums.Legenda;
import pf.application.entity.enums.Pais;
import pf.application.entity.enums.Recomendacao;
import pf.framework.model.DAO;
import pf.framework.model.EnumField;
import pf.framework.model.Field;
import pf.framework.model.FieldType;

/**
 *
 * @author kurt
 */
public class DesenhoRepositorio {

	private static final Logger logger = Logger.getLogger(DesenhoRepositorio.class.getName());

	private final Connection connection;
	private static final String DESENHO = "DESENHO";
	private static final Field ID = new Field("ID", FieldType.INTEGER);
	private static final Field TITULO = new Field("TITULO", FieldType.STRING);
	private static final Field VOLUME = new Field("VOLUME", FieldType.INTEGER);
	private static final Field TEMPO = new Field("TEMPO", FieldType.INTEGER);
	private static final Field COR = new EnumField("COR", Cor.class);
	private static final Field ANO_LANCAMENTO = new Field("ANOLANCAMENTO", FieldType.INTEGER);
	private static final Field RECOMENDACAO = new EnumField("RECOMENDACAO", Recomendacao.class);
	private static final Field REGIAO_DVD = new Field("REGIAODVD", FieldType.INTEGER);
	private static final Field LEGENDA = new EnumField("LEGENDA", Legenda.class);
	private static final Field FORMATO_TELA = new EnumField("FORMATOTELA", FormatoTela.class);
	private static final Field PAIS_ORIGEM = new EnumField("PAISORIGEM", Pais.class);
	private static final Field DESCRICAO = new Field("DESCRICAO", FieldType.STRING);
	private static final Field PRECO = new Field("PRECO", FieldType.BIG_DECIMAL);

	private static final String DESENHO_IDIOMA = "DESENHO_IDIOMA";
	private static final Field DESENHO_ID = new Field("DESENHO_ID", FieldType.INTEGER);
	private static final Field IDIOMA_ID = new Field("IDIOMA_ID", FieldType.INTEGER);

	public DesenhoRepositorio(Connection connection) {
		this.connection = connection;
	}

	public void salvar(Desenho desenho) throws Exception {
		connection.setAutoCommit(false);
		desenho = salvarDesenho(desenho);
		salvarIdiomas(desenho);
		connection.commit();
	}

	private Desenho salvarDesenho(Desenho desenho) throws Exception {
		DAO dao = DAO.createInsertOrUpdate(desenho, DESENHO);
		dao.fields(todosSemId());
		dao.values(desenho.getTitulo(),
				desenho.getVolume(),
				desenho.getTempo(),
				desenho.getCor(),
				desenho.getAnoLancamento(),
				desenho.getRecomendacao(),
				desenho.getRegiaoDvd(),
				desenho.getLegenda(),
				desenho.getFormatoTela(),
				desenho.getPaisOrigem(),
				desenho.getDescricao(),
				desenho.getPreco());

		if (!desenho.isNew()) {
			dao.whereEquals(ID, desenho.getId());
		}

		dao.execute(connection);

		if (desenho.isNew()) {
			Desenho persistido = DAO.selectMax(DESENHO, ID)
					.fields(ID)
					.getSingleResult(connection, Desenho.class);
			desenho.setId(persistido.getId());
		}

		return desenho;
	}

	private void salvarIdiomas(Desenho desenho) throws Exception {
		DAO.delete(DESENHO_IDIOMA).whereEquals(DESENHO_ID, desenho.getId());
		for (Idioma idioma : desenho.getIdiomas()) {
			DAO.insert(DESENHO_IDIOMA)
					.fields(DESENHO_ID, IDIOMA_ID)
					.values(desenho.getId(), idioma.getId())
					.execute(connection);
		}
	}

	public List<Desenho> buscarAcervo() throws Exception {
		return DAO.select(DESENHO)
				.fields(todos())
				.getResult(connection, Desenho.class);
	}

	public Desenho buscarPorId(Integer id) throws Exception {
		return DAO.select(DESENHO)
				.fields(todos())
				.whereEquals(ID, id)
				.getSingleResult(connection, Desenho.class);
	}
	
	public Desenho buscarPorIdFetchListas(Integer id) throws Exception {
		Desenho desenho = buscarPorId(id);
		List<DesenhoIdioma> idiomas = buscarIdiomas(desenho);
		return desenho;
	}
	
	private List<DesenhoIdioma> buscarIdiomas(Desenho desenho) throws Exception {
		return DAO.select(DESENHO_IDIOMA)
				.fields(DESENHO_ID, IDIOMA_ID)
				.whereEquals(ID, desenho.getId())
				.getResult(connection, DesenhoIdioma.class);
	}

	public List<Desenho> buscarPorTitulo(String titulo) throws Exception {
		return DAO.select(DESENHO)
				.fields(todos())
				.whereLike(TITULO, "%" + titulo + "%")
				.getResult(connection, Desenho.class);
	}

	public void remover(Desenho desenho) throws Exception {
		DAO.delete(DESENHO)
				.whereEquals(ID, desenho.getId())
				.execute(connection);
	}

	public Field[] todosSemId() {
		return new Field[]{TITULO, VOLUME, TEMPO, COR, ANO_LANCAMENTO, RECOMENDACAO,
			REGIAO_DVD, LEGENDA, FORMATO_TELA, PAIS_ORIGEM, DESCRICAO, PRECO};
	}

	public Field[] todos() {
		return new Field[]{ID, TITULO, VOLUME, TEMPO, COR, ANO_LANCAMENTO, RECOMENDACAO,
			REGIAO_DVD, LEGENDA, FORMATO_TELA, PAIS_ORIGEM, DESCRICAO, PRECO};
	}

}
