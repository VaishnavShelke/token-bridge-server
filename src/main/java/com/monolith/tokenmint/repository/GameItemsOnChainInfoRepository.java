package com.monolith.tokenmint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.monolith.tokenmint.beans.GameItemsOnChainInfoBean;
import com.monolith.tokenmint.beans.GameItemsOnChainInfoId;
import java.util.Optional;

@Repository
public interface GameItemsOnChainInfoRepository extends JpaRepository<GameItemsOnChainInfoBean, GameItemsOnChainInfoId> {
    
    Optional<GameItemsOnChainInfoBean> findByGameIdAndItemId(String gameId, String itemId);
}
