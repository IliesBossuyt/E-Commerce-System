package com.ecommerce.POO_Project_groupe.models;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import lombok.Data;

@Data
public class User {
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

    // Hashes le password avec SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // Register (a modifier)
    public static User register() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        return new User(username, email, password);
    }

    // Login avec email ou username
    public boolean login(String identifier, String password) {
        String hashedInputPassword = hashPassword(password);
        return (this.email.equals(identifier) || this.username.equals(identifier)) && this.password.equals(hashedInputPassword);
    }

    // Afficher l'historique des commandes
    public List<String> viewOrderHistory() {
        return orderHistory;
    }

    // Ajouter une commande
    public void addOrder(String order) {
        orderHistory.add(order);
    }
    
}