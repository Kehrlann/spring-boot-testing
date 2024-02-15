package wf.garnier.springboottesting;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class TodoController {

    TodoController() {
    }


    @GetMapping("/")
    public String index() {
        return "/index";
    }
}
