package wf.garnier.springboottesting.todos.users;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import wf.garnier.testcontainers.dexidp.DexContainer;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Testcontainers
class TestContainersConfiguration {

	@Container
	public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:17.0");

	@Container
	@ServiceConnection
	public static DexContainer dexContainer = new DexContainer(
			DexContainer.DEFAULT_IMAGE_NAME.withTag(DexContainer.DEFAULT_TAG));

	@Bean
	@ServiceConnection
	public PostgreSQLContainer<?> postgresContainer() {
		return postgresContainer;
	}

	@Bean
	@ServiceConnection
	public DexContainer dexContainer() {
		return dexContainer;
	}

}
