package com.example.effectivemobileentertask.Controller;

import com.example.effectivemobileentertask.Entity.Role;
import com.example.effectivemobileentertask.Entity.User;
import com.example.effectivemobileentertask.Repository.UserService;
import com.example.effectivemobileentertask.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;


    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("title", "Страница регистрации");
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String doRegister(@ModelAttribute("user") User user){

        user.setBalance(0);
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));

        userService.saveUser(user);
        return "redirect:/login";
    }

}
