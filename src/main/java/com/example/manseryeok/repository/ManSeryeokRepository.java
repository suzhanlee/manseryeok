package com.example.manseryeok.repository;

import com.example.manseryeok.entity.ManSeryeok;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ManSeryeokRepository extends JpaRepository<ManSeryeok, Long> {

    @Query("SELECT m.solarGanJi FROM ManSeryeok m WHERE m.solarDate = :localDate")
    Optional<String> findSolarGanJiBySolarDate(@Param("localDate") LocalDate localDate);

    @Query("SELECT m.luna.lunarGanJi FROM ManSeryeok m WHERE m.luna.lunarDate = :localDate")
    Optional<String> findLunarGanJiByLunarDate(@Param("localDate") LocalDate localDate);
}
