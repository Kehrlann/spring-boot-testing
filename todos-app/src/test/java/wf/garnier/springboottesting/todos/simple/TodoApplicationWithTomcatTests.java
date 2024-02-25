package wf.garnier.springboottesting.todos.simple;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.WebClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled
class TodoApplicationWithTomcatTests {

	@LocalServerPort
	Long port;

	WebClient webClient = new WebClient();

	@Test
	void logsIp() throws IOException {
		webClient.getPage("http://localhost:%s/".formatted(port));
	}

}
