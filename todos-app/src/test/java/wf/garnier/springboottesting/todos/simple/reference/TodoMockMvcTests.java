package wf.garnier.springboottesting.todos.simple.reference;

import org.junit.jupiter.api.BeforeEach;
import wf.garnier.springboottesting.todos.simple.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.test.web.servlet.client.assertj.RestTestClientResponse;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;
import static wf.garnier.springboottesting.todos.simple.reference.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestTestClient
//@Import(ContainersConfiguration.class)
class TodoMockMvcTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	TodoRepository todoRepository;

	@BeforeEach
	void setUp() {
		todoRepository.deleteAll();
	}

	void displaysTodo() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/todo").param("text", "this is a todo"))
			.andExpect(status().is3xxRedirection());

		mockMvc.perform(MockMvcRequestBuilders.get("/"))
			.andExpect(xpath("//*[@data-role=\"text\"]").string("this is a todo"));
	}

	@Autowired
	RestTestClient client;

	void restClient() {
		var response = client.get().uri("/").exchange();

		var resp = RestTestClientResponse.from(response);

		assertThat(resp).hasStatus2xxSuccessful();
		assertThat(resp).bodyText().contains("<h1>TODO</h1>");
	}

	@Autowired
	private MockMvcTester tester;

	void assertJ() {
		var resp = tester
			.perform(post("/todo").param("text", "hello devoxx").param("description", "it's good to be with you!"));
		assertThat(resp).hasStatus(HttpStatus.FOUND);
		assertThat(resp).redirectedUrl().isEqualTo("/");
	}

}
