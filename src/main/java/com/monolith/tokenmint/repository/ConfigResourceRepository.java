package com.monolith.tokenmint.repository;

import com.monolith.tokenmint.entities.ConfigResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.monolith.tokenmint.entities.ConfigResourceEntityId;
import java.util.Optional;

@Repository
public interface ConfigResourceRepository extends JpaRepository<ConfigResourceEntity, ConfigResourceEntityId> {
    
    Optional<ConfigResourceEntity> findByGroupNameAndName(String groupName, String name);
}
