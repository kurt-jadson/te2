package pf.application.repository;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;
import pf.application.entity.Idioma;
import pf.framework.model.DAO;

/**
 *
 * @author kurt
 */
public class IdiomaRepositorio {

	private static final Logger logger = Logger.getLogger(IdiomaRepositorio.class.getName());

	private final Connection connection;
	
	public IdiomaRepositorio(Connection connection) {
		this.connection = connection;
	}

	public void salvar(Idioma idioma) throws Exception {
		DAO dao = DAO.createInsertOrUpdate(idioma);
		dao.fields(Idioma.TODOS_SEM_ID);

		if (!idioma.isNew()) {
			dao.whereEquals("id", idioma.getId());
		}

		dao.execute(connection);
	}
	
	public List<Idioma> buscarTodos() throws Exception {
		return DAO.select(Idioma.class)
				.fields(Idioma.TODOS)
				.getResult(connection);
	}
	
	public Idioma buscarPorId(Integer id) throws Exception {
		return DAO.select(Idioma.class)
				.fields(Idioma.TODOS)
				.whereEquals("id", id)
				.getSingleResult(connection);
	}
	
	public void remover(Idioma idioma) throws Exception {
		DAO.delete(Idioma.class)
				.whereEquals("id", idioma.getId())
				.execute(connection);
	}
}