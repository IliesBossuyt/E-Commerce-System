package com.ecommerce.POO_Project_groupe.services;

import com.ecommerce.POO_Project_groupe.models.Order;
import com.ecommerce.POO_Project_groupe.models.PaymentMethod;
import com.ecommerce.POO_Project_groupe.models.PremiumUser;
import com.ecommerce.POO_Project_groupe.models.Product;
import com.ecommerce.POO_Project_groupe.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {
    private final List<Order> orders = new ArrayList<>();

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    public String placeOrder(String username, PaymentMethod paymentMethod) {
        User user = userService.getUserByIdentifier(username).orElse(null);
        if (user == null) {
            return "Utilisateur non trouvé.";
        }
    
        // Récupérer le panier sous forme de List<Map<String, Object>>
        List<Map<String, Object>> cartItemsList = cartService.viewCart(username);
        if (cartItemsList == null || cartItemsList.isEmpty()) {
            return "Le panier est vide, impossible de passer la commande.";
        }
    
        // Convertir List<Map<String, Object>> en Map<Product, Integer>
        Map<Product, Integer> cartItems = new HashMap<>();
        for (Map<String, Object> item : cartItemsList) {
            int productID = (int) item.get("productID");
            int quantity = (int) item.get("quantity");
    
            Optional<Product> productOptional = productService.getProductDetails(productID);
            if (productOptional.isEmpty()) {
                return "Produit introuvable pour l'ID: " + productID;
            }
    
            Product product = productOptional.get();
            cartItems.put(product, quantity);
        }
    
        // Calcul du total et application de la réduction
        double totalAmount = cartService.calculateTotal(username);
        double discountedTotal = user.applyDiscount(totalAmount);
    
        // Création de la commande
        Order order = new Order(user, cartItems, discountedTotal, paymentMethod);
        orders.add(order);
    
        // Validation de la commande (Stock & Paiement)
        try {
            boolean success = order.placeOrder(productService);
            if (!success) {
                orders.remove(order);
                return "Erreur lors de la validation de la commande.";
            }
        } catch (RuntimeException e) {
            orders.remove(order);
            return e.getMessage();
        }
    
        // Ajouter la commande à l'historique de l'utilisateur
        user.addOrderToHistory(order);
    
        // Nettoyage du panier après commande
        cartService.clearCart(username);
    
        String responseMessage = "Commande passée avec succès, ID: " + order.getOrderID();
        if (user instanceof PremiumUser) {
            responseMessage += ", Montant avant réduction: " + totalAmount + " €, Montant après réduction: " + discountedTotal + " €";
        }
        return responseMessage;
    }
    

    public String updateOrderStatus(String orderID, String newStatus) {
        Optional<Order> orderOptional = orders.stream()
                .filter(order -> order.getOrderID().equals(orderID))
                .findFirst();
    
        if (orderOptional.isPresent()) {
            orderOptional.get().updateStatus(newStatus);
            return "Statut de la commande mis à jour avec succès.";
        } else {
            return "Commande non trouvée.";
        }
    }
    

    public List<Order> getAllOrders() {
        return orders;
    }

    public Optional<Order> getOrderById(String orderID) {
        return orders.stream()
                .filter(order -> order.getOrderID().equals(orderID))
                .findFirst();
    }

    public List<Order> getOrdersByUser(String username) {
        return orders.stream()
                     .filter(order -> order.getUser().getUsername().equals(username))
                     .toList();
    }
}
