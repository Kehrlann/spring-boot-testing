package wf.garnier.springboottesting;

import java.io.IOException;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest()
class TodoControllerTests {

	@Autowired
	private WebClient webClient;

	@MockBean
	private TodoService todoService;

	@BeforeEach
	void setUp() {
		webClient.getCookieManager().clearCookies();
	}

	@Test
	void noUser() throws IOException {
		HtmlPage htmlPage = webClient.getPage("/");
		var loginForm = htmlPage.getElementsByTagName("form").getFirst();
		assertThat(loginForm.getTextContent()).contains("Please sign in");
	}

	@Test
	@WithMockUser
	void title() throws IOException {
		HtmlPage htmlPage = webClient.getPage("/");
		var title = htmlPage.getElementsByTagName("h1").getFirst().getTextContent();
		assertThat(title).isEqualToIgnoringCase("todo");
	}

	@Test
	@WithMockUser
	void noTodos() throws IOException {
		HtmlPage htmlPage = webClient.getPage("/");
		var todos = htmlPage.querySelectorAll(".todo");
		assertThat(todos.getLength()).isEqualTo(0);
	}

	@Test
	@WithMockUser
	void hasTodos() throws IOException{
		when(todoService.getTodos()).thenReturn(
				List.of(
						new TodoItem(1L, "Do groceries"),
						new TodoItem(2L, "Cook dinner"),
						new TodoItem(3L, "Enjoy meal")
				));

		HtmlPage htmlPage = webClient.getPage("/");
		var todos = htmlPage.querySelectorAll(".todo > [data-role=\"content\"]");
		assertThat(todos)
				.hasSize(3)
				.extracting(DomNode::getTextContent)
				.map(String::trim)
				.containsExactly(
						"Do groceries",
						"Cook dinner",
						"Enjoy meal"
				);
	}

	@Test
	@WithMockUser
	void deleteTodo() throws IOException {
		when(todoService.getTodos()).thenReturn(
				List.of(
						new TodoItem(42L, "Do groceries"),
						new TodoItem(25L, "Buy the thing")
				));

		HtmlPage page = webClient.getPage("/");
		HtmlButton deleteButton = page.querySelectorAll(".todo")
				.stream()
				.filter(node -> node.getTextContent().contains("Buy the thing"))
				.toList()
				.get(0)
				.querySelector("button");
		deleteButton.click();

		verify(todoService).delete(25);
	}

	@Test
	@WithMockUser
	void addTodo() throws IOException {
		HtmlPage page = webClient.getPage("/");
		page.<HtmlTextInput>querySelector("input[name=\"text\"]").type("Try adding things");

		page.getElementById("add-button").click();

		verify(todoService).addTodo("Try adding things");
	}

}
