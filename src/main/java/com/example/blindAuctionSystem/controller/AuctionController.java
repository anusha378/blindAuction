package com.example.blindAuctionSystem.controller;

import com.example.blindAuctionSystem.model.Auction;
import com.example.blindAuctionSystem.model.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.blindAuctionSystem.service.AuctionService;

import java.util.List;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @PostMapping("/create")
    public ResponseEntity<Auction> createAuction(@RequestBody Auction auction) {
        Auction createdAuction = auctionService.createAuction(auction);
        return ResponseEntity.ok(createdAuction);
    }

    @PostMapping("/{auctionId}/bid")
    public ResponseEntity<String> placeBid(@PathVariable Long auctionId, @RequestParam double bidAmount, @RequestParam String bidder) {
        String response = auctionService.placeBid(auctionId, bidAmount, bidder);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{auctionId}/bids")
    public ResponseEntity<List<Bid>> getBidsForAuction(@PathVariable Long auctionId) {
        List<Bid> bids = auctionService.getBidsForAuction(auctionId);
        return ResponseEntity.ok(bids);
    }

    @GetMapping("/{auctionId}/successfulBid")
    public ResponseEntity<Bid> getSuccessfulBid(@PathVariable Long auctionId) {
        Bid successfulBid = auctionService.getSuccessfulBid(auctionId);
        return ResponseEntity.ok(successfulBid);
    }
}

