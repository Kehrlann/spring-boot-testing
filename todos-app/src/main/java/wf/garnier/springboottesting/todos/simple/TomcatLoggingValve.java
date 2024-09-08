package wf.garnier.springboottesting.todos.simple;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Showcases some Servlet container-only business logic, that does not show up in MockMvc
 * tests. Do NOT do this in production. Use e.g.
 * {@link org.apache.catalina.valves.AccessLogValve}, which we are not using here because
 * it writes to a file rather than stdout. You can trick it to write to stdout by having
 * it write to {@code /dev/stdout} but that can't be intercepted by the output capture.
 */
class TomcatLoggingValve extends ValveBase {

	Logger logger = LoggerFactory.getLogger(TomcatLoggingValve.class);

	@Override
	public void invoke(Request request, Response response) throws IOException, ServletException {
		var status = ((HttpServletResponse) response).getStatus();
		logger.info("🕵️ user with IP [{}] requested [{}]. We responded with [{}].", request.getRemoteAddr(),
				request.getRequestURI(), status);
		getNext().invoke(request, response);
	}

}
