package com.ecommerce.POO_Project_groupe.models;

public class PremiumUser extends User {

    public PremiumUser(String username, String email, String password) {
        super(username, email, password);
    }

    @Override
    public String getRole() {
        return "PremiumUser";
    }

    // Réduction de 10% pour les utilisateurs premium
    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount * 0.90; // Applique une réduction de 10%
    }
}
