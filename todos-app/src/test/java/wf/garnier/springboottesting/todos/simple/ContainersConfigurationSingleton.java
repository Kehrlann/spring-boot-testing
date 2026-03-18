package wf.garnier.springboottesting.todos.simple;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;

@TestConfiguration(proxyBeanMethods = false)
@Testcontainers
class ContainersConfigurationSingleton {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Container
    private static final GenericContainer<?> nginx = new GenericContainer<>("nginx:alpine").withExposedPorts(80);

    @ServiceConnection
    @Bean
    PostgreSQLContainer<?> postgreSQLContainer() {
        return postgres;
    }

    @Bean
    GenericContainer<?> nginxContainer() {
        return nginx;
    }

    @Bean
    DynamicPropertyRegistrar dynamicPropertyRegistrar(GenericContainer nginxContainer) {
        return registry -> registry.add("external.service.url", () -> "http://localhost:" + nginxContainer.getFirstMappedPort());
    }

}
