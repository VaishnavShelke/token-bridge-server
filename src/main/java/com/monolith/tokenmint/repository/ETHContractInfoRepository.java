package com.monolith.tokenmint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.monolith.tokenmint.entities.ETHContractInfo;
import com.monolith.tokenmint.entities.ETHContractInfoId;
import java.util.Optional;

@Repository
public interface ETHContractInfoRepository extends JpaRepository<ETHContractInfo, ETHContractInfoId> {
    
    Optional<ETHContractInfo> findByGameIdAndEthContractId(String gameId, String ethContractId);
}
