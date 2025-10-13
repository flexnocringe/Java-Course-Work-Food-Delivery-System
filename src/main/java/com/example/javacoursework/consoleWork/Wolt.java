package com.example.javacoursework.consoleWork;

import com.example.javacoursework.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Wolt implements Serializable {
    private List<User> allSystemUsers;
    private List<User> allOrders;
    public Wolt(){
        this.allSystemUsers = new ArrayList<>();
        this.allOrders = new ArrayList<>();
    }

    public List<User> getAllSystemUsers() {
        return allSystemUsers;
    }

    public List<User> getAllOrders() {
        return allOrders;
    }

    public void setAllSystemUsers(List<User> allSystemUsers) {
        this.allSystemUsers = allSystemUsers;
    }

    public void setAllOrders(List<User> allOrders) {
        this.allOrders = allOrders;
    }
}
