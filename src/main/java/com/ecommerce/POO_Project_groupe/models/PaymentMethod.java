package com.ecommerce.POO_Project_groupe.models;

public abstract class PaymentMethod {
    protected String accountDetails;

    public PaymentMethod(String accountDetails) {
        this.accountDetails = accountDetails;
    }

    // Méthode abstraite pour traiter le paiement (sera définie dans les sous-classes)
    public abstract boolean processPayment(double amount);

    public String getAccountDetails() {
        return accountDetails;
    }
}
