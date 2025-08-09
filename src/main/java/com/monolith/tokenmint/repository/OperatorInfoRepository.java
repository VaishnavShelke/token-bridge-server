package com.monolith.tokenmint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.monolith.tokenmint.entities.OperatorInfoBean;

@Repository
public interface OperatorInfoRepository extends JpaRepository<OperatorInfoBean, String> {
    // operatorId is the primary key, so findById can be used
}
