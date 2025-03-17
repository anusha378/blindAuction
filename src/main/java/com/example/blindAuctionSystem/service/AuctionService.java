package com.example.blindAuctionSystem.service;

import com.example.blindAuctionSystem.model.Auction;
import com.example.blindAuctionSystem.model.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.blindAuctionSystem.repository.AuctionRepository;
import com.example.blindAuctionSystem.repository.BidRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    TokenValidationService tokenValidationService;

    public Auction createAuction(Auction auction) {
        return auctionRepository.save(auction);
    }

    public String placeBid(Long auctionId, double bidAmount, String token) {
        Optional<Auction> auctionOpt = auctionRepository.findById(auctionId);
        if (auctionOpt.isEmpty()) {
            return "Auction not found";
        }

        if (tokenValidationService.validateToken(token).isEmpty()) {
            throw new IllegalArgumentException("Invalid token");
        }

        Auction auction = auctionOpt.get();
        if (bidAmount > auction.getStartingPrice()) {
            bidRepository.save(new Bid(bidAmount, token, LocalDateTime.now(), auction));
            return "Bid placed successfully!";
        } else {
            return "Bid is lower than the starting price!";
        }
    }

    public List<Bid> getBidsForAuction(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow();
        return bidRepository.findByAuction(auction);
    }

    public Bid getSuccessfulBid(Long auctionId){
        List<Bid>  bidsForAuction = getBidsForAuction(auctionId);
        if (bidsForAuction.isEmpty()) {
            return null;
        }
        Optional<Bid> highestBid = bidsForAuction.stream()
                .sorted((bid1, bid2) -> {
                    int amountComparison = Double.compare(bid2.getAmount(), bid1.getAmount());
                    if (amountComparison != 0) {
                        return amountComparison;
                    }
                    // If amounts are the same, compare by timestamp
                    return bid1.getBidPlacedAt().compareTo(bid2.getBidPlacedAt());
                })
                .findFirst();

        return highestBid.orElse(null);
    }
}


