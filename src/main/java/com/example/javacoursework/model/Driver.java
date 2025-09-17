package com.example.javacoursework.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Driver extends BasicUser {
    private String licence;
    private LocalDate bDate;
    private VechicleType vechicleType;

    public Driver(String login, String password, String name, String surname, String phoneNumber, String address, List<FoodOrder> myOrders, List<Review> myReviews, List<Review> feedback, String licence, LocalDate bDate, VechicleType vechicleType) {
        super(login, password, name, surname, phoneNumber, address, myOrders, myReviews, feedback);
        this.licence = licence;
        this.bDate = bDate;
        this.vechicleType = vechicleType;
    }

    public Driver(int id, String login, String password, String name, String surname, String phoneNumber, LocalDateTime dateCreated, LocalDateTime dateUpdated, boolean isAdmin, String address, List<FoodOrder> myOrders, List<Review> myReviews, List<Review> feedback, String licence, LocalDate bDate, VechicleType vechicleType) {
        super(id, login, password, name, surname, phoneNumber, dateCreated, dateUpdated, isAdmin, address, myOrders, myReviews, feedback);
        this.licence = licence;
        this.bDate = bDate;
        this.vechicleType = vechicleType;
    }

    public Driver(String login, String password, String name, String surname, String phoneNumber, String address, String licence, LocalDate bDate, VechicleType vechicleType) {
        super(login, password, name, surname, phoneNumber, address);
        this.licence = licence;
        this.bDate = bDate;
        this.vechicleType = vechicleType;
    }
}
