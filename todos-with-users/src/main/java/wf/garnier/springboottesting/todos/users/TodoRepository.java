package wf.garnier.springboottesting.todos.users;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

interface TodoRepository extends ListCrudRepository<TodoItem, Long> {

	List<TodoItem> findAllByUsername(String username);

	Integer deleteTodoItemByIdAndUsername(Long id, String username);

}
