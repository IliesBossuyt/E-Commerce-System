package com.ecommerce.POO_Project_groupe.models;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class User {
    private String username;
    private String email;
    private String password;
    private List<String> orderHistory;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = hashPassword(password);
        this.orderHistory = new ArrayList<>();
    }

    // Hachage du mot de passe
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur lors du hachage du mot de passe", e);
        }
    }

    // Authentification de l'utilisateur
    public boolean login(String identifier, String password) {
        String hashedInputPassword = hashPassword(password);
        return (this.email.equals(identifier) || this.username.equals(identifier)) && this.password.equals(hashedInputPassword);
    }

    // Récupération de l'historique des commandes
    public List<String> viewOrderHistory() {
        return orderHistory;
    }

    // Ajouter une commande à l'historique
    public void addOrder(String orderId) {
        orderHistory.add(orderId);
    }

    // Méthode abstraite pour afficher le rôle de l'utilisateur
    public abstract String getRole();

    // Méthode par défaut pour les utilisateurs réguliers (pas de réduction)
    public double applyDiscount(double totalAmount) {
        return totalAmount; // Pas de remise pour les utilisateurs classiques
    }
}
