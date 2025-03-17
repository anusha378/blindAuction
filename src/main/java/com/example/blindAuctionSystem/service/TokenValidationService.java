package com.example.blindAuctionSystem.service;

import com.example.blindAuctionSystem.model.Token;
import com.example.blindAuctionSystem.model.User;
import com.example.blindAuctionSystem.repository.TokenRepository;
import com.example.blindAuctionSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TokenValidationService {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Autowired
    public TokenValidationService(TokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    public Optional<User> validateToken(String token) {
        Optional<Token> tokenEntity = tokenRepository.findByToken(token);

        if (tokenEntity.isPresent()) {
            // If the token is found, retrieve the user based on userId
            Long userId = tokenEntity.get().getUserId();
            return userRepository.findById(userId);
        }
        return Optional.empty();  // Token not found
    }

    public User register(User user){
        User newUser = userRepository.save(user);
        if (newUser != null) {
            tokenRepository.save(new Token(generateToken(), newUser.getId()));
        }
        return newUser;
    }

    private String generateToken() {
        String token = UUID.randomUUID().toString();
        return token;
    }

}

