package com.ecommerce.POO_Project_groupe.models;

public class RegularUser extends User {
    public RegularUser(String username, String email, String password) {
        super(username, email, password);
    }

    @Override
    public String getRole() {
        return "RegularUser";
    }
}
