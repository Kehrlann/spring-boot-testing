package wf.garnier.springboottesting.todos.users;

import java.io.IOException;
import java.util.UUID;

import org.htmlunit.WebClient;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.HtmlButton;
import org.htmlunit.html.HtmlInput;
import org.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Import(TestTodoUsersApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TodoUsersApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private WebClient webClient;

	private final String todoText = "test-add-todo-" + UUID.randomUUID();

	@BeforeEach
	void setUp() {
		webClient.getOptions().setCssEnabled(false);
		logout();
	}

	@Test
	@Order(1)
	void noUser() throws IOException {
		HtmlPage htmlPage = webClient.getPage("/");
		var loginForm = htmlPage.getElementsByTagName("form").getFirst();
		assertThat(loginForm.getTextContent()).contains("Please sign in");
	}

	@Test
	@Order(2)
	void showsTitle() throws IOException {
		HtmlPage htmlPage = login("alice", "password");
		var title = htmlPage.getElementsByTagName("h1").getFirst().getTextContent();
		assertThat(title).isEqualToIgnoringCase("todo");
	}

	@Test
	@Order(3)
	void noTodos() throws IOException {
		HtmlPage htmlPage = login("alice", "password");
		var todos = htmlPage.querySelectorAll(".todo");
		assertThat(todos.getLength()).isEqualTo(0);
	}

	@Test
	@Order(4)
	void addTodos() throws IOException {
		HtmlPage htmlPage = login("alice", "password");
		HtmlInput newTodoField = (HtmlInput) htmlPage.getElementById("new-todo");
		HtmlButton addTodoButton = (HtmlButton) htmlPage.getElementById("add-button");

		newTodoField.type(todoText);
		htmlPage = addTodoButton.click();

		var todos = htmlPage.querySelectorAll(".todo > [data-role=\"content\"]");
		assertThat(todos).hasSize(1).last().extracting(DomNode::getTextContent).isEqualTo(todoText);
	}

	@Test
	@Order(5)
	void otherUser() throws IOException {
		HtmlPage htmlPage = login("bob", "password");
		var todos = htmlPage.querySelectorAll(".todo");
		assertThat(todos).hasSize(0);
	}

	/**
	 * This test depends on {@link #addTodos()}. A test could also be independent by
	 * adding a todoItem and then deleting it.
	 * @throws IOException -
	 */
	@Test
	@Order(6)
	void deleteTodo() throws IOException {
		HtmlPage htmlPage = login("alice", "password");

		HtmlButton completeTodoButton = htmlPage.querySelector(".todo button");
		htmlPage = completeTodoButton.click();

		var todos = htmlPage.querySelectorAll(".todo");
		assertThat(todos).hasSize(0);
	}

	private HtmlPage login(String username, String password) throws IOException {
		HtmlPage loginPage = webClient.getPage("/");
		HtmlInput usernameField = (HtmlInput) loginPage.getElementById("username");
		HtmlInput passwordField = (HtmlInput) loginPage.getElementById("password");
		HtmlButton submitButton = (HtmlButton) loginPage.getElementsByTagName("button").getFirst();

		usernameField.type(username);
		passwordField.type(password);

		return submitButton.click();
	}

	private void logout() {
		webClient.getCookieManager().clearCookies();
	}

}
