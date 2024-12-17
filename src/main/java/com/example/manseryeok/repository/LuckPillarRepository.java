package com.example.manseryeok.repository;

import com.example.manseryeok.entity.LuckPillar;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LuckPillarRepository extends JpaRepository<LuckPillar, Long> {

    @Query("SELECT l FROM LuckPillar l WHERE l.date LIKE CONCAT('%', :ganJi, '%')")
    Optional<LuckPillar> findByGanji(@Param("ganJi") String ganJi);
}
