package com.ecommerce.POO_Project_groupe.controllers;

import com.ecommerce.POO_Project_groupe.models.Product;
import com.ecommerce.POO_Project_groupe.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductThymeleafController {

    @Autowired
    private ProductService productService;

    @GetMapping("/thymeleaf/products")
    public String showProducts(Model model) {
        // Récupérer la liste des produits depuis le service
        List<Product> productList = productService.getAllProducts();

        // Ajouter dans le model => accessible par ${products} dans la page product-list.html
        model.addAttribute("products", productList);

        // Retourne le nom du template (product-list.html) dans /templates
        return "product-list";
    }
}
