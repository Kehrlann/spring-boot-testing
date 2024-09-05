package wf.garnier.springboottesting.todos.simple;

import java.io.IOException;

import org.htmlunit.WebClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ContainersConfiguration.class)
@ExtendWith(OutputCaptureExtension.class)
class TodoApplicationWithTomcatTests {

	@LocalServerPort
	Long port;

	WebClient webClient = new WebClient();

	@Test
	void logsIp(CapturedOutput output) throws IOException {
		webClient.getPage("http://localhost:%s/".formatted(port));
		assertThat(output.getOut()).contains("user with IP [127.0.0.1] requested [/]. We responded with [200]");
	}

}
