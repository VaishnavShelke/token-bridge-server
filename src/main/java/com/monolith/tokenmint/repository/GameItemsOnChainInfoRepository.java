package com.monolith.tokenmint.repository;

import com.monolith.tokenmint.entities.GameItemsOnChainInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.monolith.tokenmint.entities.GameItemsOnChainInfoEntityId;
import java.util.Optional;

@Repository
public interface GameItemsOnChainInfoRepository extends JpaRepository<GameItemsOnChainInfoEntity, GameItemsOnChainInfoEntityId> {
    
    Optional<GameItemsOnChainInfoEntity> findByGameIdAndItemId(String gameId, String itemId);
}
