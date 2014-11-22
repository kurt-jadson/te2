package pf.application.repository;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;
import pf.application.entity.Desenho;
import pf.application.entity.enums.Cor;
import pf.application.entity.enums.FormatoTela;
import pf.application.entity.enums.Legenda;
import pf.application.entity.enums.Pais;
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
	private static final Field PRECO = new Field("PRECO", FieldType.BIG_DECIMAL);
	private static final Field COR = new EnumField("COR", Cor.class);
	private static final Field FORMATO_TELA = new EnumField("FORMATOTELA", FormatoTela.class);
	private static final Field PAIS_ORIGEM = new EnumField("PAISORIGEM", Pais.class);
	private static final Field LEGENDA = new EnumField("LEGENDA", Legenda.class);

	public DesenhoRepositorio(Connection connection) {
		this.connection = connection;
	}
	
	public void salvar(Desenho desenho) throws Exception {
		DAO.insert(DESENHO)
				.fields(TITULO,
						VOLUME,
						COR,
						LEGENDA,
						FORMATO_TELA,
						PAIS_ORIGEM,
						PRECO)
				.values(desenho.getTitulo(),
						desenho.getVolume(),
						desenho.getCor(), 
						desenho.getLegenda(),
						desenho.getFormatoTela(),
						desenho.getPaisOrigem(),
						desenho.getPreco())
				.execute(connection);
	}
	
	public List<Desenho> buscarAcervo() throws Exception {
		return DAO.select(DESENHO)
				.fields(ID, TITULO, VOLUME, COR, LEGENDA, FORMATO_TELA, PAIS_ORIGEM, PRECO)
				.getResult(connection, Desenho.class);
	}
	
}