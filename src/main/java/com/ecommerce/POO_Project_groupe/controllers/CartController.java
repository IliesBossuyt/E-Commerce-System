package com.ecommerce.POO_Project_groupe.controllers;

import com.ecommerce.POO_Project_groupe.services.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Ajouter un produit au panier (avec JSON raw dans le body)
    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody Map<String, Object> requestData, HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            return ResponseEntity.status(401).body("Aucun utilisateur connecté.");
        }
    
        int productId = (int) requestData.get("productId");
        int quantity = (int) requestData.get("quantity");
    
        try {
            return ResponseEntity.ok(cartService.addProduct(username, productId, quantity));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Supprimer un produit du panier
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeProduct(@RequestBody Map<String, Object> requestData, HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            return ResponseEntity.status(401).body("Aucun utilisateur connecté.");
        }
    
        int productId = (int) requestData.get("productId");
    
        try {
            return ResponseEntity.ok(cartService.removeProduct(username, productId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Calculer le total du panier
    @GetMapping("/total")
    public ResponseEntity<Double> calculateTotal(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            return ResponseEntity.status(401).body(0.0);
        }
        return ResponseEntity.ok(cartService.calculateTotal(username));
    }

    // Récupérer le contenu du panier
    @GetMapping("/view")
    public ResponseEntity<?> viewCart(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            return ResponseEntity.status(401).body("Aucun utilisateur connecté.");
        }
        return ResponseEntity.ok(cartService.viewCart(username));
    }

    // Vider le panier
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            return ResponseEntity.status(401).body("Aucun utilisateur connecté.");
        }
        return ResponseEntity.ok(cartService.clearCart(username));
    }
}
