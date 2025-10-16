package com.example.javacoursework.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BasicUser extends User {
    protected String address;
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<FoodOrder> myOrders;
    @OneToMany(mappedBy = "reviewOwner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<Review> myReviews;
    @OneToMany(mappedBy = "feedbackUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<Review> feedback;

    public BasicUser(String username, String password, String name, String surname, String phoneNumber) {
        super(username, password, name, surname, phoneNumber);
    }

    public BasicUser(String username, String password, String name, String surname, String phoneNumber, String address) {
        super(username, password, name, surname, phoneNumber);
        this.address = address;
    }

    public BasicUser(String username, String password, String name, String surname, String phoneNumber, List<FoodOrder> myOrders, List<Review> myReviews, List<Review> feedback) {
        super(username, password, name, surname, phoneNumber);
        this.myOrders = myOrders;
        this.myReviews = myReviews;
        this.feedback = feedback;
    }
}
