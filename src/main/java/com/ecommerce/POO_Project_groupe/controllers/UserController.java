package com.ecommerce.POO_Project_groupe.controllers;

import com.ecommerce.POO_Project_groupe.models.User;
import com.ecommerce.POO_Project_groupe.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Inscription d'un utilisateur (Admin ou Regular)
    @PostMapping("/register")
    public User registerUser(@RequestBody Map<String, String> userData) {
        return userService.registerUser(userData);
    }

    // Récupérer tous les utilisateurs
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Récupérer un utilisateur par son email ou username
    @GetMapping("/{identifier}")
    public Optional<User> getUserByIdentifier(@PathVariable String identifier) {
        return userService.getUserByIdentifier(identifier);
    }

    // Login avec session
    @PostMapping("/login")
    public String loginUser(@RequestBody Map<String, String> credentials, HttpSession session) {
        return userService.loginUser(credentials, session);
    }

    // Déconnexion de l'utilisateur
    @PostMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate();
        return "Déconnexion réussie.";
    }

    // Récupérer l'historique des commandes d'un utilisateur connecté
    @GetMapping("/orders")
    public List<String> viewOrderHistory(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            throw new IllegalStateException("Aucun utilisateur connecté.");
        }
        return userService.viewOrderHistory(username);
    }    
}
