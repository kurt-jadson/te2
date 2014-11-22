package pf.application.repository;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;
import pf.application.entity.Desenho;
import pf.framework.model.DAO;
import pf.framework.model.Field;
import pf.framework.model.FieldType;

/**
 *
 * @author kurt
 */
public class DesenhoRepositorio {

	private static final Logger logger = Logger.getLogger(UsuarioRepositorio.class.getName());

	private final Connection connection;
	private static final String DESENHO = "DESENHO";
	private static final Field ID = new Field("ID", FieldType.INTEGER);
	private static final Field TITULO = new Field("TITULO", FieldType.STRING);
	private static final Field PRECO = new Field("PRECO", FieldType.BIG_DECIMAL);

	public DesenhoRepositorio(Connection connection) {
		this.connection = connection;
	}
	
	public void salvar(Desenho desenho) throws Exception {
		DAO.insert(DESENHO)
				.fields(TITULO, PRECO)
				.values(desenho.getTitulo(), desenho.getPreco())
				.execute(connection);
	}
	
	public List<Desenho> buscarAcervo() throws Exception {
		return DAO.select(DESENHO)
				.fields(ID, TITULO, PRECO)
				.getResult(connection, Desenho.class);
	}
	
}