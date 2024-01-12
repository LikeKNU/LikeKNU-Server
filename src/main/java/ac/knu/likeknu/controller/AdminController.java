package ac.knu.likeknu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminController {

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/messages")
    public String registerMessageForm(Model model) {
        return "registerMessage";
    }
}
