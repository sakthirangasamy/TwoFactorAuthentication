package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; // This resolves to src/main/resources/templates/index.html
    }
//    @GetMapping("/login")
//    public String login() {
//        return "login"; // Ensure there is a login.html in your templates folder
//    }

}
