package wf.garnier.springboottesting.todos.simple;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class TodoControllerTests {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	TodoService todoService;

	@Test
	void deleteTodo() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/todo/25/delete"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));

		verify(todoService).delete(25);
	}

}