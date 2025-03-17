package com.example.blindAuctionSystem.service;

import com.example.blindAuctionSystem.model.Auction;
import com.example.blindAuctionSystem.model.Bid;
import com.example.blindAuctionSystem.model.Token;
import com.example.blindAuctionSystem.model.User;
import com.example.blindAuctionSystem.repository.TokenRepository;
import com.example.blindAuctionSystem.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TokenValidationServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TokenValidationService tokenValidationService;

    @Test
    public void testCreateUser() {
        // Given
        User user = new User();
        user.setEmail("email");
        user.setName("name");

        // Mock the repository behavior
        when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);

        // When
        User createdUser = tokenValidationService.register(user);

        // Then
        assertNotNull(createdUser);
    }

    @Test
    public void validateToken() {
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
        when(tokenRepository.findByToken("opaque-token-123")).thenReturn(Optional.of(token));
        when(userRepository.findById(5L)).thenReturn(Optional.of(user));

        // When
        Optional<User> existingUser = tokenValidationService.validateToken("opaque-token-123");

        // Then
        assertNotNull(existingUser);
    }
}
