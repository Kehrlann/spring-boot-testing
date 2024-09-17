package wf.garnier.springboottesting.todos.simple;

import java.io.IOException;

import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlButton;
import org.htmlunit.html.HtmlInput;
import org.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import static wf.garnier.springboottesting.todos.simple.Assertions.assertThat;
import static wf.garnier.springboottesting.todos.simple.Assertions.assertThatLog;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ContainersConfiguration.class)
@ExtendWith(OutputCaptureExtension.class)
class TodoApplicationWithTomcatTests {

	@LocalServerPort
	Long port;

	WebClient webClient = new WebClient();

	private String baseUrl;

	@BeforeEach
	void setUp() {
		baseUrl = "http://localhost:%s/".formatted(port);
	}

	@Test
	void logsIp(CapturedOutput output) throws IOException {
		webClient.getPage(baseUrl);
		//@formatter:off
		assertThat(output)
				.hasSize(2)
				.allSatisfy(logLine -> assertThatLog(logLine).hasIp("127.0.0.1").hasStatus(HttpStatus.OK))
				.first()
				.hasIp("127.0.0.1");
		//@formatter:on;
	}

	@Test
	void displaysPage() throws IOException {
		HtmlPage page = webClient.getPage(baseUrl);

		HtmlInput input = page.querySelector("form > input");
		HtmlButton button = (HtmlButton) page.getElementById("add-button");

		input.type("this is a todo");
		page = button.click();

		var addedToto = page.querySelector(".todo > [data-role=\"text\"]").getTextContent();
		assertThat(addedToto).isEqualTo("this is a todo");
	}

}
