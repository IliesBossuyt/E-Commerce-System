package com.ecommerce.POO_Project_groupe.models;
import java.util.UUID;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    private String orderID;
    private User user;
    private Map<Product, Integer> items;
    private String status;
    private double totalAmount;
    private PaymentMethod paymentMethod;

    public Order(User user, Map<Product, Integer> items, double totalAmount, PaymentMethod paymentMethod) {
        this.orderID = UUID.randomUUID().toString();
        this.user = user;
        this.items = items;
        this.status = "Pending";
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

     // Confirms an order
     public void placeOrder() {
        if (items.isEmpty()) {
            throw new RuntimeException("Impossible de passer une commande vide.");
        }

        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            if (product.getStockQuantity() >= quantity) {
                product.updateStock(quantity);
            } else {
                System.out.println("Stock insuffisant pour : " + product.getProductName());
                return;
            }
        }

        // Vérifier si le paiement est validé
        boolean paymentSuccess = paymentMethod.processPayment(totalAmount);
        if (!paymentSuccess) {
            System.out.println("Paiement échoué. Commande annulée.");
            return;
        }

        this.status = "Confirmed";
        System.out.println("Commande " + orderID + " confirmée avec succès. Paiement reçu.");
    }

    // Updates the order status
    public void updateStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("Order " + orderID + " status updated to: " + status);
    }
}