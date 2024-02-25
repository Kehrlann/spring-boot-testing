package wf.garnier.springboottesting.todos.simple;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@SpringBootTest
@AutoConfigureMockMvc
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

}
