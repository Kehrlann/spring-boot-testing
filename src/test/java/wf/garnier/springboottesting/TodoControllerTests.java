package wf.garnier.springboottesting;

import java.io.IOException;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@WebMvcTest
class TodoControllerTests {

	@MockBean
	private TodoService todoService;

	@Nested
	class MockMvcTests {

		@Autowired
		private MockMvc mockMvc;

		@Test
		void noUser() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().is(401));
		}

		@Test
		@WithMockUser
		void title() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.get("/"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(xpath("//h1").string(Matchers.containsString("TODO")));
		}

		@Test
		void securityProcessors() throws Exception {
			var user = User.withUsername("alice").password("~~ignored~~").build();
			var alice = UsernamePasswordAuthenticationToken.authenticated(user, null, AuthorityUtils.NO_AUTHORITIES);
			mockMvc.perform(MockMvcRequestBuilders.get("/").with(authentication(alice)))
				.andExpect(status().is2xxSuccessful());
		}

		@Test
		@WithMockUser("test-user")
		void addTodo() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.post("/todo").param("text", "Try adding things").with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));

			verify(todoService).addTodo("Try adding things", "test-user");
		}

		@Test
		@WithMockUser("test-user")
		void addTodoMissingCsrf() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.post("/todo").param("text", "Try adding things"))
				.andExpect(status().is4xxClientError());

			verifyNoInteractions(todoService);
		}

	}

	@Nested
	class HtmlUnitTests {

		@Autowired
		private WebClient webClient;

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
		@WithMockUser("test-user")
		void hasTodos() throws IOException {
			var username = "test-user";
			when(todoService.getTodos(username)).thenReturn(List.of(new TodoItem(1L, "Do groceries", username),
					new TodoItem(2L, "Cook dinner", username), new TodoItem(3L, "Enjoy meal", username)));

			HtmlPage htmlPage = webClient.getPage("/");
			var todos = htmlPage.querySelectorAll(".todo > [data-role=\"content\"]");
			assertThat(todos).hasSize(3)
				.extracting(DomNode::getTextContent)
				.map(String::trim)
				.containsExactly("Do groceries", "Cook dinner", "Enjoy meal");
		}

		@Test
		@WithMockUser("test-user")
		void deleteTodo() throws IOException {
			var username = "test-user";
			when(todoService.getTodos(username)).thenReturn(
					List.of(new TodoItem(42L, "Do groceries", username), new TodoItem(25L, "Buy the thing", username)));

			HtmlPage page = webClient.getPage("/");
			HtmlButton deleteButton = page.querySelectorAll(".todo")
				.stream()
				.filter(node -> node.getTextContent().contains("Buy the thing"))
				.toList()
				.get(0)
				.querySelector("button");
			deleteButton.click();

			verify(todoService).delete(25, username);
		}

		@Test
		@WithMockUser("test-user")
		void addTodo() throws IOException {
			HtmlPage page = webClient.getPage("/");
			page.<HtmlTextInput>querySelector("input[name=\"text\"]").type("Try adding things");

			page.getElementById("add-button").click();

			verify(todoService).addTodo("Try adding things", "test-user");
		}

	}

}
