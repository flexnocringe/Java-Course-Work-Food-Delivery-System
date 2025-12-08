package com.example.javacoursework.model;

import lombok.Getter;
import org.springframework.security.crypto.keygen.KeyGenerators;

public class Salt {
    private static String salt = "8cdee2119b35c31f";
    public static String getSalt() {
        return salt;
    }
}
