package wf.garnier.springboottesting.todos.simple;

import org.springframework.data.repository.ListCrudRepository;

interface TodoRepository extends ListCrudRepository<TodoItem, Long> {

}