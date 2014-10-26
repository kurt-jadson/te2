package pf.framework.util;

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
	
}
