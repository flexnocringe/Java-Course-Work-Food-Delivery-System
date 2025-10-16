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
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double price;
    @Transient
    private List<Ingredients> ingredients = new ArrayList<>();
    private List<Allergens> allergens = new ArrayList<>();
    private List<PortionSize> portionSizes = new ArrayList<>();
    @ManyToMany(mappedBy = "foodItems", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FoodOrder> orderList = new ArrayList<>();
}
