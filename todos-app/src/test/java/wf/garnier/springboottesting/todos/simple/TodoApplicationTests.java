package wf.garnier.springboottesting.todos.simple;

import java.io.IOException;

import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlButton;
import org.htmlunit.html.HtmlElement;
import org.htmlunit.html.HtmlInput;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.html.HtmlTextArea;
import org.junit.jupiter.api.BeforeEach;
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

	@Autowired
	TodoService todoService;

	@Autowired
	TodoRepository todoRepository;

	@BeforeEach
	void setUp() {
		todoRepository.deleteAll();
	}

	@Test
	void displaysTodo() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/todo").param("text", "this is a todo"))
			.andExpect(status().is3xxRedirection());

		mockMvc.perform(MockMvcRequestBuilders.get("/"))
			.andExpect(xpath("//*[@data-role=\"text\"]").string("this is a todo"));
	}

	@Test
	void loadsWithBrowser() throws IOException {
		HtmlPage page = webClient.getPage("/");

		HtmlInput input = page.querySelector("form > input");
		HtmlButton button = (HtmlButton) page.getElementById("add-button");

		input.type("this is a todo");
		page = button.click();

		var addedToto = page.querySelector(".todo > [data-role=\"text\"]").getTextContent();
		assertThat(addedToto).isEqualTo("this is a todo");
	}

	@Test
	void browserOnlyJavascript() throws IOException {
		HtmlPage page = webClient.getPage("/");

		HtmlInput input = page.querySelector("form > input");
		HtmlTextArea textarea = page.querySelector("form > textarea");
		HtmlButton button = (HtmlButton) page.getElementById("add-button");

		input.type("This is a todo with a description");
		textarea.type("""
				This is a nice description which tells you what the TODO item is about.
				Useful, innit??
				""");
		page = button.click();

		var todoItem = page.querySelectorAll(".todo")
			.stream()
			.filter(node -> node.querySelector("[data-role=\"text\"]")
				.getTextContent()
				.equals("This is a todo with a description"))
			.findFirst()
			.get();

		var description = todoItem.querySelector("[data-role=\"description\"]");
		assertThat(description.isDisplayed()).isFalse();

		todoItem.<HtmlButton>querySelector("[data-role=\"toggle\"]").click();
		assertThat(description.isDisplayed()).isTrue();
	}

	@Test
	void javascriptToggle() throws IOException {
		todoService.addTodo("new todo", """
				this is a todo that will showcase toggling visibility
				very cool!
				""");

		HtmlPage page = webClient.getPage("/");

		HtmlElement description = page.querySelector("[data-role=\"description\"]");
		HtmlButton toggler = page.querySelector("[data-role=\"toggle\"]");

		assertThat(description.isDisplayed()).isFalse();

		toggler.click();

		assertThat(description.isDisplayed()).isTrue();
	}

}
