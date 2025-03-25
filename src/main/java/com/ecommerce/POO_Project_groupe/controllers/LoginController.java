package com.ecommerce.POO_Project_groupe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showlogin(Model model) {
        model.addAttribute("pageTitle", "log");
        return "login"; 
    }
}
