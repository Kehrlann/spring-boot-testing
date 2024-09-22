package wf.garnier.springboottesting.todos.simple.reference;

import org.junit.jupiter.api.BeforeEach;
import wf.garnier.springboottesting.todos.simple.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestClient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;
import static wf.garnier.springboottesting.todos.simple.reference.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
// @Import(ContainersConfiguration.class)
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
	RestClient.Builder restClientBuilder;

	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	void restClient() {
		var client = restClientBuilder.requestFactory(new MockMvcClientHttpRequestFactory(mockMvc)).build();

		var response = client.get().uri("/").retrieve().toEntity(String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).contains("<h1>TODO</h1>");
	}

	void restTemplateBuilder() {
		var template = restTemplateBuilder.requestFactory(() -> new MockMvcClientHttpRequestFactory(mockMvc)).build();

		var response = template.getForEntity("/", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).contains("<h1>TODO</h1>");
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
