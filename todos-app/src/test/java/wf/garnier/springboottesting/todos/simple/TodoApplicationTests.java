package wf.garnier.springboottesting.todos.simple;

import java.io.IOException;

import org.htmlunit.WebClient;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.HtmlButton;
import org.htmlunit.html.HtmlInput;
import org.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(ContainersConfiguration.class)
class TodoApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	WebClient webClient;

	@Test
	void contextLoads() {
	}

	@Test
	void loads() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/todo").param("text", "this is a todo"))
			.andExpect(status().is3xxRedirection());

		mockMvc.perform(MockMvcRequestBuilders.get("/"))
			.andExpect(xpath("//*[@data-role=\"content\"]").string("this is a todo"));
	}

	@Test
	void loadsWithBrowser() throws IOException {
		HtmlPage page = webClient.getPage("/");

		HtmlInput input = page.querySelector("form > input");
		HtmlButton button = (HtmlButton) page.getElementById("add-button");

		input.type("this is a todo");
		page = button.click();

		var addedToto = page.querySelector(".todo > [data-role=\"content\"]").getTextContent();
		assertThat(addedToto).isEqualTo("this is a todo");
	}

	@Test
	void itDoesJavascript() throws IOException {
		HtmlPage page = webClient.getPage("/");

		HtmlInput input = page.querySelector("form > input");
		HtmlButton button = (HtmlButton) page.getElementById("add-button");

		input.type("this is an INVALID todo");
		page = button.click();

		var allTodos = page.querySelectorAll(".todo > [data-role=\"content\"]").stream().map(DomNode::getTextContent);
		assertThat(allTodos).doesNotContain("this is an INVALID todo");
	}

}
