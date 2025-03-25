package com.ecommerce.POO_Project_groupe.models;

public class AdminUser extends User {
    public AdminUser(String username, String email, String password) {
        super(username, email, password);
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}
