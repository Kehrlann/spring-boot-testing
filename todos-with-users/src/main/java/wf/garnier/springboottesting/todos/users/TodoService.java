package wf.garnier.springboottesting.todos.users;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
class TodoService {

	private final TodoRepository todoRepository;

	public TodoService(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	public TodoItem addTodo(String text, String username) {
		return todoRepository.save(new TodoItem(null, text, username));
	}

	public List<TodoItem> getTodos(String username) {
		return todoRepository.findAllByUsername(username);
	}

	@Transactional
	public void delete(long id, String username) {
		todoRepository.deleteTodoItemByIdAndUsername(id, username);
	}

}
