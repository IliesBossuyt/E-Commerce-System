package com.ecommerce.POO_Project_groupe.models;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart {
    private User user;
    private Map<Product, Integer> items;

    public Cart(User user) {
        this.user = user;
        this.items = new HashMap<>();
    }

    // Ajouter un produit au panier
    public void addProduct(Product product, int quantity) {
        if (product.getStockQuantity() >= quantity) {
            items.put(product, items.getOrDefault(product, 0) + quantity);
        } else {
            throw new RuntimeException("Stock insuffisant pour " + product.getProductName());
        }
    }
    
    // Suppriemr un produit du panier
    public void removeProduct(Product product) {
        if (items.containsKey(product)) {
            items.remove(product);
        } else {
            throw new RuntimeException("Produit non trouvé dans le panier.");
        }
    }
    
    // Calculer le total du panier
    public double calculateTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }
}
