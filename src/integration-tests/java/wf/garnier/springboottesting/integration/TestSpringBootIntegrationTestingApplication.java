package java.wf.garnier.springboottesting.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import wf.garnier.springboottesting.SpringBootTestingApplication;

@TestConfiguration(proxyBeanMethods = false)
public class TestSpringBootIntegrationTestingApplication {

	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer() {
		return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
	}

	public static void main(String[] args) {
		SpringApplication.from(SpringBootTestingApplication::main)
			.with(TestSpringBootIntegrationTestingApplication.class)
			.run(args);
	}

}
