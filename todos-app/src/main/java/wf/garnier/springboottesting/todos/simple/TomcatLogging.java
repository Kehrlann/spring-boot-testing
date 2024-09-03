package wf.garnier.springboottesting.todos.simple;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.RequestFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

@Component
class TomcatLogging implements Filter {

	Logger logger = LoggerFactory.getLogger(TomcatLogging.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(request, response);
		var status = ((HttpServletResponse) response).getStatus();
		logger.info("🕵️ user with IP [{}] requested [{}]. We responded with [{}].", request.getRemoteAddr(),
				((RequestFacade) request).getRequestURI(), status);
	}

}
