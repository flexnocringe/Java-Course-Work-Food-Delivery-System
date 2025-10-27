package com.example.javacoursework.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @ManyToMany
    private List<FoodItem> foodItems = new ArrayList<>();
    private double price;
    @OneToOne
    private Chat chat;
    @ManyToOne
    private BasicUser buyer;
    @ManyToOne
    private Restaurant restaurant;
    @Enumerated
    private OrderStatus orderStatus;
}
