package com.example.javacoursework.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant extends BasicUser{
    private List<Cuisine> menu;

    public Restaurant(String login, String password, String name, String surname, String phoneNumber, String address, List<FoodOrder> myOrders, List<Review> myReviews, List<Review> feedback, List<Cuisine> menu) {
        super(login, password, name, surname, phoneNumber, address, myOrders, myReviews, feedback);
        this.menu = menu;
    }

    public Restaurant(int id, String login, String password, String name, String surname, String phoneNumber, LocalDateTime dateCreated, LocalDateTime dateUpdated, boolean isAdmin, String address, List<FoodOrder> myOrders, List<Review> myReviews, List<Review> feedback, List<Cuisine> menu) {
        super(id, login, password, name, surname, phoneNumber, dateCreated, dateUpdated, isAdmin, address, myOrders, myReviews, feedback);
        this.menu = menu;
    }
}
