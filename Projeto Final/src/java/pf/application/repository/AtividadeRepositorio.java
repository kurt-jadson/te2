package pf.application.repository;

import java.sql.Connection;
import java.util.List;
import pf.application.entity.Atividade;
import pf.framework.model.DAO;
import pf.framework.model.Field;
import pf.framework.model.FieldType;

/**
 *
 * @author kurt
 */
public class AtividadeRepositorio {

	private final Connection connection;
	private static final String ATIVIDADE = "Atividade";
	private static final Field CODIGO = new Field("CODIGO", FieldType.INTEGER);
	private static final Field DESCRICAO = new Field("DESCRICAO", FieldType.STRING);
	private static final Field DATACADASTRO = new Field("DATACADASTRO", FieldType.DATE);
	private static final Field DATACONCLUSAO = new Field("DATACONCLUSAO", FieldType.DATE);
	private static final Field ESTAGIO = new Field("ESTAGIO", FieldType.INTEGER);

	public AtividadeRepositorio(Connection connection) {
		this.connection = connection;
	}

	public void adicionarAtividade(Atividade atividade) throws Exception {
		Atividade emBanco = getAtividade(atividade.getCodigo());
		if (emBanco == null) {
			insertAtividade(atividade);
		} else {
			updateAtividade(atividade);
		}
	}

	private void insertAtividade(Atividade atividade) throws Exception {
		DAO.insert(ATIVIDADE)
				.fields(CODIGO, DESCRICAO, DATACADASTRO, ESTAGIO)
				.values(atividade.getCodigo(),
						atividade.getDescricao(),
						atividade.getDataCadastro(),
						atividade.getEstagio())
				.execute(connection);
	}

	private void updateAtividade(Atividade atividade) throws Exception {
		DAO.update(ATIVIDADE)
				.fields(CODIGO, DESCRICAO, DATACADASTRO, ESTAGIO)
				.values(atividade.getCodigo(),
						atividade.getDescricao(),
						atividade.getDataCadastro(),
						atividade.getEstagio())
				.whereEquals(CODIGO, atividade.getCodigo())
				.execute(connection);
	}

	public void removerAtividade(Atividade atividade) throws Exception {
		DAO.delete(ATIVIDADE)
				.whereEquals(CODIGO, atividade.getCodigo())
				.execute(connection);
	}

	public Atividade getAtividade(Integer codigo) throws Exception {
		return DAO.select(ATIVIDADE)
				.fields(CODIGO, DESCRICAO, DATACADASTRO, DATACONCLUSAO, ESTAGIO)
				.whereEquals(CODIGO, codigo)
				.getSingleResult(connection, Atividade.class);
	}

	public List<Atividade> getAtividades() throws Exception {
		return DAO.select(ATIVIDADE)
				.fields(CODIGO, DESCRICAO, DATACADASTRO, DATACONCLUSAO, ESTAGIO)
				.getResult(connection, Atividade.class);
	}

}
