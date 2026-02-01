package com.lol.teambuilder.service;

import com.lol.teambuilder.model.Champion;
import com.lol.teambuilder.repository.ChampionRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamRecommendationService {
    
    private final ChampionRepository championRepository;
    
    public Map<String, Object> analyzeTeamAndRecommend(List<Long> selectedChampionIds, String lane) {
        List<Champion> selectedChampions = championRepository.findAllById(selectedChampionIds);
        List<Champion> allChampions = championRepository.findAll();
        
        Map<String, Object> result = new HashMap<>();
        result.put("teamAnalysis", analyzeTeam(selectedChampions));
        result.put("recommendations", recommendChampions(selectedChampions, allChampions, lane));
        result.put("synergy", calculateSynergy(selectedChampions));
        
        return result;
    }
    
    private Map<String, Object> analyzeTeam(List<Champion> champions) {
        Map<String, Object> analysis = new HashMap<>();
        
        double avgPhysicalDamage = champions.stream().mapToInt(Champion::getPhysicalDamage).average().orElse(0);
        double avgMagicDamage = champions.stream().mapToInt(Champion::getMagicDamage).average().orElse(0);
        double avgTankiness = champions.stream().mapToInt(Champion::getTankiness).average().orElse(0);
        double avgCC = champions.stream().mapToInt(Champion::getCrowdControl).average().orElse(0);
        
        analysis.put("physicalDamage", Math.round(avgPhysicalDamage * 10) / 10.0);
        analysis.put("magicDamage", Math.round(avgMagicDamage * 10) / 10.0);
        analysis.put("tankiness", Math.round(avgTankiness * 10) / 10.0);
        analysis.put("crowdControl", Math.round(avgCC * 10) / 10.0);
        
        // Identificar pontos fracos
        List<String> weaknesses = new ArrayList<>();
        if (avgPhysicalDamage < 4) weaknesses.add("Pouco dano físico");
        if (avgMagicDamage < 4) weaknesses.add("Pouco dano mágico");
        if (avgTankiness < 4) weaknesses.add("Time frágil");
        if (avgCC < 4) weaknesses.add("Pouco controle de grupo");
        
        analysis.put("weaknesses", weaknesses);
        analysis.put("balance", calculateBalance(avgPhysicalDamage, avgMagicDamage, avgTankiness, avgCC));
        
        return analysis;
    }
    
    private List<Map<String, Object>> recommendChampions(List<Champion> selected, List<Champion> all, String lane) {
        // Remove campeões já selecionados
        Set<Long> selectedIds = selected.stream().map(Champion::getId).collect(Collectors.toSet());
        List<Champion> available = all.stream()
            .filter(c -> !selectedIds.contains(c.getId()))
            .filter(c -> lane == null || c.getLanes().contains(lane))
            .collect(Collectors.toList());
        
        // Calcular score para cada campeão baseado no que falta no time
        Map<String, Object> teamAnalysis = analyzeTeam(selected);
        @SuppressWarnings("unchecked")
        List<String> weaknesses = (List<String>) teamAnalysis.get("weaknesses");
        
        return available.stream()
            .map(champion -> {
                Map<String, Object> rec = new HashMap<>();
                rec.put("champion", champion);
                rec.put("score", calculateRecommendationScore(champion, weaknesses, teamAnalysis));
                rec.put("reasons", getRecommendationReasons(champion, weaknesses));
                return rec;
            })
            .sorted((a, b) -> Double.compare((Double) b.get("score"), (Double) a.get("score")))
            .limit(5)
            .collect(Collectors.toList());
    }
    
    private double calculateRecommendationScore(Champion champion, List<String> weaknesses, Map<String, Object> analysis) {
        double score = 50.0;
        
        for (String weakness : weaknesses) {
            if (weakness.contains("físico") && champion.getPhysicalDamage() >= 7) score += 20;
            if (weakness.contains("mágico") && champion.getMagicDamage() >= 7) score += 20;
            if (weakness.contains("frágil") && champion.getTankiness() >= 7) score += 20;
            if (weakness.contains("controle") && champion.getCrowdControl() >= 7) score += 20;
        }
        
        return Math.min(score, 100);
    }
    
    private List<String> getRecommendationReasons(Champion champion, List<String> weaknesses) {
        List<String> reasons = new ArrayList<>();
        
        for (String weakness : weaknesses) {
            if (weakness.contains("físico") && champion.getPhysicalDamage() >= 7) 
                reasons.add("Alto dano físico (" + champion.getPhysicalDamage() + "/10)");
            if (weakness.contains("mágico") && champion.getMagicDamage() >= 7) 
                reasons.add("Alto dano mágico (" + champion.getMagicDamage() + "/10)");
            if (weakness.contains("frágil") && champion.getTankiness() >= 7) 
                reasons.add("Muito resistente (" + champion.getTankiness() + "/10)");
            if (weakness.contains("controle") && champion.getCrowdControl() >= 7) 
                reasons.add("Forte controle de grupo (" + champion.getCrowdControl() + "/10)");
        }
        
        if (reasons.isEmpty()) {
            reasons.add("Campeão equilibrado para o time");
        }
        
        return reasons;
    }
    
    private double calculateSynergy(List<Champion> champions) {
        if (champions.size() < 2) return 0;
        
        // Sinergia baseada em composição equilibrada
        double avgPD = champions.stream().mapToInt(Champion::getPhysicalDamage).average().orElse(0);
        double avgMD = champions.stream().mapToInt(Champion::getMagicDamage).average().orElse(0);
        double avgTank = champions.stream().mapToInt(Champion::getTankiness).average().orElse(0);
        double avgCC = champions.stream().mapToInt(Champion::getCrowdControl).average().orElse(0);
        
        // Time balanceado tem melhor sinergia
        double balance = 100 - (Math.abs(avgPD - 5) + Math.abs(avgMD - 5) + Math.abs(avgTank - 5) + Math.abs(avgCC - 5)) * 5;
        
        return Math.max(0, Math.min(100, balance));
    }
    
    private String calculateBalance(double pd, double md, double tank, double cc) {
        double variance = Math.abs(pd - 5) + Math.abs(md - 5) + Math.abs(tank - 5) + Math.abs(cc - 5);
        
        if (variance < 5) return "Muito equilibrado";
        if (variance < 10) return "Equilibrado";
        if (variance < 15) return "Desequilibrado";
        return "Muito desequilibrado";
    }
}
