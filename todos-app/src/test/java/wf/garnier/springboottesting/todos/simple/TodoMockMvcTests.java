package wf.garnier.springboottesting.todos.simple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@SpringBootTest
@AutoConfigureMockMvc
@Import(ContainersConfiguration.class)
class TodoMockMvcTests {

	@Autowired
	MockMvc mockMvc;

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

}
