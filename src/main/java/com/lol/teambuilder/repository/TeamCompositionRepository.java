package com.lol.teambuilder.repository;

import com.lol.teambuilder.model.TeamComposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamCompositionRepository extends JpaRepository<TeamComposition, Long> {
    
    List<TeamComposition> findByNameContainingIgnoreCase(String name);
}
