package com.monolith.tokenmint.repository;

import com.monolith.tokenmint.entities.OperatorInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorInfoRepository extends JpaRepository<OperatorInfoEntity, String> {
    // operatorId is the primary key, so findById can be used
}
