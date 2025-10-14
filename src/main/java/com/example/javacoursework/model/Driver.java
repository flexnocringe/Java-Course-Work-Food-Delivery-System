package com.example.javacoursework.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Driver extends BasicUser {
    private String driverLicence;
    private LocalDate bDate;
    private VechicleType vechicleType;


    public Driver(String username, String password, String name, String surname, String phoneNumber, String address, String driverLicence, LocalDate bDate, VechicleType vechicleType) {
        super(username, password, name, surname, phoneNumber, address);
        this.driverLicence = driverLicence;
        this.bDate = bDate;
        this.vechicleType = vechicleType;
    }
}
