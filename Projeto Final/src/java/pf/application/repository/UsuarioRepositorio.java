package pf.application.repository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import pf.application.entity.Usuario;
import pf.framework.model.DAO;
import pf.framework.model.Field;
import pf.framework.model.FieldType;
import sun.misc.BASE64Encoder;

/**
 *
 * @author kurt
 */
public class UsuarioRepositorio {

	private static final Logger logger = Logger.getLogger(UsuarioRepositorio.class.getName());
	
	private final Connection connection;
	private static final String USUARIO = "USUARIO";
	private static final Field ID = new Field("ID", FieldType.INTEGER);
	private static final Field USERNAME = new Field("USERNAME", FieldType.STRING);
	private static final Field PASSWORD = new Field("PASSWORD", FieldType.STRING);
	
	public UsuarioRepositorio(Connection connection) {
		this.connection = connection;
	}
	
	public Usuario buscarPorUsernamePassword(String username, String password) throws Exception {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] bytes = digest.digest(password.getBytes());
			String hashPass = new BASE64Encoder().encode(bytes);
			return DAO.select(USUARIO)
					.fields(ID, USERNAME, PASSWORD)
					.whereEquals(USERNAME, username)
					.andEquals(PASSWORD, hashPass)
					.getSingleResult(connection, Usuario.class);
		} catch(NoSuchAlgorithmException ex) {
			logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
		}
		return null;
	}
	
}
