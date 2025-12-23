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
    private String name;
    private Double price;
    private boolean spicy = false;
    private boolean vegan = false;
    private String ingredients;
    @ElementCollection(targetClass = Allergens.class)
    @CollectionTable(
            name = "fooditem_allergens",
            joinColumns = @JoinColumn(name = "fooditem_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "allergens")
    private List<Allergens> allergens = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private PortionSize portionSize;
    @ManyToMany(mappedBy = "foodItems", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FoodOrder> orderList = new ArrayList<>();
    @ManyToOne
    private Restaurant restaurant;

    public FoodItem(String name, Double price, boolean spicy, boolean vegan, Restaurant restaurant, String ingredients, List<Allergens> allergens, PortionSize portionSize) {
        this.name = name;
        this.price = price;
        this.spicy = spicy;
        this.vegan = vegan;
        this.restaurant = restaurant;
        this.ingredients = ingredients;
        this.allergens = allergens;
        this.portionSize = portionSize;
    }

    @Override
    public String toString() {
        return "name: "+name+" | "+"price: "+price+" | portion size: "+portionSize+" | spicy?: "+spicy+" | vegan?: "+vegan;
    }
}
