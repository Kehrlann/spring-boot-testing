package wf.garnier.springboottesting.todos.simple;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TodoConfiguration {

	Logger logger = LoggerFactory.getLogger(TodoConfiguration.class);

	record SlowStartingBean() {
	}

	@Bean
	public SlowStartingBean slowStartingBean() throws InterruptedException {
		logger.info("🐌 ... configuring ...");
		Thread.sleep(Duration.ofSeconds(1));
		logger.info("🐌 ... slow ...");
		Thread.sleep(Duration.ofSeconds(1));
		logger.info("🐌 ... bean ...");
		Thread.sleep(Duration.ofSeconds(1));
		logger.info("🐌🏆 ... slow ... bean ... successfully... configured ...");
		return new SlowStartingBean();
	}

	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> connectorCustomizer() {
		return (tomcat) -> tomcat.addContextValves(new TomcatLoggingValve());
	}

}
