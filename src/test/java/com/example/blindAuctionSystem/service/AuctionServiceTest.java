package com.example.blindAuctionSystem.service;

import com.example.blindAuctionSystem.model.Auction;
import com.example.blindAuctionSystem.model.Bid;
import com.example.blindAuctionSystem.model.Token;
import com.example.blindAuctionSystem.model.User;
import com.example.blindAuctionSystem.repository.TokenRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.blindAuctionSystem.repository.AuctionRepository;
import com.example.blindAuctionSystem.repository.BidRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AuctionServiceTest {

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private BidRepository bidRepository;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private AuctionService auctionService;

    @Mock
    private TokenValidationService tokenValidationService;

    @Test
    public void testCreateAuction() {
        // Given
        Auction auction = new Auction();
        auction.setItemName("Test Auction");
        auction.setStartingPrice(100.0);

        // Mock the repository behavior
        when(auctionRepository.save(ArgumentMatchers.any())).thenReturn(auction);

        // When
        Auction createdAuction = auctionService.createAuction(auction);

        // Then
        assertNotNull(createdAuction);
        assertEquals("Test Auction", createdAuction.getItemName());
        assertEquals(100.0, createdAuction.getStartingPrice(), 0);
    }

    @Test
    public void testPlaceBid() {
        // Given
        Auction auction = new Auction();
        auction.setId(1L);

        Bid bid = new Bid();
        bid.setAuction(auction);
        bid.setBidPlacedAt(LocalDateTime.now());
        bid.setAmount(120.0);
        bid.setUserToken("Bidder1");

        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setId(5L);
        Token token = new Token();
        token.setUserId(user.getId());
        token.setToken("opaque-token-123");

        // Mock the repository behavior
        when(auctionRepository.findById(1L)).thenReturn(Optional.of(auction));
        when(bidRepository.save(ArgumentMatchers.any())).thenReturn(bid);
        when(tokenValidationService.validateToken(ArgumentMatchers.any())).thenReturn(Optional.of(user));
        when(tokenRepository.findByToken(ArgumentMatchers.any())).thenReturn(Optional.of(token));


        // When
        String response = auctionService.placeBid(1L, 120.0, "Bidder1");

        // Then
        assertEquals("Bid placed successfully!", response);
    }

    @Test
    public void testSuccessfulBid(){

        Auction auction = new Auction();
        auction.setId(1L);
        auction.setStartingPrice(100.0);

        // Create some test bids
        List<Bid> bids = new ArrayList<>();
        Bid bid1 = new Bid();
        bid1.setAuction(auction);
        bid1.setBidPlacedAt(LocalDateTime.of(2025, 3, 15, 10, 30, 0));
        bid1.setAmount(150.0);
        bid1.setUserToken("Bidder1");
        bids.add(bid1);

        Bid bid2 = new Bid();
        bid2.setAuction(auction);
        bid2.setBidPlacedAt(LocalDateTime.of(2025, 3, 15, 9, 0, 0));
        bid2.setAmount(200.0);
        bid2.setUserToken("Bidder2");
        bids.add(bid2);

        Bid bid3 = new Bid();
        bid3.setAuction(auction);
        bid3.setBidPlacedAt(LocalDateTime.of(2025, 3, 15, 12, 0, 0));
        bid3.setAmount(200.0);
        bid3.setUserToken("Bidder3");
        bids.add(bid3);

        when(auctionRepository.findById(1L)).thenReturn(Optional.of(auction));
        when(bidRepository.findByAuction(auction)).thenReturn(bids);

        Bid successfulBid = auctionService.getSuccessfulBid(auction.getId());
        assertNotNull(successfulBid);
        assertEquals("Bidder2", successfulBid.getUserToken());

    }

}

