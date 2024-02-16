package wf.garnier.springboottesting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestSpringBootTestingApplication {

	static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(
			DockerImageName.parse("postgres:latest"));

	static {
		postgreSQLContainer.start();
	}

	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer() {
		return postgreSQLContainer;
	}

	public static void main(String[] args) {
		SpringApplication.from(SpringBootTestingApplication::main)
			.with(TestSpringBootTestingApplication.class)
			.run(args);
	}

}
