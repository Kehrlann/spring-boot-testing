package wf.garnier.springboottesting.todos.users;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestContainersConfiguration.class)
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
		var todos = todoService.getTodos("alice");

		assertThat(todos).hasSize(0);
	}

	@Test
	void add() {
		var username = "alice";
		todoService.addTodo("first thing to do", username);
		todoService.addTodo("second thing to do", username);
		todoService.addTodo("third thing to do", username);

		var todos = todoService.getTodos(username);

		assertThat(todos).hasSize(3)
			.map(TodoItem::text)
			.containsExactly("first thing to do", "second thing to do", "third thing to do");
	}

	@Test
	void multipleUsers() {
		var firstUsername = "alice";
		var secondUsername = "bob";
		todoService.addTodo("first", firstUsername);
		todoService.addTodo("second", secondUsername);

		assertThat(todoService.getTodos(firstUsername)).hasSize(1).map(TodoItem::text).containsExactly("first");
		assertThat(todoService.getTodos(secondUsername)).hasSize(1).map(TodoItem::text).containsExactly("second");
		assertThat(todoService.getTodos("carol")).isEmpty();
	}

	@Test
	void delete() {
		var username = "alice";
		var todo = todoService.addTodo("something", username);

		todoService.delete(todo.id(), username);

		assertThat(todoService.getTodos(username)).isEmpty();
	}

	@Test
	void deleteSomeoneElsesTodo() {
		var username = "alice";
		var todo = todoService.addTodo("something", username);

		todoService.delete(todo.id(), "bob");

		assertThat(todoService.getTodos(username)).hasSize(1).containsExactly(todo);
	}

}
