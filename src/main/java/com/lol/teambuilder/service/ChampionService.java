package com.lol.teambuilder.service;

import com.lol.teambuilder.model.Champion;
import com.lol.teambuilder.repository.ChampionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChampionService {
    
    private final ChampionRepository championRepository;
    
    @Transactional(readOnly = true)
    public List<Champion> getAllChampions() {
        return championRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Champion getChampionById(Long id) {
        return championRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Campeão não encontrado com id: " + id));
    }
    
    @Transactional(readOnly = true)
    public List<Champion> getChampionsByClass(String championClass) {
        return championRepository.findByChampionClass(championClass);
    }
    
    @Transactional(readOnly = true)
    public List<Champion> getChampionsByLane(String lane) {
        return championRepository.findByLane(lane);
    }
    
    @Transactional(readOnly = true)
    public List<Champion> searchChampions(String name) {
        return championRepository.findByNameContainingIgnoreCase(name);
    }
    
    @Transactional
    public Champion createChampion(Champion champion) {
        return championRepository.save(champion);
    }
    
    @Transactional
    public Champion updateChampion(Long id, Champion champion) {
        Champion existing = getChampionById(id);
        existing.setName(champion.getName());
        existing.setRole(champion.getRole());
        existing.setChampionClass(champion.getChampionClass());
        existing.setIcon(champion.getIcon());
        existing.setPhysicalDamage(champion.getPhysicalDamage());
        existing.setMagicDamage(champion.getMagicDamage());
        existing.setTankiness(champion.getTankiness());
        existing.setCrowdControl(champion.getCrowdControl());
        existing.setLanes(champion.getLanes());
        return championRepository.save(existing);
    }
    
    @Transactional
    public void deleteChampion(Long id) {
        championRepository.deleteById(id);
    }
}
