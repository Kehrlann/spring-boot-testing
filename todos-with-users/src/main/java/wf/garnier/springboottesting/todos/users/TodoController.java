package wf.garnier.springboottesting.todos.users;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
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
	public String index(Model model, Authentication authentication) {
		var todos = todoService.getTodos(getName(authentication));
		model.addAttribute("todos", todos);
		return "/index";
	}

	@PostMapping("/todo/{id}/delete")
	public String delete(@PathVariable("id") Long id, Authentication authentication) {
		todoService.delete(id, getName(authentication));
		return "redirect:/";
	}

	@PostMapping("/todo")
	public String delete(@RequestParam("text") String text, Authentication authentication) {
		todoService.addTodo(text, getName(authentication));
		return "redirect:/";
	}

	private static String getName(Authentication authentication) {
		if (authentication.getPrincipal() instanceof OidcUser user) {
			return user.getEmail();
		}
		return authentication.getName();
	}

}
