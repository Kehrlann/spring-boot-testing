package wf.garnier.springboottesting;

import org.springframework.data.repository.ListCrudRepository;

interface TodoRepository extends ListCrudRepository<TodoItem, Long> {

}