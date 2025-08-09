package com.monolith.tokenmint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.monolith.tokenmint.entities.TokenMintTransaction;

@Repository
public interface TokenMintTransactionRepository extends JpaRepository<TokenMintTransaction, String> {
    // Basic CRUD operations are inherited from JpaRepository
}
