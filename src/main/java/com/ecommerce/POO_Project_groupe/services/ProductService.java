package com.ecommerce.POO_Project_groupe.services;

import com.ecommerce.POO_Project_groupe.models.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final List<Product> products = new ArrayList<>();

    @PostConstruct
    public void initProducts() {
        products.add(new Product("Laptop", 101, 999.99, 10));
        products.add(new Product("Smartphone", 102, 599.99, 5));
    }

    public Product addProduct(Product product) {
        boolean exists = products.stream().anyMatch(p -> p.getProductID() == product.getProductID());
        if (exists) {
            throw new IllegalArgumentException("Product ID existe déja.");
        }
        products.add(product);
        return product;
    }
    

    public List<Product> getAllProducts() {
        return products;
    }

    public Optional<Product> getProductDetails(int productID) {
        return products.stream()
                .filter(product -> product.getProductID() == productID)
                .findFirst();
    }

    public String updateStock(int productID, int quantity) {
        Optional<Product> productOptional = products.stream()
                .filter(product -> product.getProductID() == productID)
                .findFirst();

        if (productOptional.isPresent()) {
            productOptional.get().updateStock(quantity);
            return "Stock mis à jour avec succès.";
        } else {
            return "Produit non trouvé.";
        }
    }

    public boolean removeProduct(int productID) {
        return products.removeIf(product -> product.getProductID() == productID);
    }    

    public Optional<Product> getProductById(int productID) {
        return products.stream()
                .filter(product -> product.getProductID() == productID)
                .findFirst();
    }    
}
