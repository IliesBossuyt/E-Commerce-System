package com.ecommerce.POO_Project_groupe.controllers;

import com.ecommerce.POO_Project_groupe.models.User;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private List<User> users = new ArrayList<>(); // Simule une base de données temporaire

    // Inscription d'un utilisateur
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        users.add(user);
        return user;
    }

    // Récupérer tous les utilisateurs
    @GetMapping
    public List<User> getAllUsers() {
        return users;
    }

    // Récupérer un utilisateur par son email ou username
    @GetMapping("/{identifier}")
    public Optional<User> getUserByIdentifier(@PathVariable String identifier) {
        return users.stream()
                .filter(user -> user.getUsername().equals(identifier) || user.getEmail().equals(identifier))
                .findFirst();
    }

    // Vérifier le login
    @PostMapping("/login")
    public String loginUser(@RequestParam String identifier, @RequestParam String password) {
        Optional<User> userOptional = users.stream()
                .filter(user -> user.getUsername().equals(identifier) || user.getEmail().equals(identifier))
                .findFirst();

        if (userOptional.isPresent() && userOptional.get().login(identifier, password)) {
            return "Login successful!";
        }
        return "Invalid username/email or password.";
    }
}