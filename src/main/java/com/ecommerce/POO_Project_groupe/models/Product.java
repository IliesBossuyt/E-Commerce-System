package com.ecommerce.POO_Project_groupe.models;
import lombok.Data;

@Data
public class Product {
    private String productName;
    private int productID;
    private double price;
    private int stockQuantity;

    public Product(String productName, int productID, double price, int stockQuantity) {
        this.productName = productName;
        this.productID = productID;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    
    // Mise a jour stock apres achat
    public void updateStock(int quantity) {
        if (quantity > 0 && stockQuantity >= quantity) {
            stockQuantity -= quantity;
            System.out.println(quantity + " items sold. New stock: " + stockQuantity);
        } else {
            System.out.println("Insufficient stock or invalid quantity.");
        }
    }

    // Afficher les details du produit
    public String getProductDetails() {
        return "Product ID: " + productID + "\n" +
                "Product Name: " + productName + "\n" +
                "Price: $" + price + "\n" +
                "Stock Quantity: " + stockQuantity;
    }
}
