package wf.garnier.springboottesting.todos.simple;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;

@Testcontainers
@TestConfiguration
class ContainersConfigurationSingleton {

	@Container
	static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

	@Bean
	@ServiceConnection
	public PostgreSQLContainer<?> postgreSQLContainer() {
		return postgreSQLContainer;
	}

}
