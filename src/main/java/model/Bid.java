package model;


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
    private String bidder;
    private LocalDateTime bidPlacedAt;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

//    protected Bid(double amount, String bidder, LocalDateTime bidPlacedAt, Auction auction) {
//        this.amount = amount;
//        this.bidder = bidder;
//        this.bidPlacedAt = bidPlacedAt;
//        this.auction = auction;
//    }

    // getters and setters

}

