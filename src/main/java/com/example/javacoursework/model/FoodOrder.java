package com.example.javacoursework.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FoodOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Double price;
    @ManyToMany
    private List<FoodItem> foodItems = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Chat chat;
    @ManyToOne
    private BasicUser buyer;
    @ManyToOne
    private Restaurant restaurant;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    public FoodOrder(String name, Double price, List<FoodItem> foodItems, BasicUser buyer, Restaurant restaurant, OrderStatus orderStatus, LocalDateTime dateCreated) {
        this.name = name;
        this.price = price;
        this.foodItems = foodItems;
        this.buyer = buyer;
        this.restaurant = restaurant;
        this.orderStatus = orderStatus;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateCreated;
    }
}
