package service;

import model.Auction;
import model.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AuctionRepository;
import repository.BidRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidRepository bidRepository;

    public Auction createAuction(Auction auction) {
        return auctionRepository.save(auction);
    }

    public String placeBid(Long auctionId, double bidAmount, String bidder) {
        Optional<Auction> auctionOpt = auctionRepository.findById(auctionId);
        if (auctionOpt.isEmpty()) {
            return "Auction not found";
        }

        Auction auction = auctionOpt.get();
        if (bidAmount > auction.getStartingPrice()) {
            Bid bid = new Bid();
            bid.setAmount(bidAmount);
            bid.setBidder(bidder);
            bid.setBidPlacedAt(LocalDateTime.now());
            bid.setAuction(auction);
            bidRepository.save(bid);
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


