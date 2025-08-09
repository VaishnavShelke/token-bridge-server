package com.monolith.tokenmint.entities;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "tokenmint_transaction")
public class TokenMintTransaction {
    
    @Id
    @Column(name = "tokenmint_transaction_id")
    private String tokenMintTransactionId;
    
    @Column(name = "transaction_summary_json", columnDefinition = "TEXT")
    private String transactionSummaryJson;
}
