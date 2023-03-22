package com.example.effectivemobileentertask.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Principal principal){
        return "home";
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
