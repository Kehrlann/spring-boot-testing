package wf.garnier.springboottesting.todos.simple;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class TodoControllerTests {

	@Autowired
	MockMvc mockMvc;

	@MockitoBean
	TodoService todoService;

	@Test
	void deleteTodo() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/todo/25/delete"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/"));

		verify(todoService).delete(25);
	}

	@Test
	void addTodo() throws Exception {
		mockMvc
			.perform(MockMvcRequestBuilders.post("/todo")
				.param("text", "Test todo")
				.param("description", "This is a test todo, and it's fun!"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/"));

		verify(todoService).addTodo("Test todo", "This is a test todo, and it's fun!");
	}

}