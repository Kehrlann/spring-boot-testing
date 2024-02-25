package wf.garnier.springboottesting.todos.simple;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
class TodoService {

	private final TodoRepository todoRepository;

	public TodoService(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	public TodoItem addTodo(String text) {
		return todoRepository.save(new TodoItem(null, text));
	}

	public List<TodoItem> getTodos() {
		return todoRepository.findAll();
	}

	@Transactional
	public void delete(long id) {
		todoRepository.deleteById(id);
	}

}
