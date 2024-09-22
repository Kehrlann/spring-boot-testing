package wf.garnier.springboottesting.todos.simple.reference;

import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
//@Import(ContainersConfigurationSingleton.class)
//@Tag("integ")
class ExternalTests {

	void connectToExternalService(@Value("${external.service.url:}") String externalServiceUrl) {
		assertThat(externalServiceUrl).isNotBlank();

		var nginxResp = RestClient.create().get().uri(externalServiceUrl).retrieve().body(String.class);

		assertThat(nginxResp).contains("Welcome to nginx!");
	}

}
