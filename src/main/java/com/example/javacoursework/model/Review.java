package com.example.javacoursework.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int rating;
    private String text;
    private LocalDateTime dateCreated;
    @ManyToOne
    private BasicUser reviewOwner;
    @ManyToOne
    private BasicUser feedbackUser;
    @ManyToOne
    private Chat chat;

    public Review(String text, LocalDateTime dateCreated, BasicUser reviewOwner) {
        this.text = text;
        this.dateCreated = dateCreated;
        this.reviewOwner = reviewOwner;
    }
}
