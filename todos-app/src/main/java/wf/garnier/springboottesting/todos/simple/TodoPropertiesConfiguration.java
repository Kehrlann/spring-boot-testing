package wf.garnier.springboottesting.todos.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TodoProperties.class)
@ConditionalOnProperty(value = "todo.profiles[0].name", matchIfMissing = false)
public class TodoPropertiesConfiguration {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public void configure(TodoProperties props) {
		logger.info("📝 found {} profile(s)", props.getProfiles().size());
		if (props.getProfiles().stream().map(TodoProperties.UserProfile::name).anyMatch("devoxx"::equals)) {
			throw new RuntimeException("BOOM");
		}
	}

}