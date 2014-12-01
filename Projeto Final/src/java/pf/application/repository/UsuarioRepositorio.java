package pf.application.repository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import pf.application.entity.Usuario;
import pf.framework.model.DAO;
import sun.misc.BASE64Encoder;

/**
 *
 * @author kurt
 */
public class UsuarioRepositorio {

	private static final Logger logger = Logger.getLogger(UsuarioRepositorio.class.getName());
	
	private final Connection connection;
	
	public UsuarioRepositorio(Connection connection) {
		this.connection = connection;
	}
	
	public Usuario buscarPorUsernamePassword(String username, String password) throws Exception {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] bytes = digest.digest(password.getBytes());
			String hashPass = new BASE64Encoder().encode(bytes);
			return DAO.select(Usuario.class)
					.fields(Usuario.TODOS)
					.whereEquals("username", username)
					.andEquals("password", hashPass)
					.getSingleResult(connection);
		} catch(NoSuchAlgorithmException ex) {
			logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
		}
		return null;
	}
	
}
