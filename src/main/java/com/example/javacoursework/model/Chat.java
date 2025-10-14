package com.example.javacoursework.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Chat {
    private int id;
    private List<Review> messages = new ArrayList<>();
}
