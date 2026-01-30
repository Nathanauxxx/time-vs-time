package com.lol.teambuilder.service;

import com.lol.teambuilder.model.TeamComposition;
import com.lol.teambuilder.repository.TeamCompositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamCompositionService {
    
    private final TeamCompositionRepository teamCompositionRepository;
    
    @Transactional(readOnly = true)
    public List<TeamComposition> getAllCompositions() {
        return teamCompositionRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public TeamComposition getCompositionById(Long id) {
        return teamCompositionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Composição não encontrada com id: " + id));
    }
    
    @Transactional(readOnly = true)
    public List<TeamComposition> searchCompositions(String name) {
        return teamCompositionRepository.findByNameContainingIgnoreCase(name);
    }
    
    @Transactional
    public TeamComposition saveComposition(TeamComposition composition) {
        return teamCompositionRepository.save(composition);
    }
    
    @Transactional
    public void deleteComposition(Long id) {
        teamCompositionRepository.deleteById(id);
    }
}
