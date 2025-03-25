package com.ecommerce.POO_Project_groupe.models;

public class PayPal extends PaymentMethod {
    public PayPal(String email) {
        super(email);
    }

    @Override
    public boolean processPayment(double amount) {
        // Simuler un paiement réussi via PayPal
        System.out.println("Paiement de " + amount + " € effectué via PayPal : " + accountDetails);
        return true;
    }
}
