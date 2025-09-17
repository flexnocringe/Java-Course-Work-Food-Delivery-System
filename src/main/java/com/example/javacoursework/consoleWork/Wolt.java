package com.example.javacoursework.consoleWork;

import com.example.javacoursework.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Wolt {
    private List<User> allSystemUsers;
    private List<User> allOrders;
    public Wolt(){
        this.allSystemUsers = new ArrayList<>();
        this.allOrders = new ArrayList<>();
    }
}
