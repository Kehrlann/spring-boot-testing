package wf.garnier.springboottesting.todos.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestTodoUsersApplication {

	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer() {
		return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
	}

	/**
	 * Launch the testing app, with TestContainers as the backing services.
	 * @param args -
	 */
	public static void main(String[] args) {
		SpringApplication.from(TodoUsersApplication::main)
			.with(TestTodoUsersApplication.class)
			.run(args);
	}

}
