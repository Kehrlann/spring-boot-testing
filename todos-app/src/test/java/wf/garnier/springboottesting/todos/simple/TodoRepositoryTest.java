package wf.garnier.springboottesting.todos.simple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(ContainersConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TodoRepositoryTest {

	@Autowired
	TodoRepository todoRepository;

	TodoService todoService;

	@BeforeEach
	void setUp() {
		todoService = new TodoService(todoRepository);
		todoService.addTodo("Talk about Spring Security", """
				It's the default library when you want to add authentication or Authorization to Spring apps.
				""");
		todoService.addTodo("Think about threat model", """
				In order to keep your apps secure, you want to know which kind of threats they are exposed to.
				""");
		todoService.addTodo("Deploy to production", """
				Josh's favorite place on the internet!
				""");
	}

	@Test
	void fullTextSearch() {
		var todos = todoService.searchByKeyword("secure");

		assertThat(todos).hasSize(2)
			.map(TodoItem::text)
			.containsExactlyInAnyOrder("Talk about Spring Security", "Think about threat model");
	}

}
