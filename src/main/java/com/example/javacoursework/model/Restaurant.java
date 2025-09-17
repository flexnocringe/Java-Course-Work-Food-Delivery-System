package com.example.javacoursework.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Restaurant extends BasicUser{
    private List<Cuisine> menu;

    public Restaurant(String username, String password, String name, String surname, LocalDate dateCreated, LocalDate dateModified, boolean isAdmin, String address, List<Review> myReview, List<Review> feedback, List<FoodOrder> myOrders, List<Cuisine> menu) {
        super(username, password, name, surname, dateCreated, dateModified, isAdmin, address, myReview, feedback, myOrders);
        this.menu = new ArrayList<>();
    }
}
