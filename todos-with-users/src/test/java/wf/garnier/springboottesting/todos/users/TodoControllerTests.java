package wf.garnier.springboottesting.todos.users;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@WebMvcTest
@Import(SecurityConfiguration.class)
class TodoControllerTests {

	@MockitoBean
	private TodoService todoService;

	@MockitoBean
	ClientRegistrationRepository clientRegistrationRepository;

	@Nested
	class MockMvcTests {

		@Autowired
		private MockMvc mockMvc;

		@Test
		void noUser() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.get("/"))
				.andExpect(status().is(HttpStatus.FOUND.value()))
				.andExpect(redirectedUrl("http://localhost/login"));
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

}
