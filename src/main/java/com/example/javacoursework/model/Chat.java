package com.example.javacoursework.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String chatText;
    private LocalDate dateCreated;
    @OneToOne(mappedBy ="chat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private FoodOrder foodOrder;
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> messages = new ArrayList<>();

    public Chat(String name, LocalDate dateCreated, FoodOrder foodOrder, List<Review> messages) {
        this.name = name;
        this.dateCreated = dateCreated;
        this.foodOrder = foodOrder;
        this.messages = messages;
    }
}
