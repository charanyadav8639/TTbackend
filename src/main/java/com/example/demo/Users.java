package com.example.demo;
import jakarta.persistence.*;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String email;
    private String password;
     public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

