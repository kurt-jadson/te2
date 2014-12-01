package pf.application.repository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import pf.application.entity.Desenho;
import pf.application.entity.DesenhoEpisodio;
import pf.application.entity.DesenhoIdioma;
import pf.application.entity.Episodio;
import pf.application.entity.Idioma;
import pf.framework.model.DAO;

/**
 *
 * @author kurt
 */
public class DesenhoRepositorio {
	
	private final Connection connection;

	public DesenhoRepositorio(Connection connection) {
		this.connection = connection;
	}
	
	public void salvar(Desenho desenho) throws Exception {
		connection.setAutoCommit(false);
		desenho = salvarDesenho(desenho);
		salvarIdiomas(desenho);
		salvarEpisodios(desenho);
		connection.commit();
	}

	private void salvarIdiomas(Desenho desenho) throws Exception {
		DAO.delete(DesenhoIdioma.class).whereEquals("desenho", desenho.getId()).execute(connection);
		for (Idioma idioma : desenho.getIdiomas()) {
			DesenhoIdioma desenhoIdioma = new DesenhoIdioma(desenho.getId(), idioma.getId());
			DAO.insert(desenhoIdioma)
					.fields("desenho", "idioma")
					.execute(connection);
		}
	}
	
	private void salvarEpisodios(Desenho desenho) throws Exception {
		DAO.delete(DesenhoEpisodio.class).whereEquals("desenho", desenho.getId()).execute(connection);
		for(Episodio episodio : desenho.getEpisodios()) {
			DesenhoEpisodio desenhoEpisodio = new DesenhoEpisodio();
			desenhoEpisodio.setDesenho(desenho.getId());
			desenhoEpisodio.setNome(episodio.getNome());
			
			DAO.insert(desenhoEpisodio)
					.fields("nome", "desenho")
					.execute(connection);
		}
	}
	
	private Desenho salvarDesenho(Desenho desenho) throws Exception {
		DAO dao = DAO.createInsertOrUpdate(desenho);
		dao.fields(Desenho.TODOS_SEM_ID);

		if (!desenho.isNew()) {
			dao.whereEquals("id", desenho.getId());
		}

		dao.execute(connection);

		if (desenho.isNew()) {
			Desenho persistido = DAO.selectMax(Desenho.class, "id")
					.fields("id")
					.getSingleResult(connection);
			desenho.setId(persistido.getId());
		}

		return desenho;
	}
	
	public void remover(Desenho desenho) throws Exception {
		connection.setAutoCommit(false);
		DAO.delete(DesenhoIdioma.class).whereEquals("desenho", desenho.getId()).execute(connection);
		DAO.delete(DesenhoEpisodio.class).whereEquals("desenho", desenho.getId()).execute(connection);
		DAO.delete(Desenho.class).whereEquals("id", desenho.getId()).execute(connection);
		connection.commit();
	}
	
	public List<Desenho> buscarAcervo() throws Exception {
		return DAO.select(Desenho.class)
				.fields(Desenho.TODOS)
				.getResult(connection);
	}
	
	public Desenho buscarPorId(Integer id) throws Exception {
		return DAO.select(Desenho.class)
				.fields(Desenho.TODOS)
				.whereEquals("id", id)
				.getSingleResult(connection);
	}
	
	public Desenho buscarPorIdFetchListas(Integer id) throws Exception {
		Desenho desenho = buscarPorId(id);
		desenho.addAllIdiomas(buscarIdiomas(desenho));
		desenho.addAllEpisodios(buscarEpisodios(desenho));
		return desenho;
	}
	
	public List<Desenho> buscarPorTitulo(String titulo) throws Exception {
		return DAO.select(Desenho.class)
				.fields(Desenho.TODOS)
				.whereLike("titulo", "%" + titulo + "%")
				.getResult(connection);
	}
	
	private List<Idioma> buscarIdiomas(Desenho desenho) throws Exception {
		List<DesenhoIdioma> idiomas = DAO.select(DesenhoIdioma.class)
				.fields("desenho", "idioma")
				.whereEquals("desenho", desenho.getId())
				.getResult(connection);
		
		if(idiomas.isEmpty()) {
			return Collections.EMPTY_LIST;
		}
		
		Integer[] idiomasIds = new Integer[idiomas.size()];
		for(int i = 0, j = idiomas.size(); i < j; i++) {
			idiomasIds[i] = idiomas.get(i).getIdioma();
		}
		
		return DAO.select(Idioma.class)
				.fields(Idioma.TODOS)
				.whereIn("id", idiomasIds)
				.getResult(connection);
	}
		
	private List<Episodio> buscarEpisodios(Desenho desenho) throws Exception {
		List<DesenhoEpisodio> desenhoEpisodios = DAO.select(DesenhoEpisodio.class)
				.fields(DesenhoEpisodio.TODOS)
				.whereEquals("desenho", desenho.getId())
				.getResult(connection);
		
		if(desenhoEpisodios.isEmpty()) {
			return Collections.EMPTY_LIST;
		}
		
		List<Episodio> episodios = new ArrayList<>();
		for(DesenhoEpisodio desenhoEpisodio : desenhoEpisodios) {
			Episodio episodio = new Episodio();
			episodio.setId(desenhoEpisodio.getId());
			episodio.setNome(desenhoEpisodio.getNome());
			episodios.add(episodio);
		}
		
		return episodios;
	}
		
}