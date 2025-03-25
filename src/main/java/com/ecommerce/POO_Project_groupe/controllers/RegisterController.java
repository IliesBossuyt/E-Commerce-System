package com.ecommerce.POO_Project_groupe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

    @GetMapping("/register")
    public String showregister(Model model) {
        model.addAttribute("pageTitle", "register");
        return "register"; 
    }
}
