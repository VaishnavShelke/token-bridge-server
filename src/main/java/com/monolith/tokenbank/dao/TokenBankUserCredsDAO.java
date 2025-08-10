package com.monolith.tokenbank.dao;

import com.monolith.tokenbank.entities.TokenBankUserCredsEntity;
import com.monolith.tokenbank.repository.TokenBankUserCredsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenBankUserCredsDAO {

    private static final Logger logger = LoggerFactory.getLogger(TokenBankUserCredsDAO.class);

    @Autowired
    private TokenBankUserCredsRepository tokenBankUserCredsRepository;

    public boolean doesUserExist(String username) {
        try {
            return tokenBankUserCredsRepository.existsByUsername(username);
        } catch (Exception e) {
            logger.error("TokenBank :: Error while checking if user exists for username: {} :: {}", username, e.getMessage());
            return false;
        }
    }

    public TokenBankUserCredsEntity getUserByUsername(String username) {
        try {
            return tokenBankUserCredsRepository.findById(username).orElse(null);
        } catch (Exception e) {
            logger.error("TokenBank :: Error while getting user by username: {} :: {}", username, e.getMessage());
            return null;
        }
    }

    public boolean updateUserGameIdAndRole(String username, String gameId, String userRole) {
        try {
            int rowsUpdated = tokenBankUserCredsRepository.updateGameIdAndUserRole(username, gameId, userRole);

            if (rowsUpdated > 0) {
                logger.info("TokenBank :: Successfully updated user {} with gameId: {} and role: {}", username, gameId, userRole);
                return true;
            } else {
                logger.warn("TokenBank :: Failed to update user {} with gameId: {} and role: {}", username, gameId, userRole);
            }
        } catch (Exception e) {
            logger.error("TokenBank :: Error while updating user {} with gameId: {} and role: {} :: {}", username, gameId, userRole, e.getMessage());
        }
        return false;
    }

    public void saveTokenBankUserCreds(TokenBankUserCredsEntity tokenBankUserCredsEntity) {
        try {
            tokenBankUserCredsRepository.save(tokenBankUserCredsEntity);
            logger.info("TokenBank :: Successfully saved user creds for gameId: {}, Username: {}", tokenBankUserCredsEntity.getGameId(), tokenBankUserCredsEntity.getUsername());
        } catch (Exception e) {
            logger.error("TokenBank :: Failed to save user creds for gameId: {}, Username: {} :: {}", tokenBankUserCredsEntity.getGameId(), tokenBankUserCredsEntity.getUsername(), e.getMessage());
            throw new RuntimeException("Failed to save user creds");
        }
    }


}
