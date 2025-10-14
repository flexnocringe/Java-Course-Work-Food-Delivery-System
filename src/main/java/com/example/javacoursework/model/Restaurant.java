package com.example.javacoursework.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Restaurant extends BasicUser{
    protected List<FoodItem> dishes = new ArrayList<>();
    protected String workHours;
    protected double rating;

    public Restaurant(String username, String password, String name, String surname, String phoneNumber, String address, List<FoodItem> dishes, String workHours, double rating) {
        super(username, password, name, surname, phoneNumber, address);
        this.dishes = dishes;
        this.workHours = workHours;
        this.rating = rating;
    }
}
