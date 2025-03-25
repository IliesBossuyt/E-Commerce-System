package com.ecommerce.POO_Project_groupe.models;
import com.ecommerce.POO_Project_groupe.services.ProductService;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    private String orderID;
    @JsonIgnore
    private User user;
    private List<Map<String, Object>> items;
    private String status;
    private double totalAmount;
    private PaymentMethod paymentMethod;

    public Order(User user, Map<Product, Integer> cartItems, double totalAmount, PaymentMethod paymentMethod) {
        this.orderID = UUID.randomUUID().toString();
        this.user = user;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.status = "Pending";

        // Conversion des produits en format JSON lisible
        this.items = cartItems.entrySet().stream().map(entry -> {
            Map<String, Object> item = new HashMap<>();
            item.put("productID", entry.getKey().getProductID());
            item.put("productName", entry.getKey().getProductName());
            item.put("price", entry.getKey().getPrice());
            item.put("quantity", entry.getValue());
            return item;
        }).toList();
    }

    // Valider la commande après vérification du stock et paiement
    public boolean placeOrder(ProductService productService) {
        if (items.isEmpty()) {
            throw new RuntimeException("Impossible de passer une commande vide.");
        }

        // Vérifier si les produits sont en stock
        for (Map<String, Object> item : items) {
            int productID = (int) item.get("productID");
            int quantity = (int) item.get("quantity");

            Optional<Product> productOptional = productService.getProductDetails(productID);
            if (productOptional.isEmpty() || productOptional.get().getStockQuantity() < quantity) {
                throw new RuntimeException("Stock insuffisant pour : " + item.get("productName"));
            }
        }

        // Vérifier et traiter le paiement
        boolean paymentSuccess = paymentMethod.processPayment(totalAmount);
        if (!paymentSuccess) {
            throw new RuntimeException("Paiement échoué. Commande annulée.");
        }

        // Mettre à jour le stock après validation du paiement
        for (Map<String, Object> item : items) {
            int productID = (int) item.get("productID");
            int quantity = (int) item.get("quantity");

            Optional<Product> productOptional = productService.getProductDetails(productID);
            if (productOptional.isPresent()) {
                productOptional.get().updateStock(quantity);
            }
        }

        // Mise à jour du statut de la commande
        this.status = "Confirmed";
        return true;
    }

    // Mettre à jour le statut d'une commande
    public void updateStatus(String newStatus) {
        Set<String> validStatuses = Set.of("Pending", "Confirmed", "Processing", "Shipped", "Delivered");

        if (!validStatuses.contains(newStatus)) {
            throw new IllegalArgumentException("Statut de commande invalide.");
        }

        this.status = newStatus;
    }

    public String getOrderSummary() {
        return "Commande ID: " + orderID + ", Montant: " + totalAmount + "€, Statut: " + status;
    }
}
