package com.monolith.tokenmint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.monolith.tokenmint.entities.ConfigResource;
import com.monolith.tokenmint.entities.ConfigResourceId;
import java.util.Optional;

@Repository
public interface ConfigResourceRepository extends JpaRepository<ConfigResource, ConfigResourceId> {
    
    Optional<ConfigResource> findByGroupNameAndName(String groupName, String name);
}
