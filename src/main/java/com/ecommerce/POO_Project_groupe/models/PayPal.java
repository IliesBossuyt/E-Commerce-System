package com.ecommerce.POO_Project_groupe.models;

public class PayPal extends PaymentMethod {
    public PayPal(String email) {
        super(email);
    }

    @Override
    public boolean processPayment(double amount) {
        return true;
    }
}
