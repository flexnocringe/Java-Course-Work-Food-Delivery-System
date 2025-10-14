package com.example.javacoursework.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FoodItem {
    private int id;
    private double price;
    private List<Ingredients> ingredients = new ArrayList<>();
    private List<Allergens> allergens = new ArrayList<>();
    private List<PortionSize> portionSizes = new ArrayList<>();
}
