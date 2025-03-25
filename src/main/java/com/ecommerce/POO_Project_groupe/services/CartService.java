package com.ecommerce.POO_Project_groupe.services;

import com.ecommerce.POO_Project_groupe.models.Cart;
import com.ecommerce.POO_Project_groupe.models.Product;
import com.ecommerce.POO_Project_groupe.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {
    private final Map<String, Cart> carts = new HashMap<>();

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    public String addProduct(String username, int productId, int quantity) {
        User user = userService.getUserByIdentifier(username).orElse(null);
        if (user == null) {
            return "Utilisateur non trouvé.";
        }

        Product product = productService.getProductDetails(productId).orElse(null);
        if (product == null) {
            return "Produit non trouvé.";
        }

        carts.putIfAbsent(username, new Cart(user));
        carts.get(username).addProduct(product, quantity);
        return quantity + " " + product.getProductName() + " ajouté(s) au panier.";
    }

    public String removeProduct(String username, int productId) {
        Cart cart = carts.get(username);
        if (cart == null) {
            throw new RuntimeException("Panier non trouvé.");
        }
    
        Product product = productService.getProductDetails(productId).orElseThrow(() -> 
            new RuntimeException("Le produit avec l'ID " + productId + " n'existe pas.")
        );
    
        if (!cart.getItems().containsKey(product)) {
            throw new RuntimeException("Ce produit n'est pas dans le panier.");
        }
    
        cart.removeProduct(product);
        return product.getProductName() + " supprimé du panier.";
    }
    

    public double calculateTotal(String username) {
        Cart cart = carts.get(username);
        if (cart == null) {
            return 0.0;
        }
        return cart.calculateTotal();
    }

    public List<Map<String, Object>> viewCart(String username) {
        Cart cart = carts.get(username);
        if (cart == null) return new ArrayList<>();
    
        List<Map<String, Object>> cartItems = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Map<String, Object> item = new HashMap<>();
            item.put("productID", entry.getKey().getProductID());
            item.put("productName", entry.getKey().getProductName());
            item.put("price", entry.getKey().getPrice());
            item.put("quantity", entry.getValue());
            cartItems.add(item);
        }
        return cartItems;
    }
      

    public String clearCart(String username) {
        Cart cart = carts.remove(username);
        return (cart != null) ? "Panier vidé avec succès." : "Aucun panier trouvé pour cet utilisateur.";
    }
}