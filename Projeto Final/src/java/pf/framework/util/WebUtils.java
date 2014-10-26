package pf.framework.util;

import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import pf.framework.controller.ApplicationConstants;
import pf.framework.web.WebContext;

/**
 *
 * @author kurt
 */
public class WebUtils {

	public static final WebContext getWebContext(HttpServletRequest request) {
		return (WebContext) request.getAttribute(ApplicationConstants.WEB_CONTEXT);
	}
	
	public static final Connection getConnection(HttpServletRequest request) {
		return (Connection) request.getAttribute(ApplicationConstants.CONNECTION);
	}
	
}
