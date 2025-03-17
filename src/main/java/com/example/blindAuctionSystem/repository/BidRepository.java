package com.example.blindAuctionSystem.repository;

import com.example.blindAuctionSystem.model.Auction;
import com.example.blindAuctionSystem.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByAuction(Auction auction);
}
