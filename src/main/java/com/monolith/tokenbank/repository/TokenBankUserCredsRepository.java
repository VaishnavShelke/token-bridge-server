package com.monolith.tokenbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.monolith.tokenbank.entities.TokenBankUserCreds;
import jakarta.transaction.Transactional;

@Repository
public interface TokenBankUserCredsRepository extends JpaRepository<TokenBankUserCreds, String> {
    
    boolean existsByUsername(String username);
    
    @Modifying
    @Transactional
    @Query("UPDATE TokenBankUserCreds t SET t.gameId = :gameId, t.userRole = :userRole WHERE t.username = :username")
    int updateGameIdAndUserRole(@Param("username") String username, @Param("gameId") String gameId, @Param("userRole") String userRole);
}
