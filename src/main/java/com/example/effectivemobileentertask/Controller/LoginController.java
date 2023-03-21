package com.example.effectivemobileentertask.Controller;

import com.example.effectivemobileentertask.Entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model){
        User user = new User();

        model.addAttribute("title", "Страница входа");
        model.addAttribute("makeUser", user);
        return "login";
    }

}
