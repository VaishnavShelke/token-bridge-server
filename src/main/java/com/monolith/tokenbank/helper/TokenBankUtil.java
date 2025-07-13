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
}
