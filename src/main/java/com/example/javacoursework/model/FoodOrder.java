package com.example.javacoursework.model;

import java.util.ArrayList;
import java.util.List;

public class FoodOrder {
    private int id;
    private List<FoodItem> foodItems = new ArrayList<>();
    private double price;
    private List<Chat> chat = new ArrayList<>();
}
