package com.example.javacoursework.model;

import jakarta.persistence.*;
import lombok.*;

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
    @Transient
    private List<Review> messages = new ArrayList<>();
    private String chatText;
    @OneToOne(mappedBy ="chat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private FoodOrder foodOrder;
}
