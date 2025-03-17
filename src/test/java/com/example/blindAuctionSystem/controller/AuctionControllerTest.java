package com.example.blindAuctionSystem.controller;

import com.example.blindAuctionSystem.model.Auction;
import com.example.blindAuctionSystem.model.Bid;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import com.example.blindAuctionSystem.service.AuctionService;

import java.time.LocalDateTime;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class AuctionControllerTest {

    @InjectMocks
    private AuctionController auctionController; // Class under test

    @Mock
    private AuctionService auctionService; // Mocked dependency

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this); // Initializes mocks
    }

    @Test
    public void testPlaceBid() {
        Long auctionId = 1L;
        double bidAmount = 100.0;
        String userId = "user123";

        when(auctionService.placeBid(auctionId, bidAmount, userId)).thenReturn("Bid placed successfully!");

        ResponseEntity<?> response = auctionController.placeBid(auctionId, bidAmount, userId);

        assertNotNull(response);
        verify(auctionService, times(1)).placeBid(auctionId, bidAmount, userId);
    }

    @Test
    public void testCreateAuction(){
        // Given
        Auction auction = new Auction();
        auction.setItemName("Test Auction");
        auction.setStartingPrice(100.0);

        when(auctionService.createAuction(any(Auction.class))).thenReturn(auction);

        ResponseEntity<?> response = auctionController.createAuction(auction);

        assertNotNull(response);
        verify(auctionService, times(1)).createAuction(auction);
    }

    @Test
    public void testSuccessfulBid(){
        // Given
        Auction auction = new Auction();
        auction.setId(1L);

        Bid bid = new Bid();
        bid.setAmount(500.00);
        bid.setUserToken("Bidder1");
        bid.setAuction(auction);
        bid.setBidPlacedAt(LocalDateTime.now());


        when(auctionService.getSuccessfulBid(eq(1L))).thenReturn(bid);

        ResponseEntity<?> response = auctionController.getSuccessfulBid(auction.getId());
        assertNotNull(response);
        verify(auctionService, times(1)).getSuccessfulBid(eq(1L));

    }
}

