package com.ecommerce.POO_Project_groupe.models;
import java.util.UUID;
import java.util.Map;
import lombok.Data;

@Data
public class Order {
    private String orderID;
    private User user;
    private Map<Product, Integer> items;
    private String status;

    public Order(User user, Map<Product, Integer> items) {
        this.orderID = UUID.randomUUID().toString();
        this.user = user;
        this.items = items;
        this.status = "Pending";
    }

     // Confirms an order and updates stock
     public void placeOrder() {
        if (items.isEmpty()) {
            System.out.println("Cannot place an empty order.");
            return;
        }
        
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            if (product.getStockQuantity() >= quantity) {
                product.updateStock(quantity);
            } else {
                System.out.println("Insufficient stock for: " + product.getProductName());
                return;
            }
        }
        
        this.status = "Confirmed";
        System.out.println("Order " + orderID + " placed successfully.");
    }

    // Updates the order status
    public void updateStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("Order " + orderID + " status updated to: " + status);
    }
}
