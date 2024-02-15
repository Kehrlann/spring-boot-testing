package wf.garnier.springboottesting;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest()
class TodoControllerTests {

	@Autowired
	private WebClient webClient;

	@Test
	void title() throws IOException {
		HtmlPage htmlPage = webClient.getPage("/");
		var title = htmlPage.getElementsByTagName("h1").getFirst().getTextContent();
		assertThat(title).isEqualToIgnoringCase("todo");
	}

}
