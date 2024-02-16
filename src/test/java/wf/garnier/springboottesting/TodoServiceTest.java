package wf.garnier.springboottesting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestSpringBootTestingApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TodoServiceTest {

	@Autowired
	TodoRepository todoRepository;

	private TodoService todoService;

	@BeforeEach
	void beforeEach() {
		todoService = new TodoService(todoRepository);
		todoRepository.deleteAll();
	}

	@Test
	void empty() {
		var todos = todoService.getTodos();

		assertThat(todos).hasSize(0);
	}

	@Test
	void add() {
		todoService.addTodo("first thing to do");
		todoService.addTodo("second thing to do");
		todoService.addTodo("third thing to do");

		var todos = todoService.getTodos();

		assertThat(todos).hasSize(3)
			.map(TodoItem::text)
			.containsExactly("first thing to do", "second thing to do", "third thing to do");
	}

	@Test
	void delete() {
		var todo = todoService.addTodo("something");

		todoService.delete(todo.id());

		assertThat(todoService.getTodos()).isEmpty();
	}
}
