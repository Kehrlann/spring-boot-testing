package wf.garnier.springboottesting.todos.simple.reference;

import java.io.IOException;

import org.htmlunit.WebClient;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.HtmlButton;
import org.htmlunit.html.HtmlElement;
import org.htmlunit.html.HtmlInput;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.html.HtmlTextArea;
import wf.garnier.springboottesting.todos.simple.TodoRepository;
import wf.garnier.springboottesting.todos.simple.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
// @Import(ContainersConfiguration.class)
class TodoWebClientTests {

	@Autowired
	WebClient webClient;

	@Autowired
	TodoService todoService;

	@Autowired
	TodoRepository todoRepository;

	void setUp() {
		todoRepository.deleteAll();
	}

	void addTodo() throws IOException {
		HtmlPage page = webClient.getPage("/");

		HtmlInput input = page.querySelector("form > input");
		HtmlTextArea textArea = page.querySelector("form > textarea");
		HtmlButton button = (HtmlButton) page.getElementById("add-button");

		input.type("this is a todo");
		textArea.type("and it has a description");
		page = button.click();

		var todoText = page.querySelector(".todo > [data-role=\"text\"]").getTextContent();
		var todoDescription = page.querySelector(".todo > [data-role=\"description\"]").getTextContent();
		assertThat(todoText).isEqualTo("this is a todo");
		assertThat(todoDescription).isEqualTo("and it has a description");
	}

	void completeTodo() throws IOException {
		todoService.addTodo("new todo", null);

		HtmlPage page = webClient.getPage("/");

		HtmlButton completeTodo = page.querySelector(".todo > form > button");
		page = completeTodo.click();

		assertThat(page.querySelectorAll(".todo")).isEmpty();
	}

	void noDescription() throws IOException {
		todoService.addTodo("new todo", null);

		HtmlPage page = webClient.getPage("/");

		HtmlButton toggleButton = page.querySelector(".todo > [data-role=\"toggle\"]");

		assertThat(toggleButton.isDisabled()).isTrue();
	}

	void javascriptToggle() throws IOException {
		todoService.addTodo("new todo", """
				this is a todo that will showcase toggling visibility
				very cool!
				""");

		HtmlPage page = webClient.getPage("/");

		HtmlElement description = page.querySelector("[data-role=\"description\"]");
		assertThat(description.isDisplayed()).isFalse();

		page.<HtmlButton>querySelector("[data-role=\"toggle\"]").click();

		assertThat(description.isDisplayed()).isTrue();
	}

	void searchTodo() throws IOException {
		todoService.addTodo("Secure todo", "This is top secret");
		todoService.addTodo("Open todo", "Everyone can see this");
		todoService.addTodo("A todo", "Should this be secured?");

		HtmlPage page = webClient.getPage("/");
		page.<HtmlInput>querySelector("#search-input").type("secure");
		page = page.getElementById("search-button").click();

		var todos = page.querySelectorAll("[data-role=\"text\"]").stream().map(DomNode::getTextContent);

		assertThat(todos).hasSize(2).containsExactlyInAnyOrder("A todo", "Secure todo");
	}

	void clearSearch() throws IOException {
		todoService.addTodo("Secure todo", "This is top secret");
		todoService.addTodo("Open todo", "Everyone can see this");
		todoService.addTodo("A todo", "Should this be secured?");

		HtmlPage page = webClient.getPage("/?q=secure");
		page = page.getElementById("clear-button").click();

		var todos = page.querySelectorAll("[data-role=\"text\"]").stream().map(DomNode::getTextContent);

		assertThat(todos).hasSize(3);
	}

	void parseString() throws IOException {
		var htmlString = """
				<html>
				<body>
				  <h1>Hello world</h1>
				  <ul>
				    <li class="todo">One</li>
				    <li class="todo">Two</li>
				    <li class="todo">three</li>
				  </ul>
				</body>
				</html>
				""";
		HtmlPage page = webClient.loadHtmlCodeIntoCurrentWindow(htmlString);
		var textContent = page.querySelector("li.todo:last-child").getTextContent();
		assertThat(textContent).isEqualTo("three");
	}

}
