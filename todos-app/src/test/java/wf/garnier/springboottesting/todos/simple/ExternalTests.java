package wf.garnier.springboottesting.todos.simple;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestClient;
import static wf.garnier.springboottesting.todos.simple.Assertions.assertThat;

@SpringBootTest
@Import(ContainersConfigurationSingleton.class)
class ExternalTests {

    @Test
    void connectToExternalService(@Value("${external.service.url:}") String externalServiceUrl) {
        assertThat(externalServiceUrl).isNotBlank();

        var nginxResp = RestClient.create().get().uri(externalServiceUrl)
                .retrieve()
                .body(String.class);

        assertThat(nginxResp).contains("Welcome to nginx!");
    }
}
