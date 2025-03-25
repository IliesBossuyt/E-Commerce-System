package com.ecommerce.POO_Project_groupe.controllers;

import com.ecommerce.POO_Project_groupe.models.Product;
import com.ecommerce.POO_Project_groupe.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProduitsController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product-list")
    public String showProducts(Model model) {
        // Récupérer la liste de tous les produits
        List<Product> products = productService.getAllProducts();

        // Mettre cette liste dans le model pour la rendre disponible dans product-list.html
        model.addAttribute("products", products);

        // Optionnel : un titre de page
        model.addAttribute("pageTitle", "Nos produits disponibles");

        // Retourne le template "product-list.html"
        return "product-list";
    }
}
