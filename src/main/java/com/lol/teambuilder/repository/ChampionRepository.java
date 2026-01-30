package com.lol.teambuilder.repository;

import com.lol.teambuilder.model.Champion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChampionRepository extends JpaRepository<Champion, Long> {
    
    Optional<Champion> findByName(String name);
    
    List<Champion> findByChampionClass(String championClass);
    
    @Query("SELECT c FROM Champion c WHERE c.lanes LIKE %?1%")
    List<Champion> findByLane(String lane);
    
    List<Champion> findByNameContainingIgnoreCase(String name);
}
