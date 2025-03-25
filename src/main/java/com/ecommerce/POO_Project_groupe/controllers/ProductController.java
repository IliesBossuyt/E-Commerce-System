package com.ecommerce.POO_Project_groupe.controllers;

import com.ecommerce.POO_Project_groupe.models.Product;
import com.ecommerce.POO_Project_groupe.services.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Ajouter un produit (requiert un utilisateur connecté)
    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product, HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if (role == null || !role.equals("Admin")) {
            throw new RuntimeException("Seuls les admins peuvent ajouter un produit.");
        }
        return productService.addProduct(product);
    }
    

    // Récupérer tous les produits
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Récupérer les détails d'un produit par son ID
    @GetMapping("/{productID}")
    public Optional<Product> getProductDetails(@PathVariable int productID) {
        return productService.getProductDetails(productID);
    }

    // Mettre à jour le stock d'un produit
    @PutMapping("/{productID}/updateStock")
    public ResponseEntity<String> updateStock(@PathVariable int productID, @RequestBody Map<String, Integer> requestData, HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if (role == null || !role.equals("Admin")) {
            throw new RuntimeException("Seuls les admins peuvent ajouter un produit.");
        }

        // Vérifier si "quantity" est présent dans la requête JSON
        if (!requestData.containsKey("quantity")) {
            return ResponseEntity.status(400).body("Erreur : Le champ 'quantity' est manquant dans le JSON.");
        }

        int quantity = requestData.get("quantity");
        return ResponseEntity.ok(productService.updateStock(productID, quantity));
    }

    @DeleteMapping("/{productID}")
    public String deleteProduct(@PathVariable int productID, HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if (role == null || !role.equals("Admin")) {
            throw new RuntimeException("Seuls les admins peuvent supprimer un produit.");
        }

        boolean removed = productService.removeProduct(productID);
        return removed ? "Product deleted successfully." : "Product not found.";
    }
}
