package com.example.blindAuctionSystem.controller;

import com.example.blindAuctionSystem.model.Token;
import com.example.blindAuctionSystem.model.TokenRequest;
import com.example.blindAuctionSystem.model.User;
import com.example.blindAuctionSystem.repository.TokenRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.blindAuctionSystem.service.TokenValidationService;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private TokenValidationService tokenValidationService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this); // Initializes mocks
    }

    @Test
    public void testValidateToken_ValidToken_ReturnsUser() {
        TokenRequest request = new TokenRequest();
        request.setToken("opaque-token-123");

        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setId(5L);
        Token token = new Token();
        token.setToken(request.getToken());
        token.setUserId(user.getId());

        when(tokenRepository.findByToken(any())).thenReturn(Optional.of(token));
        when(tokenValidationService.validateToken(request.getToken())).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.validateToken(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
    }

    @Test
    public void testValidateToken_InvalidToken_ReturnsUnauthorized() {
        TokenRequest request = new TokenRequest();
        request.setToken("invalid-token");

        ResponseEntity<User> response = userController.validateToken(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
