package com.example.blindAuctionSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private Long userId;  // FK to User

    // Getters and Setters

    public Token(String token, Long userId) {}
    public Long getId() {return id;}
    public String getToken() {return token;}
    public Long getUserId() {return userId;}
    public void setId(Long id) {this.id = id;}
    public void setToken(String token) {this.token = token;}
    public void setUserId(Long userId) {this.userId = userId;}

}
