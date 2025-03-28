package com.ecommerce.POO_Project_groupe.models;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "productID")
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
    public String updateStock(int quantity) {
        if (quantity > 0 && stockQuantity >= quantity) {
            stockQuantity -= quantity;
            return quantity + " articles vendus. Nouveau stock : " + stockQuantity;
        } else {
            return "Stock insuffisant ou quantité invalide.";
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
