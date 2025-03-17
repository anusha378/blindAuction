package com.example.blindAuctionSystem.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private String userToken;
    private LocalDateTime bidPlacedAt;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;


    // getters and setters


    public Bid(double amount, String userToken, LocalDateTime bidPlacedAt, Auction auction) {
        this.amount = amount;
        this.userToken = userToken;
        this.bidPlacedAt = bidPlacedAt;
        this.auction = auction;
    }

    public Bid() {
    }
}

