package com.monolith.tokenmint.repository;

import com.monolith.tokenmint.entities.GameConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.monolith.tokenmint.entities.GameConfigEntityId;
import java.util.Optional;

@Repository
public interface GameConfigRepository extends JpaRepository<GameConfigEntity, GameConfigEntityId> {
    
    Optional<GameConfigEntity> findByGameIdAndConfigType(String gameId, String configType);
}
