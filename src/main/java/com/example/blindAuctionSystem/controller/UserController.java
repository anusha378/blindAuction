package com.example.blindAuctionSystem.controller;
import com.example.blindAuctionSystem.model.TokenRequest;
import com.example.blindAuctionSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.blindAuctionSystem.service.TokenValidationService;

import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {

    private final TokenValidationService tokenValidationService;

    @Autowired
    public UserController(TokenValidationService tokenValidationService) {
        this.tokenValidationService = tokenValidationService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        tokenValidationService.register(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/validate-token")
    public ResponseEntity<User> validateToken(@RequestBody TokenRequest tokenRequest) {
        Optional<User> user = tokenValidationService.validateToken(tokenRequest.getToken());

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}

