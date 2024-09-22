package wf.garnier.springboottesting.todos.simple;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface TodoRepository extends ListCrudRepository<TodoItem, Long> {

	@Query(value = "select t.* from todo_item t where t.ts @@ to_tsquery('english', ?1)", nativeQuery = true)
	List<TodoItem> searchByKeyword(String keyword);

}