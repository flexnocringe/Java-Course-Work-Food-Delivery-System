package com.example.javacoursework.fxcontrollers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.Setter;

public class UserTableParameters {
    private SimpleIntegerProperty id = new  SimpleIntegerProperty();
    private SimpleStringProperty name = new  SimpleStringProperty();
    private SimpleStringProperty surname = new  SimpleStringProperty();
    private SimpleStringProperty username = new  SimpleStringProperty();
    private SimpleStringProperty password = new  SimpleStringProperty();
    private SimpleStringProperty address = new  SimpleStringProperty();
    private SimpleStringProperty phoneNumber = new  SimpleStringProperty();
    private SimpleStringProperty license = new  SimpleStringProperty();
    private SimpleStringProperty userType = new  SimpleStringProperty();

    public void setId(int id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public void setLicense(String license) {
        this.license.set(license);
    }

    public void setUserType(String userType) {
        this.userType.set(userType);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public String getLicense() {
        return license.get();
    }

    public SimpleStringProperty licenseProperty() {
        return license;
    }

    public String getUserType() {
        return userType.get();
    }

    public SimpleStringProperty userTypeProperty() {
        return userType;
    }
}
