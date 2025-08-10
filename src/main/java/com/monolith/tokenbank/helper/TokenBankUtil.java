package com.monolith.tokenbank.helper;

import java.security.SecureRandom;
import java.util.Random;

public class TokenBankUtil {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int KEY_LENGTH = 32;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomGameId(){
        int length = 6;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder gameId = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            gameId.append(characters.charAt(index));
        }

        return gameId.toString();
    }

    public static String generateApiKey() {
        StringBuilder apiKey = new StringBuilder(KEY_LENGTH);
        for (int i = 0; i < KEY_LENGTH; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            apiKey.append(CHARACTERS.charAt(index));
        }
        return apiKey.toString();
    }
    
    /**
     * Generates a unique item ID that fits within 20 characters
     * Format: ITEM_[timestamp][random] (max 20 chars)
     */
    public static String generateItemId() {
        // Use current timestamp (last 8 digits) + 3 random chars
        // This gives us ITEM_ (5) + 8 digits + 3 random = 16 chars (well within 20 limit)
        long timestamp = System.currentTimeMillis();
        String timestampSuffix = String.valueOf(timestamp).substring(5); // Last 8 digits
        
        StringBuilder randomSuffix = new StringBuilder(3);
        for (int i = 0; i < 3; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            randomSuffix.append(CHARACTERS.charAt(index));
        }
        
        return "ITEM_" + timestampSuffix + randomSuffix.toString();
    }


}
