package com.ecommerce.POO_Project_groupe.services;

import com.ecommerce.POO_Project_groupe.models.Order;
import com.ecommerce.POO_Project_groupe.models.PaymentMethod;
import com.ecommerce.POO_Project_groupe.models.PremiumUser;
import com.ecommerce.POO_Project_groupe.models.Product;
import com.ecommerce.POO_Project_groupe.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
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

    public String placeOrder(String username, PaymentMethod paymentMethod) {
        User user = userService.getUserByIdentifier(username).orElse(null);
        if (user == null) {
            return "Utilisateur non trouvé.";
        }

        // Récupérer le panier de l'utilisateur
        Map<Product, Integer> cartItems = cartService.viewCart(username);
        if (cartItems == null || cartItems.isEmpty()) {
            return "Le panier est vide, impossible de passer la commande.";
        }

        // Vérifier si tous les produits sont en stock
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            if (product.getStockQuantity() < quantity) {
                return "Stock insuffisant pour " + product.getProductName();
            }
        }

        // Calculer le montant total de la commande
        double totalAmount = cartService.calculateTotal(username);

        // Appliquer une réduction selon le type d'utilisateur (Polymorphisme)
        double discountedTotal = user.applyDiscount(totalAmount);

        // Vérifier si le paiement est validé
        boolean paymentSuccess = paymentMethod.processPayment(discountedTotal);
        if (!paymentSuccess) {
            return "Paiement échoué. Commande annulée.";
        }

        // Créer la commande après validation du paiement
        Order order = new Order(user, cartItems, discountedTotal, paymentMethod);
        order.placeOrder();
        orders.add(order);

        // Mettre à jour le stock des produits commandés
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            entry.getKey().updateStock(entry.getValue());
        }

        // Vider le panier après la commande
        cartService.clearCart(username);

        // Construire le message de retour
        String responseMessage = "Commande passée avec succès, ID: " + order.getOrderID();

        // Afficher la réduction seulement si l'utilisateur est Premium
        if (user instanceof PremiumUser) {
            responseMessage += ", Montant avant réduction: " + totalAmount + " €, Montant après réduction: " + discountedTotal + " €";
        }

        return responseMessage;
    }


    public String updateOrderStatus(String orderID, String newStatus) {
        Optional<Order> orderOptional = orders.stream()
                .filter(order -> order.getOrderID().equals(orderID))
                .findFirst();
    
        if (orderOptional.isEmpty()) {
            throw new RuntimeException("Commande introuvable avec l'ID " + orderID);
        }
    
        orderOptional.get().updateStatus(newStatus);
        return "Statut de la commande mis à jour avec succès.";
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
