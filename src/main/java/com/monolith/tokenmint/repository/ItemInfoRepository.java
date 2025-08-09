package com.monolith.tokenmint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.monolith.tokenmint.beans.ItemInfoBean;
import com.monolith.tokenmint.beans.ItemStatus;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemInfoRepository extends JpaRepository<ItemInfoBean, String> {
    
    @Query("SELECT i FROM ItemInfoBean i WHERE i.gameId = :gameId AND i.itemId = :itemId")
    Optional<ItemInfoBean> findByGameIdAndItemId(@Param("gameId") String gameId, @Param("itemId") String itemId);
    
    List<ItemInfoBean> findByGameId(String gameId);
    
    @Query("SELECT MAX(i.contractItemId) FROM ItemInfoBean i WHERE i.gameId = :gameId")
    Integer findMaxContractItemIdByGameId(@Param("gameId") String gameId);
    
    @Query(value = "SELECT MAX(CAST(item_id AS UNSIGNED)) FROM game_items WHERE game_id = :gameId AND item_id REGEXP '^[0-9]{5}$'", nativeQuery = true)
    Integer findMaxNumericItemIdByGameId(@Param("gameId") String gameId);
    
    @Modifying
    @Transactional
    @Query("UPDATE ItemInfoBean i SET i.itemStatus = :status WHERE i.gameId = :gameId AND i.itemId = :itemId")
    int updateItemStatus(@Param("gameId") String gameId, @Param("itemId") String itemId, @Param("status") ItemStatus status);
}