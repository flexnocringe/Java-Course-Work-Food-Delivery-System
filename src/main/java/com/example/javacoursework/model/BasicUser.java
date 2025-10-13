package com.example.javacoursework.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BasicUser extends User {
    protected String address;
    protected List<FoodOrder> myOrders;
    protected List<Review> myReviews;
    protected List<Review> feedback;

    public BasicUser(String login, String password, String name, String surname, String phoneNumber, String address, List<FoodOrder> myOrders, List<Review> myReviews, List<Review> feedback) {
        super(login, password, name, surname, phoneNumber);
        this.address = address;
        this.myOrders = myOrders;
        this.myReviews = myReviews;
        this.feedback = feedback;
    }

    public BasicUser(int id, String login, String password, String name, String surname, String phoneNumber, LocalDateTime dateCreated, LocalDateTime dateUpdated, boolean isAdmin, String address, List<FoodOrder> myOrders, List<Review> myReviews, List<Review> feedback) {
        super(id, login, password, name, surname, phoneNumber, dateCreated, dateUpdated, isAdmin);
        this.address = address;
        this.myOrders = myOrders;
        this.myReviews = myReviews;
        this.feedback = feedback;
    }

    public BasicUser(String login, String password, String name, String surname, String phoneNumber, String address) {
        super(login, password, name, surname, phoneNumber);
        this.address = address;
    }
}
