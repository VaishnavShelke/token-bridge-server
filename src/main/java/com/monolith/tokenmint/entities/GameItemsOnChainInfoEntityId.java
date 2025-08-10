package com.monolith.tokenmint.entities;

import java.io.Serializable;
import java.util.Objects;

public class GameItemsOnChainInfoEntityId implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String gameId;
    private String itemId;
    
    public GameItemsOnChainInfoEntityId() {}
    
    public GameItemsOnChainInfoEntityId(String gameId, String itemId) {
        this.gameId = gameId;
        this.itemId = itemId;
    }
    
    public String getGameId() {
        return gameId;
    }
    
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
    
    public String getItemId() {
        return itemId;
    }
    
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameItemsOnChainInfoEntityId that = (GameItemsOnChainInfoEntityId) o;
        return Objects.equals(gameId, that.gameId) && Objects.equals(itemId, that.itemId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(gameId, itemId);
    }
}
