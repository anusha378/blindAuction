package com.example.blindAuctionSystem.config;

import com.example.blindAuctionSystem.model.Token;
import com.example.blindAuctionSystem.model.User;
import com.example.blindAuctionSystem.repository.TokenRepository;
import com.example.blindAuctionSystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository, TokenRepository tokenRepository) {
        return args -> {
            User user = new User();
            user.setName("John Doe");
            user.setEmail("john.doe@example.com");
            userRepository.save(user);

            Token token = new Token("opaque-token-123", user.getId());
            tokenRepository.save(token);
        };
    }
}
