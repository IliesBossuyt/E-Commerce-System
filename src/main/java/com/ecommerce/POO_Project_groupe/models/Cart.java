package com.ecommerce.POO_Project_groupe.models;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class Cart {
    private User user;
    private Map<Product, Integer> items;

    public Cart(User user) {
        this.user = user;
        this.items = new HashMap<>();
    }

    // Adds a product to the cart
    public void addProduct(Product product, int quantity) {
        if (product.getStockQuantity() >= quantity) {
            items.put(product, items.getOrDefault(product, 0) + quantity);
            System.out.println(quantity + " " + product.getProductName() + "(s) added to the cart.");
        } else {
            System.out.println("Not enough stock available for " + product.getProductName());
        }
    }

    // Removes a product from the cart
    public void removeProduct(Product product) {
        if (items.containsKey(product)) {
            System.out.println(product.getProductName() + " removed from the cart.");
        } else {
            System.out.println("Product not found in the cart.");
        }
    }

    // Calculates the total price of all items in the cart
    public double calculateTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    // Displays cart details
    public void displayCart() {
        System.out.println("Cart for " + user.getUsername() + ":");
        if (items.isEmpty()) {
            System.out.println("The cart is empty.");
        } else {
            for (Map.Entry<Product, Integer> entry : items.entrySet()) {
                System.out.println(entry.getKey().getProductName() + " - Quantity: " + entry.getValue());
            }
            System.out.println("Total Price: $" + calculateTotal());
        }
    }
}
