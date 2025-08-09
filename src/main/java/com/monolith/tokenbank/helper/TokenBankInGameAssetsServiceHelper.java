package com.monolith.tokenbank.helper;

import com.monolith.shared.dao.ItemInfoDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.monolith.tokenbank.helper.TokenBankConstants.TOKEN_BANK_PREPEND;

public class TokenBankInGameAssetsServiceHelper {

    private static final Logger logger = LoggerFactory.getLogger(TokenBankInGameAssetsServiceHelper.class);

    /**
     * Generates a unique incremental item ID for a specific game
     * 
     * @param gameId The game ID to generate the item ID for
     * @param itemInfoDAO The DAO to generate the next incremental ID
     * @return A unique incremental item ID (e.g., "00001", "00002", etc.)
     */
    public static String generateUniqueItemId(String gameId, ItemInfoDAO itemInfoDAO) {
        try {
            // Validate input parameters
            if (gameId == null || gameId.trim().isEmpty()) {
                logger.error("{} :: Game ID cannot be null or empty", TOKEN_BANK_PREPEND);
                throw new IllegalArgumentException("Game ID cannot be null or empty");
            }
            
            if (itemInfoDAO == null) {
                logger.error("{} :: ItemInfoDAO cannot be null", TOKEN_BANK_PREPEND);
                throw new IllegalArgumentException("ItemInfoDAO cannot be null");
            }
            
            // Generate incremental 5-digit item ID from DAO
            String generatedItemId = itemInfoDAO.getNextIncrementalItemId(gameId);
            
            logger.info("{} :: Generated unique 5-digit incremental item ID: {} for game: {}", TOKEN_BANK_PREPEND, generatedItemId, gameId);
            return generatedItemId;
            
        } catch (Exception e) {
            logger.error("{} :: Failed to generate unique item ID for game: {} - {}", TOKEN_BANK_PREPEND, gameId, e.getMessage());
            throw new RuntimeException("Failed to generate unique item ID for game: " + gameId, e);
        }
    }
} 