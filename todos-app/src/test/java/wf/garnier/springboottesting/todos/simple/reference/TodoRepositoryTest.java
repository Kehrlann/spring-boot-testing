package wf.garnier.springboottesting.todos.simple.reference;

import wf.garnier.springboottesting.todos.simple.TodoItem;
import wf.garnier.springboottesting.todos.simple.TodoRepository;
import wf.garnier.springboottesting.todos.simple.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
//@Import(ContainersConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TodoRepositoryTest {

	@Autowired
	TodoRepository todoRepository;

	TodoService todoService;

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

	void fullTextSearch() {
		var todos = todoService.searchByKeyword("secure");

		assertThat(todos).hasSize(2)
			.map(TodoItem::text)
			.containsExactlyInAnyOrder("Talk about Spring Security", "Think about threat model");
	}

}
