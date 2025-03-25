package com.ecommerce.POO_Project_groupe.controllers;

import com.ecommerce.POO_Project_groupe.models.CreditCard;
import com.ecommerce.POO_Project_groupe.models.PayPal;
import com.ecommerce.POO_Project_groupe.models.PaymentMethod;
import com.ecommerce.POO_Project_groupe.services.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Passer une commande (valider le panier)
 @PostMapping("/place")
    public ResponseEntity<String> placeOrder(@RequestBody Map<String, String> paymentData, HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            return ResponseEntity.status(401).body("Aucun utilisateur connecté.");
        }

        // Récupérer la méthode de paiement depuis la requête JSON
        PaymentMethod paymentMethod;
        String paymentType = paymentData.get("paymentType");

        if ("creditCard".equalsIgnoreCase(paymentType)) {
            paymentMethod = new CreditCard(
                    paymentData.get("accountDetails"),
                    paymentData.get("cardNumber"),
                    paymentData.get("expirationDate"),
                    paymentData.get("cvv")
            );
        } else if ("paypal".equalsIgnoreCase(paymentType)) {
            paymentMethod = new PayPal(paymentData.get("email"));
        } else {
            return ResponseEntity.badRequest().body("Méthode de paiement invalide.");
        }

        // Passer la commande avec la méthode de paiement sélectionnée
        return ResponseEntity.ok(orderService.placeOrder(username, paymentMethod));
    }

    // Mettre à jour le statut d'une commande
    @PatchMapping("/{orderID}/status")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable String orderID,
            @RequestBody Map<String, String> requestBody,
            HttpSession session) {
    
        String username = (String) session.getAttribute("loggedInUser");
        String role = (String) session.getAttribute("userRole");
    
        if (username == null) {
            return ResponseEntity.status(401).body("Aucun utilisateur connecté.");
        }
    
        // Vérification si l'utilisateur est un Admin
        if (!"Admin".equals(role)) {
            return ResponseEntity.status(403).body("Accès refusé. Seuls les administrateurs peuvent modifier le statut d'une commande.");
        }
    
        // Vérifier si le JSON contient bien un "status"
        if (!requestBody.containsKey("Status")) {
            return ResponseEntity.status(400).body("Erreur : Le champ 'Status' est manquant.");
        }
    
        String newStatus = requestBody.get("Status");
    
        return ResponseEntity.ok(orderService.updateOrderStatus(orderID, newStatus));
    }     
    

    // Récupérer toutes les commandes
    @GetMapping
    public ResponseEntity<?> getAllOrders(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            return ResponseEntity.status(401).body("Aucun utilisateur connecté.");
        }
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // Récupérer une commande par son ID
    @GetMapping("/{orderID}")
    public ResponseEntity<?> getOrderById(@PathVariable String orderID, HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            return ResponseEntity.status(401).body("Aucun utilisateur connecté.");
        }
        return ResponseEntity.ok(orderService.getOrderById(orderID));
    }
}
