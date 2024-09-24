package wf.garnier.springboottesting.todos.simple;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class TodoController {

	private final TodoService todoService;

	TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	@GetMapping("/")
	public String index(@RequestParam(value = "q", required = false) String query, Model model) {
		var todos = StringUtils.hasLength(query) ? todoService.searchByKeyword(query) : todoService.getTodos();
		model.addAttribute("todos", todos);
		return "/index";
	}

	@PostMapping("/todo/{id}/delete")
	public String delete(@PathVariable("id") Long id) {
		todoService.delete(id);
		return "redirect:/";
	}

	@PostMapping("/todo")
	public String addTodo(@RequestParam("text") String text,
			@RequestParam(value = "description", required = false) String description) {
		todoService.addTodo(text, description);
		return "redirect:/";
	}

}
