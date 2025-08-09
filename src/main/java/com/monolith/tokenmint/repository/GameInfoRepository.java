package com.monolith.tokenmint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.monolith.tokenmint.entities.GameInfo;

@Repository
public interface GameInfoRepository extends JpaRepository<GameInfo, String> {
    // Custom query methods can be added here if needed
}
