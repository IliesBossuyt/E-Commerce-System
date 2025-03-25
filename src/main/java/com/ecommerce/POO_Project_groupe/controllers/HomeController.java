package com.ecommerce.POO_Project_groupe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String showHome(Model model) {
        model.addAttribute("pageTitle", "Accueil E-Commerce");
        return "home"; 
    }
}
