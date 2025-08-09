package com.monolith.tokenmint.entities;

import java.io.Serializable;
import java.util.Objects;

public class ConfigResourceId implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String groupName;
    private String name;
    
    public ConfigResourceId() {}
    
    public ConfigResourceId(String groupName, String name) {
        this.groupName = groupName;
        this.name = name;
    }
    
    public String getGroupName() {
        return groupName;
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigResourceId that = (ConfigResourceId) o;
        return Objects.equals(groupName, that.groupName) && Objects.equals(name, that.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(groupName, name);
    }
}
