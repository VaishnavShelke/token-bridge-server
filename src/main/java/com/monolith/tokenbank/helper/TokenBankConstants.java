package com.monolith.tokenbank.helper;

public class TokenBankConstants {
    public static String TOKEN_BANK_PREPEND = "TokenBank :: ";
    
    // Status codes
    public static final String STATUS_CODE_SUCCESS = "000";
    public static final String STATUS_CODE_VALIDATION_FAILED = "001";
    public static final String STATUS_CODE_USER_ALREADY_EXISTS = "002";
    public static final String STATUS_CODE_INTERNAL_ERROR = "003";
    public static final String STATUS_CODE_GAME_MISMATCH = "004";
    public static final String STATUS_CODE_USER_NOT_FOUND = "005";
    public static final String USER_VALIDATION_FAILED = "006";
    
    // Messages
    public static final String MESSAGE_SUCCESS = "User created successfully";
    public static final String MESSAGE_VALIDATION_FAILED = "Username and password are required";
    public static final String MESSAGE_USER_ALREADY_EXISTS = "User already exists";
    public static final String MESSAGE_INTERNAL_ERROR = "Internal server error occurred";
    public static final String MESSAGE_GAME_ONBOARDED = "Game onboarded successfully";
    public static final String MESSAGE_GAME_EDITED = "Game edited successfully";
    public static final String MESSAGE_GAME_DELETED = "Game deleted successfully";
    public static final String MESSAGE_GAME_MISMATCH = "User is already associated with a different game";
    public static final String MESSAGE_USER_NOT_FOUND = "User not found";
    
    // Default values
    public static final String DEFAULT_GAME_ID = "DEFAULT_GAME";
    public static final String DEFAULT_USER_ROLE = "USER";
    public static final String ADMIN_USER_ROLE = "ADMIN";
}
