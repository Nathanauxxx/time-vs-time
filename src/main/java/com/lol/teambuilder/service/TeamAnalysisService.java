package com.lol.teambuilder.service;

import com.lol.teambuilder.dto.TeamAnalysisDTO;
import com.lol.teambuilder.model.Champion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamAnalysisService {
    
    private final ChampionService championService;
    
    public TeamAnalysisDTO analyzeTeam(List<Long> championIds) {
        List<Champion> champions = new ArrayList<>();
        for (Long id : championIds) {
            if (id != null) {
                champions.add(championService.getChampionById(id));
            }
        }
        
        if (champions.isEmpty()) {
            return new TeamAnalysisDTO();
        }
        
        TeamAnalysisDTO.TeamStats stats = calculateStats(champions);
        List<String> strengths = analyzeStrengths(champions, stats);
        List<String> weaknesses = analyzeWeaknesses(champions, stats);
        List<String> suggestions = generateSuggestions(champions, stats);
        
        return new TeamAnalysisDTO(stats, strengths, weaknesses, suggestions);
    }
    
    private TeamAnalysisDTO.TeamStats calculateStats(List<Champion> champions) {
        int totalPhysical = champions.stream().mapToInt(Champion::getPhysicalDamage).sum();
        int totalMagic = champions.stream().mapToInt(Champion::getMagicDamage).sum();
        int totalTank = champions.stream().mapToInt(Champion::getTankiness).sum();
        int totalCC = champions.stream().mapToInt(Champion::getCrowdControl).sum();
        
        int totalDamage = totalPhysical + totalMagic;
        double physicalPercent = totalDamage > 0 ? (totalPhysical * 100.0) / totalDamage : 0;
        double magicPercent = totalDamage > 0 ? (totalMagic * 100.0) / totalDamage : 0;
        
        return new TeamAnalysisDTO.TeamStats(
            totalPhysical, totalMagic, totalTank, totalCC, 
            physicalPercent, magicPercent
        );
    }
    
    private List<String> analyzeStrengths(List<Champion> champions, TeamAnalysisDTO.TeamStats stats) {
        List<String> strengths = new ArrayList<>();
        
        // Balanceamento de dano
        if (stats.getPhysicalDamagePercent() >= 40 && stats.getPhysicalDamagePercent() <= 60) {
            strengths.add("✅ Excelente balanceamento entre dano físico e mágico");
        }
        
        // Frontline
        double avgTank = (double) stats.getTotalTankiness() / champions.size();
        if (avgTank >= 7) {
            strengths.add("✅ Forte frontline para proteger carries");
        }
        
        // Controle de Grupo
        double avgCC = (double) stats.getTotalCrowdControl() / champions.size();
        if (avgCC >= 7) {
            strengths.add("✅ Excelente controle de grupo para teamfights");
        }
        
        // Análise de classes
        long tankCount = champions.stream().filter(c -> c.getChampionClass().equals("tank")).count();
        long assassinCount = champions.stream().filter(c -> c.getChampionClass().equals("assassin")).count();
        
        if (tankCount >= 2) {
            strengths.add("✅ Múltiplos tanques para absorver dano");
        }
        
        if (assassinCount >= 2) {
            strengths.add("✅ Composição boa para picks e skirmishes");
        }
        
        return strengths;
    }
    
    private List<String> analyzeWeaknesses(List<Champion> champions, TeamAnalysisDTO.TeamStats stats) {
        List<String> weaknesses = new ArrayList<>();
        
        // Dano desbalanceado
        if (stats.getPhysicalDamagePercent() > 75) {
            weaknesses.add("⚠️ Muito dano físico - inimigos podem itemizar armadura facilmente");
        } else if (stats.getMagicDamagePercent() > 75) {
            weaknesses.add("⚠️ Muito dano mágico - inimigos podem itemizar resistência mágica facilmente");
        }
        
        // Falta de frontline
        double avgTank = (double) stats.getTotalTankiness() / champions.size();
        if (avgTank < 4) {
            weaknesses.add("❌ Time muito frágil - falta frontline para proteger");
        }
        
        // Falta de CC
        double avgCC = (double) stats.getTotalCrowdControl() / champions.size();
        if (avgCC < 4) {
            weaknesses.add("⚠️ Pouco controle de grupo - dificuldade em iniciar teamfights");
        }
        
        // Falta de atirador
        boolean hasMarksman = champions.stream()
            .anyMatch(c -> c.getChampionClass().equals("marksman"));
        if (!hasMarksman && champions.size() >= 4) {
            weaknesses.add("⚠️ Sem atirador - pode ter dificuldade contra tanques no late game");
        }
        
        return weaknesses;
    }
    
    private List<String> generateSuggestions(List<Champion> champions, TeamAnalysisDTO.TeamStats stats) {
        List<String> suggestions = new ArrayList<>();
        
        if (stats.getPhysicalDamagePercent() > 75) {
            suggestions.add("💡 Considere adicionar mais dano mágico à composição");
        }
        
        if (stats.getMagicDamagePercent() > 75) {
            suggestions.add("💡 Considere adicionar mais dano físico à composição");
        }
        
        double avgTank = (double) stats.getTotalTankiness() / champions.size();
        if (avgTank < 4) {
            suggestions.add("💡 Adicione um tank para melhorar a durabilidade do time");
        }
        
        double avgCC = (double) stats.getTotalCrowdControl() / champions.size();
        if (avgCC < 5) {
            suggestions.add("💡 Procure campeões com mais controle de grupo");
        }
        
        return suggestions;
    }
}
