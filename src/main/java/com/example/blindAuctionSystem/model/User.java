package com.example.blindAuctionSystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // Getters and Setters

    public Long getId() {return id;}
    public String getName() {return name;}
    public String getEmail() {return email;}
    public void setId(Long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setEmail(String email) {this.email = email;}
}