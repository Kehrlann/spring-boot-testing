package wf.garnier.springboottesting;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
class TodoService {

	private final TodoRepository todoRepository;

	public TodoService(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	public List<TodoItem> getTodos() {
		return todoRepository.findAll();
	}

	public void delete(long id) {
		todoRepository.deleteById(id);
	}

	public TodoItem addTodo(String text) {
		return todoRepository.save(new TodoItem(null, text));
	}

}
