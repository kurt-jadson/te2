package pf.application.repository;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;
import pf.application.entity.Idioma;
import pf.application.entity.enums.SistemaSom;
import pf.framework.model.DAO;
import pf.framework.model.EnumField;
import pf.framework.model.Field;
import pf.framework.model.FieldType;

/**
 *
 * @author kurt
 */
public class IdiomaRepositorio {

	private static final Logger logger = Logger.getLogger(IdiomaRepositorio.class.getName());

	private final Connection connection;
	private static final String IDIOMA = "IDIOMA";
	private static final Field ID = new Field("ID", FieldType.INTEGER);
	private static final Field NOME = new Field("NOME", FieldType.STRING);
	private static final Field SISTEMA_SOM = new EnumField("SISTEMASOM", SistemaSom.class);

	public IdiomaRepositorio(Connection connection) {
		this.connection = connection;
	}

	public void salvar(Idioma idioma) throws Exception {
		DAO dao;
		if (idioma.isNew()) {
			dao = DAO.insert(IDIOMA);
		} else {
			dao = DAO.update(IDIOMA);
		}

		dao.fields(todosSemId());
		dao.values(idioma.getNome(), idioma.getSistemaSom());

		if (!idioma.isNew()) {
			dao.whereEquals(ID, idioma.getId());
		}

		dao.execute(connection);
	}
	
	public List<Idioma> buscarTodos() throws Exception {
		return DAO.select(IDIOMA)
				.fields(todos())
				.getResult(connection, Idioma.class);
	}
	
	public Idioma buscarPorId(Integer id) throws Exception {
		return DAO.select(IDIOMA)
				.fields(todos())
				.whereEquals(ID, id)
				.getSingleResult(connection, Idioma.class);
	}
	
	public void remover(Idioma idioma) throws Exception {
		DAO.delete(IDIOMA)
				.whereEquals(ID, idioma.getId())
				.execute(connection);
	}
	
	public Field[] todos() {
		return new Field[]{ID, NOME, SISTEMA_SOM};
	}

	public Field[] todosSemId() {
		return new Field[]{NOME, SISTEMA_SOM};
	}
}
