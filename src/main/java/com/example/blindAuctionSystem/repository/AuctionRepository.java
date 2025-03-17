package com.example.blindAuctionSystem.repository;

import com.example.blindAuctionSystem.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
}
