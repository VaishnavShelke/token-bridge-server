package com.monolith.tokenmint.repository;

import com.monolith.tokenmint.entities.ChainProviderInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderInfoRepository extends JpaRepository<ChainProviderInfoEntity, String> {
    // providerId is the primary key, so findById can be used
}
