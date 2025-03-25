package com.ecommerce.POO_Project_groupe.models;

public class CreditCard extends PaymentMethod {
    private String cardNumber;
    private String expirationDate;
    private String cvv;

    public CreditCard(String accountDetails, String cardNumber, String expirationDate, String cvv) {
        super(accountDetails);
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    @Override
    public boolean processPayment(double amount) {
        // Simuler un paiement réussi
        System.out.println("Paiement de " + amount + " € effectué par carte bancaire : " + cardNumber);
        return true;
    }
}
