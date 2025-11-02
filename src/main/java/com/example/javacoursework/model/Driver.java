package com.example.javacoursework.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Driver extends BasicUser {
    private String driverLicence;
    private LocalDate bDate;
    @Enumerated(EnumType.STRING)
    private VechicleType vechicleType;

    public Driver(String username, String password, String name, String surname, String phoneNumber, LocalDateTime dateCreated, String address, String driverLicence, LocalDate bDate, VechicleType vechicleType) {
        super(username, password, name, surname, phoneNumber, dateCreated, address);
        this.driverLicence = driverLicence;
        this.bDate = bDate;
        this.vechicleType = vechicleType;
    }

    public Driver(String username, String password, String name, String surname, String phoneNumber, String address, String driverLicence, LocalDate bDate, VechicleType vechicleType) {
        super(username, password, name, surname, phoneNumber, address);
        this.driverLicence = driverLicence;
        this.bDate = bDate;
        this.vechicleType = vechicleType;
    }
}
