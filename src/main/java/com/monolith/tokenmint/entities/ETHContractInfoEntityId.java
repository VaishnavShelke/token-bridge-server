package com.monolith.tokenmint.entities;

import java.io.Serializable;
import java.util.Objects;

public class ETHContractInfoEntityId implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String gameId;
    private String ethContractId;
    
    public ETHContractInfoEntityId() {}
    
    public ETHContractInfoEntityId(String gameId, String ethContractId) {
        this.gameId = gameId;
        this.ethContractId = ethContractId;
    }
    
    public String getGameId() {
        return gameId;
    }
    
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
    
    public String getEthContractId() {
        return ethContractId;
    }
    
    public void setEthContractId(String ethContractId) {
        this.ethContractId = ethContractId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ETHContractInfoEntityId that = (ETHContractInfoEntityId) o;
        return Objects.equals(gameId, that.gameId) && Objects.equals(ethContractId, that.ethContractId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(gameId, ethContractId);
    }
}
