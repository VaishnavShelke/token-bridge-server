package com.monolith.tokenmint.repository;

import com.monolith.tokenmint.entities.ETHContractInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.monolith.tokenmint.entities.ETHContractInfoEntityId;
import java.util.Optional;

@Repository
public interface ETHContractInfoRepository extends JpaRepository<ETHContractInfoEntity, ETHContractInfoEntityId> {
    
    Optional<ETHContractInfoEntity> findByGameIdAndEthContractId(String gameId, String ethContractId);
}
