package com.lol.teambuilder.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * DTO para receber dados da API Data Dragon da Riot Games
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RiotChampionDTO {
    
    private String id;
    private String key;
    private String name;
    private String title;
    private String blurb;
    private Info info;
    private Stats stats;
    private List<String> tags;
    private String partype;
    
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Info {
        private int attack;      // 0-10: Dano físico
        private int defense;     // 0-10: Defesa/Tankiness
        private int magic;       // 0-10: Dano mágico
        private int difficulty;  // 0-10: Dificuldade
    }
    
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Stats {
        private double hp;
        private double hpperlevel;
        private double mp;
        private double mpperlevel;
        private double movespeed;
        private double armor;
        private double armorperlevel;
        private double spellblock;
        private double spellblockperlevel;
        private double attackrange;
        private double hpregen;
        private double hpregenperlevel;
        private double mpregen;
        private double mpregenperlevel;
        private double crit;
        private double critperlevel;
        private double attackdamage;
        private double attackdamageperlevel;
        private double attackspeedperlevel;
        private double attackspeed;
    }
    
    /**
     * Mapeia tags da Riot para classes do sistema
     */
    public String getChampionClass() {
        if (tags == null || tags.isEmpty()) {
            return "fighter";
        }
        
        String primaryTag = tags.get(0).toLowerCase();
        
        return switch (primaryTag) {
            case "fighter" -> "fighter";
            case "tank" -> "tank";
            case "mage" -> "mage";
            case "assassin" -> "assassin";
            case "marksman" -> "marksman";
            case "support" -> "support";
            default -> "fighter";
        };
    }
    
    /**
     * Determina a lane primária baseado nas tags e características
     */
    public String getPrimaryLane() {
        if (tags == null || tags.isEmpty()) {
            return "mid";
        }
        
        String primaryTag = tags.get(0).toLowerCase();
        
        return switch (primaryTag) {
            case "fighter" -> "top";
            case "tank" -> tags.size() > 1 && tags.get(1).equalsIgnoreCase("Support") ? "support" : "top";
            case "mage" -> "mid";
            case "assassin" -> "mid";
            case "marksman" -> "adc";
            case "support" -> "support";
            default -> "mid";
        };
    }
}
