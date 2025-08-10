package com.monolith.tokenmint.entities;

import java.io.Serializable;
import java.util.Objects;

public class GameConfigEntityId implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String gameId;
    private String configType;
    
    public GameConfigEntityId() {}
    
    public GameConfigEntityId(String gameId, String configType) {
        this.gameId = gameId;
        this.configType = configType;
    }
    
    public String getGameId() {
        return gameId;
    }
    
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
    
    public String getConfigType() {
        return configType;
    }
    
    public void setConfigType(String configType) {
        this.configType = configType;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameConfigEntityId that = (GameConfigEntityId) o;
        return Objects.equals(gameId, that.gameId) && Objects.equals(configType, that.configType);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(gameId, configType);
    }
}
