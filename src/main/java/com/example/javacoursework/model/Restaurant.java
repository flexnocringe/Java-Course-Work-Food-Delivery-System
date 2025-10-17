package com.example.javacoursework.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.criteria.Order;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Restaurant extends BasicUser{
    @OneToMany(mappedBy ="restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FoodOrder> orderList;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FoodItem> dishes = new ArrayList<>();
    private String workHours;
    private double rating;


    public Restaurant(String username, String password, String name, String surname, String phoneNumber, String address, List<FoodItem> dishes, String workHours, double rating) {
        super(username, password, name, surname, phoneNumber, address);
        this.dishes = dishes;
        this.workHours = workHours;
        this.rating = rating;
    }
}
