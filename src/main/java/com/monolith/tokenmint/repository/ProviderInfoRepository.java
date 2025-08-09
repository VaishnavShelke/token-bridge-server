package com.monolith.tokenmint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.monolith.tokenmint.entities.ProviderInfo;

@Repository
public interface ProviderInfoRepository extends JpaRepository<ProviderInfo, String> {
    // providerId is the primary key, so findById can be used
}
