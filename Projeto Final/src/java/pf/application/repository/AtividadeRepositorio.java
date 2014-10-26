package pf.application.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import pf.application.entity.Atividade;
import pf.framework.factory.ConnectionFactory;
import pf.framework.model.DAO;
import pf.framework.model.Field;
import pf.framework.model.FieldType;

/**
 *
 * @author comp2
 */
public class AtividadeRepositorio {

	private final Connection connection;

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
		DAO.insert("ATIVIDADE")
				.fields(new Field("CODIGO", FieldType.INTEGER),
						new Field("DESCRICAO", FieldType.STRING),
						new Field("DATACADASTRO", FieldType.DATE),
						new Field("ESTAGIO", FieldType.INTEGER))
				.values(atividade.getCodigo(),
						atividade.getDescricao(),
						atividade.getDataCadastro(),
						atividade.getEstagio())
				.execute(connection);
	}

	private void updateAtividade(Atividade atividade) throws Exception {
		DAO.update("ATIVIDADE")
				.fields(new Field("CODIGO", FieldType.INTEGER),
						new Field("DESCRICAO", FieldType.STRING),
						new Field("DATACADASTRO", FieldType.DATE),
						new Field("ESTAGIO", FieldType.INTEGER))
				.values(atividade.getCodigo(),
						atividade.getDescricao(),
						atividade.getDataCadastro(),
						atividade.getEstagio())
				.whereEquals(new Field("CODIGO", FieldType.INTEGER), atividade.getCodigo())
				.execute(connection);
	}

	public void removerAtividade(Atividade atividade) throws Exception {
		DAO.delete("ATIVIDADE")
				.whereEquals(new Field("CODIGO", FieldType.INTEGER), atividade.getCodigo())
				.execute(connection);
	}

	public Atividade getAtividade(Integer codigo) throws Exception {
		Atividade atividade = null;
		
		ResultSet rs = DAO.select("ATIVIDADE")
				.fields(new Field("CODIGO", FieldType.INTEGER),
						new Field("DESCRICAO", FieldType.STRING),
						new Field("DATACADASTRO", FieldType.DATE),
						new Field("DATACONCLUSAO", FieldType.DATE),
						new Field("ESTAGIO", FieldType.INTEGER))
				.whereEquals(new Field("CODIGO", FieldType.INTEGER), codigo)
				.getResult(connection);
		
		while (rs.next()) {
			atividade = new Atividade();
			atividade.setCodigo(rs.getInt(1));
			atividade.setDescricao(rs.getString(2));
			atividade.setDataCadastro(rs.getDate(3));
			atividade.setDataConclusao(rs.getDate(4));
			atividade.setEstagio(rs.getInt(5));
		}
		
		return atividade;
	}

	public List<Atividade> getAtividades() throws Exception {
		List<Atividade> atividades = new ArrayList<>();

		ResultSet rs = DAO.select("ATIVIDADE")
				.fields(new Field("CODIGO", FieldType.INTEGER),
						new Field("DESCRICAO", FieldType.STRING),
						new Field("DATACADASTRO", FieldType.DATE),
						new Field("DATACONCLUSAO", FieldType.DATE),
						new Field("ESTAGIO", FieldType.INTEGER))
				.getResult(connection);

		while (rs.next()) {
			Atividade atividade = new Atividade();
			atividade.setCodigo(rs.getInt(1));
			atividade.setDescricao(rs.getString(2));
			atividade.setDataCadastro(rs.getDate(3));
			atividade.setDataConclusao(rs.getDate(4));
			atividade.setEstagio(rs.getInt(5));
			atividades.add(atividade);
		}

		return atividades;
	}

}
