package com.ecommerce.POO_Project_groupe.services;

import com.ecommerce.POO_Project_groupe.models.AdminUser;
import com.ecommerce.POO_Project_groupe.models.PremiumUser;
import com.ecommerce.POO_Project_groupe.models.RegularUser;
import com.ecommerce.POO_Project_groupe.models.User;
import com.ecommerce.POO_Project_groupe.models.Order;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();

    @PostConstruct
    public void initUsers() {
        users.add(new AdminUser("admin", "admin@example.com", "adminpassword"));
        users.add(new RegularUser("john_doe", "john@example.com", "password123"));
        users.add(new PremiumUser("premium_user", "premium@example.com", "password123"));
    }

    // Inscription
    public User registerUser(Map<String, String> userData) {
        String username = userData.get("username");
        String email = userData.get("email");
        String password = userData.get("password");
        
        // Vérifier si l'email est déjà utilisé
        if (getUserByIdentifier(email).isPresent()) {
            throw new RuntimeException("Erreur : Cet email est déjà utilisé.");
        }
    
        // Vérifier si un rôle est spécifié, sinon mettre "RegularUser" par défaut
        String role = userData.getOrDefault("role", "regular");
    
        // Empêcher l'inscription en tant qu'Admin
        if ("admin".equalsIgnoreCase(role)) {
            throw new RuntimeException("Erreur : Il est impossible de s'inscrire en tant qu'Administrateur.");
        }
    
        // Créer l'utilisateur (Regular ou Premium uniquement)
        User newUser = "premium".equalsIgnoreCase(role)
                ? new PremiumUser(username, email, password)
                : new RegularUser(username, email, password); // Par défaut RegularUser
    
        users.add(newUser);
        return newUser;
    }

    // Récupérer tous les utilisateurs
    public List<User> getAllUsers() {
        return users;
    }

    // Récupérer un utilisateur par son identifiant
    public Optional<User> getUserByIdentifier(String identifier) {
        return users.stream()
                .filter(user -> user.getUsername().equals(identifier) || user.getEmail().equals(identifier))
                .findFirst();
    }

    // Login
    public String loginUser(Map<String, String> credentials, HttpSession session) {
        String identifier = credentials.get("identifier");
        String password = credentials.get("password");

        Optional<User> userOptional = getUserByIdentifier(identifier);

        if (userOptional.isPresent() && userOptional.get().login(identifier, password)) {
            session.setAttribute("loggedInUser", userOptional.get().getUsername());
            session.setAttribute("userRole", userOptional.get().getRole());
            return "Login successful! Role: " + userOptional.get().getRole();
        }
        return "Invalid username/email or password.";
    }

    public List<String> viewOrderHistory(String username) {
        Optional<User> userOptional = getUserByIdentifier(username);
    
        return userOptional.map(user ->
            user.getOrderHistory()
                .stream()
                .map(Order::getOrderSummary)
                .toList()
        ).orElseGet(ArrayList::new);
    }
}
