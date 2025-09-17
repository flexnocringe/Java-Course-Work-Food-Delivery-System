package com.example.javacoursework.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Driver extends BasicUser {
    private String licence;
    private LocalDate bDate;
    private VechicleType vechicleType;

    public Driver(String login, String password, String name, String surname, String phoneNumber, String address, String licence, LocalDate bDate, VechicleType vehicleType) {
        super(login, password, name, surname, phoneNumber, address);
        this.licence = licence;
        this.bDate = bDate;
        this.vechicleType = vehicleType;
    }
}
