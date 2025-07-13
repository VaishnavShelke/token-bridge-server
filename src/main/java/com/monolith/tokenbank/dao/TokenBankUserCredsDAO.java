package com.monolith.tokenbank.dao;

import com.monolith.shared.dao.ItemInfoDAO;
import com.monolith.tokenbank.beans.TokenBankUserCreds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class TokenBankUserCredsDAO {

    private static final Logger logger = LoggerFactory.getLogger(TokenBankUserCredsDAO.class);

    @Autowired
    @Qualifier("tokenmintjdbctemplate")
    JdbcTemplate globalJdbcTemplate;

    public boolean doesUserExist(String username) {
        try {
            String query = "SELECT COUNT(*) FROM tokenbank_user_creds WHERE username = ?";
            Integer count = globalJdbcTemplate.queryForObject(query, Integer.class, username);
            return count != null && count > 0;
        } catch (Exception e) {
            logger.error("TokenBank :: Error while checking if user exists for username: {} :: {}", username, e.getMessage());
            return false;
        }
    }

    public TokenBankUserCreds getUserByUsername(String username) {
        try {
            String query = "SELECT username, password, game_id, user_role FROM tokenbank_user_creds WHERE username = ?";
            return globalJdbcTemplate.queryForObject(query, new TokenBankUserCredsRowMapper(), username);
        } catch (Exception e) {
            logger.error("TokenBank :: Error while getting user by username: {} :: {}", username, e.getMessage());
            return null;
        }
    }

    public boolean updateUserGameIdAndRole(String username, String gameId, String userRole) {
        try {
            String query = "UPDATE tokenbank_user_creds SET game_id = ?, user_role = ? WHERE username = ?";
            int rowsUpdated = globalJdbcTemplate.update(query, gameId, userRole, username);
            
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

    public boolean saveTokenBankUserCreds(TokenBankUserCreds tokenBankUserCreds) {
        try {
            String query = "INSERT INTO tokenbank_user_creds (username, password, game_id, user_role) VALUES (?, ?, ?, ?)";
            int rowsInserted = globalJdbcTemplate.update(
                    query,
                    tokenBankUserCreds.getUsername(),  // Corrected
                    tokenBankUserCreds.getPassword(),
                    tokenBankUserCreds.getGameId(),
                    tokenBankUserCreds.getUserRole()
            );

            if (rowsInserted > 0) {
                logger.info("TokenBank :: Successfully saved user creds for gameId: {}, Username: {}", tokenBankUserCreds.getGameId(), tokenBankUserCreds.getUsername());
                return true;
            } else {
                logger.warn("TokenBank :: Failed to save user creds for gameId: {}, Username: {}", tokenBankUserCreds.getGameId(), tokenBankUserCreds.getUsername());
            }
        } catch (Exception e) {
            logger.error("TokenBank :: Error while saving user creds for gameId: {}, Username: {} :: {}", tokenBankUserCreds.getGameId(), tokenBankUserCreds.getUsername(), e.getMessage());
        }
        return false;
    }

    private static class TokenBankUserCredsRowMapper implements RowMapper<TokenBankUserCreds> {
        @Override
        public TokenBankUserCreds mapRow(ResultSet rs, int rowNum) throws SQLException {
            TokenBankUserCreds userCreds = new TokenBankUserCreds();
            userCreds.setUsername(rs.getString("username"));
            userCreds.setPassword(rs.getString("password"));
            userCreds.setGameId(rs.getString("game_id"));
            userCreds.setUserRole(rs.getString("user_role"));
            return userCreds;
        }
    }
}
