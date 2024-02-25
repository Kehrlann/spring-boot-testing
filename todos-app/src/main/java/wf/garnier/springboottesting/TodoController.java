package wf.garnier.springboottesting;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String index(Model model) {
        var todos = todoService.getTodos();
        model.addAttribute("todos", todos);
        return "/index";
    }

    @PostMapping("/todo/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        todoService.delete(id);
        return "redirect:/";
    }

    @PostMapping("/todo")
    public String delete(@RequestParam("text") String text) {
        todoService.addTodo(text);
        return "redirect:/";
    }
}
