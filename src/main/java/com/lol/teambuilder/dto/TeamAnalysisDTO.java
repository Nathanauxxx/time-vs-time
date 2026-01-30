package com.lol.teambuilder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamAnalysisDTO {
    
    private TeamStats stats;
    private List<String> strengths;
    private List<String> weaknesses;
    private List<String> suggestions;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamStats {
        private int totalPhysicalDamage;
        private int totalMagicDamage;
        private int totalTankiness;
        private int totalCrowdControl;
        private double physicalDamagePercent;
        private double magicDamagePercent;
    }
}
