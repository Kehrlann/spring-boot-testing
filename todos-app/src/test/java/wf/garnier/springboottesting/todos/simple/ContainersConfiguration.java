package wf.garnier.springboottesting.todos.simple;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;

@TestConfiguration
class ContainersConfiguration {

	@ServiceConnection
	@Bean
	PostgreSQLContainer<?> postgreSQLContainer() {
		return new PostgreSQLContainer<>("postgres:latest");
	}

}
