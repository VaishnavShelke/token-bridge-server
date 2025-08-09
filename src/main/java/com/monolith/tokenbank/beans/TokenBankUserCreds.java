package com.monolith.tokenbank.beans;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "tokenbank_user_creds")
public class TokenBankUserCreds {
    
    @Id
    @Column(name = "username")
    private String username;
    
    @Column(name = "game_id")
    private String gameId;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "user_role")
    private String userRole;
}
