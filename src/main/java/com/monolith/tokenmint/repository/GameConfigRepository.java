package com.monolith.tokenmint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.monolith.tokenmint.entities.GameConfigInfo;
import com.monolith.tokenmint.entities.GameConfigInfoId;
import java.util.Optional;

@Repository
public interface GameConfigRepository extends JpaRepository<GameConfigInfo, GameConfigInfoId> {
    
    Optional<GameConfigInfo> findByGameIdAndConfigType(String gameId, String configType);
}
