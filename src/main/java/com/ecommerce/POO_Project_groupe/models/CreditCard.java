package com.ecommerce.POO_Project_groupe.models;

public class CreditCard extends PaymentMethod {
    
    public CreditCard(String accountDetails, String cardNumber, String expirationDate, String cvv) {
        super(accountDetails);
    }
    @Override
    public boolean processPayment(double amount) {
        return true;
    }
}
