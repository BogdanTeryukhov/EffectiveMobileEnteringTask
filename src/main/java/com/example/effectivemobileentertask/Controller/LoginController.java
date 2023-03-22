package com.example.effectivemobileentertask.Controller;

import com.example.effectivemobileentertask.Entity.User;
import com.example.effectivemobileentertask.Repository.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
