package wf.garnier.springboottesting;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This configuration emulates a slow startup
 */
@Configuration
class TodoConfiguration {

	Logger logger = LoggerFactory.getLogger(TodoConfiguration.class);

	@Bean
	public SlowStartingBean slowStartingBean() throws InterruptedException {
		logger.info("🐌 warming up slow-starting bean ...");
		Thread.sleep(Duration.ofSeconds(3L));
		logger.info("🐌 done!");
		return new SlowStartingBean();
	}

	public static class SlowStartingBean {
	}

}
