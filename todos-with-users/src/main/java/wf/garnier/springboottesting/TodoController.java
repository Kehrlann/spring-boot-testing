package wf.garnier.springboottesting;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public String index(Model model, @AuthenticationPrincipal UserDetails user) {
        var todos = todoService.getTodos(user.getUsername());
        model.addAttribute("todos", todos);
        return "/index";
    }

    @PostMapping("/todo/{id}/delete")
    public String delete(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails user) {
        todoService.delete(id, user.getUsername());
        return "redirect:/";
    }

    @PostMapping("/todo")
    public String delete(@RequestParam("text") String text, @AuthenticationPrincipal UserDetails user) {
        todoService.addTodo(text, user.getUsername());
        return "redirect:/";
    }
}
