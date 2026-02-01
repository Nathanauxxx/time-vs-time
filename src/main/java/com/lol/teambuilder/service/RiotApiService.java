package com.lol.teambuilder.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lol.teambuilder.dto.RiotChampionDTO;
import com.lol.teambuilder.model.Champion;
import com.lol.teambuilder.repository.ChampionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Serviço para sincronizar dados de campeões com a API oficial da Riot Games (Data Dragon)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RiotApiService {
    
    private final ChampionRepository championRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    // URL da API Data Dragon (dados estáticos, não requer API key)
    private static final String RIOT_API_VERSION = "14.1.1"; // Atualizar conforme versão do jogo
    private static final String CHAMPIONS_URL = "https://ddragon.leagueoflegends.com/cdn/" + RIOT_API_VERSION + "/data/pt_BR/champion.json";
    private static final String CHAMPION_DETAIL_URL = "https://ddragon.leagueoflegends.com/cdn/" + RIOT_API_VERSION + "/data/pt_BR/champion/%s.json";
    
    // Mapeamento de ícones por classe
    private static final Map<String, String> CLASS_ICONS = Map.of(
        "fighter", "🗡️",
        "tank", "🛡️",
        "mage", "🔮",
        "assassin", "🗡️",
        "marksman", "🏹",
        "support", "✨"
    );
    
    /**
     * Sincroniza todos os campeões da API da Riot com o banco de dados
     * Busca dados detalhados de habilidades para calcular dano real
     */
    public List<Champion> syncAllChampions() {
        log.info("🔄 Iniciando sincronização de campeões com dados REAIS de habilidades...");
        
        try {
            // Busca lista de todos os campeões
            String response = restTemplate.getForObject(CHAMPIONS_URL, String.class);
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode championsNode = rootNode.get("data");
            
            List<Champion> syncedChampions = new ArrayList<>();
            int count = 0;
            
            for (var entry : (Iterable<Map.Entry<String, JsonNode>>) () -> championsNode.fields()) {
                try {
                    String championKey = entry.getKey();
                    count++;
                    
                    log.info("📥 [{}/{}] Processando: {}", count, championsNode.size(), championKey);
                    
                    // Busca detalhes completos do campeão (com habilidades)
                    String detailUrl = String.format(CHAMPION_DETAIL_URL, championKey);
                    String detailResponse = restTemplate.getForObject(detailUrl, String.class);
                    JsonNode detailRoot = objectMapper.readTree(detailResponse);
                    JsonNode championDetail = detailRoot.get("data").get(championKey);
                    
                    RiotChampionDTO riotChampion = objectMapper.treeToValue(championDetail, RiotChampionDTO.class);
                    
                    // Busca ou cria o campeão
                    Champion champion = championRepository.findByName(riotChampion.getName())
                            .orElse(new Champion());
                    
                    // Atualiza dados básicos
                    updateChampionFromRiotData(champion, riotChampion);
                    
                    // Calcula dano REAL baseado nas habilidades
                    calculateRealDamageFromSpells(champion, championDetail);
                    
                    // Salva no banco
                    Champion saved = championRepository.save(champion);
                    syncedChampions.add(saved);
                    
                    log.info("✅ {} - Físico: {}, Mágico: {}, Tank: {}, CC: {}", 
                            saved.getName(), saved.getPhysicalDamage(), saved.getMagicDamage(), 
                            saved.getTankiness(), saved.getCrowdControl());
                    
                    // Pequeno delay para não sobrecarregar a API
                    Thread.sleep(50);
                    
                } catch (Exception e) {
                    log.error("❌ Erro ao processar campeão: {}", entry.getKey(), e);
                }
            }
            
            log.info("✅ Sincronização concluída! {} campeões atualizados com dados REAIS.", syncedChampions.size());
            return syncedChampions;
            
        } catch (Exception e) {
            log.error("❌ Erro ao sincronizar campeões da API da Riot", e);
            throw new RuntimeException("Falha ao sincronizar com API da Riot: " + e.getMessage());
        }
    }
    
    /**
     * Calcula dano real baseado nas habilidades do campeão (Q, W, E, R)
     * Analisa dano base, scaling AP/AD, e tipo de dano
     */
    private void calculateRealDamageFromSpells(Champion champion, JsonNode championDetail) {
        try {
            JsonNode spells = championDetail.get("spells");
            if (spells == null || !spells.isArray()) {
                return;
            }
            
            double totalPhysicalDamage = 0;
            double totalMagicDamage = 0;
            double totalAdScaling = 0;
            double totalApScaling = 0;
            int ccCount = 0;
            
            // Analisa cada habilidade (Q, W, E, R)
            for (JsonNode spell : spells) {
                String description = spell.get("description") != null ? 
                        spell.get("description").asText().toLowerCase() : "";
                
                // Detecta tipo de dano pela descrição
                boolean hasPhysicalDamage = description.contains("physical damage") || 
                                           description.contains("dano físico") ||
                                           description.contains("attack damage");
                                           
                boolean hasMagicDamage = description.contains("magic damage") || 
                                        description.contains("dano mágico") ||
                                        description.contains("ability power");
                
                // Detecta scaling
                boolean hasAdScaling = description.contains("bonus attack damage") ||
                                      description.contains("total attack damage") ||
                                      description.matches(".*\\d+%.*attack damage.*");
                                      
                boolean hasApScaling = description.contains("ability power") ||
                                      description.matches(".*\\d+%.*ap.*");
                
                // Detecta CC
                boolean hasCc = description.contains("stun") || description.contains("atordoa") ||
                               description.contains("root") || description.contains("enraíza") ||
                               description.contains("slow") || description.contains("lentidão") ||
                               description.contains("knock") || description.contains("derruba") ||
                               description.contains("charm") || description.contains("encanta") ||
                               description.contains("fear") || description.contains("medo") ||
                               description.contains("taunt") || description.contains("provoca") ||
                               description.contains("suppress") || description.contains("suprime");
                
                if (hasCc) ccCount++;
                
                // Pontuação baseada no tipo de dano
                if (hasPhysicalDamage) totalPhysicalDamage += hasAdScaling ? 2.5 : 2.0;
                if (hasMagicDamage) totalMagicDamage += hasApScaling ? 2.5 : 2.0;
                if (hasAdScaling) totalAdScaling += 1.0;
                if (hasApScaling) totalApScaling += 1.0;
            }
            
            // Stats base do campeão
            JsonNode stats = championDetail.get("stats");
            if (stats != null) {
                double baseAd = stats.has("attackdamage") ? stats.get("attackdamage").asDouble() : 50;
                double attackSpeed = stats.has("attackspeed") ? stats.get("attackspeed").asDouble() : 0.6;
                
                // Se tem scaling AD, aumenta dano físico
                if (totalAdScaling > 0) {
                    totalPhysicalDamage += (baseAd / 20) * totalAdScaling;
                }
                
                // Adiciona dano de auto-attacks
                totalPhysicalDamage += (attackSpeed * 2);
            }
            
            // Normaliza para escala 0-10
            int physicalScore = Math.min(10, (int) Math.round(totalPhysicalDamage));
            int magicScore = Math.min(10, (int) Math.round(totalMagicDamage));
            int ccScore = Math.min(10, ccCount * 2 + 2);
            
            // Se ambos são baixos, usa valores da Info da Riot como fallback
            if (physicalScore < 3 && magicScore < 3) {
                // Mantém valores originais do info
                return;
            }
            
            champion.setPhysicalDamage(physicalScore);
            champion.setMagicDamage(magicScore);
            champion.setCrowdControl(ccScore);
            
        } catch (Exception e) {
            log.warn("⚠️ Não foi possível calcular dano real para {}, usando valores padrão", champion.getName());
        }
    }
    
    /**
     * Atualiza um Champion com dados da API da Riot
     */
    private void updateChampionFromRiotData(Champion champion, RiotChampionDTO riotData) {
        champion.setName(riotData.getName());
        champion.setChampionClass(riotData.getChampionClass());
        champion.setIcon(CLASS_ICONS.getOrDefault(riotData.getChampionClass(), "⚔️"));
        
        // Mapeia dados de Info para stats do nosso sistema
        if (riotData.getInfo() != null) {
            RiotChampionDTO.Info info = riotData.getInfo();
            
            // Dano físico (attack da Riot, escala 0-10)
            champion.setPhysicalDamage(info.getAttack());
            
            // Dano mágico (magic da Riot, escala 0-10)
            champion.setMagicDamage(info.getMagic());
            
            // Tankiness (defense da Riot, escala 0-10)
            champion.setTankiness(info.getDefense());
            
            // Crowd Control baseado em classe e características
            champion.setCrowdControl(calculateCrowdControl(riotData));
        }
        
        // Define lane primária
        String primaryLane = riotData.getPrimaryLane();
        champion.setLanes(primaryLane);
        
        // Define role baseada na classe
        champion.setRole(getRoleFromClass(riotData.getChampionClass()));
    }
    
    /**
     * Calcula o nível de crowd control baseado nas características do campeão
     */
    private int calculateCrowdControl(RiotChampionDTO riotData) {
        String championClass = riotData.getChampionClass();
        
        // Valores base por classe
        return switch (championClass) {
            case "tank", "support" -> 8; // Tanks e supports geralmente têm muito CC
            case "fighter" -> 6;          // Fighters têm CC moderado
            case "mage" -> 7;             // Mages têm bom CC
            case "assassin" -> 4;         // Assassinos têm pouco CC
            case "marksman" -> 3;         // Marksmen têm pouco CC
            default -> 5;
        };
    }
    
    /**
     * Determina a role baseada na classe do campeão
     */
    private String getRoleFromClass(String championClass) {
        return switch (championClass) {
            case "fighter" -> "Lutador";
            case "tank" -> "Tank";
            case "mage" -> "Mago";
            case "assassin" -> "Assassino";
            case "marksman" -> "Atirador";
            case "support" -> "Suporte";
            default -> "Lutador";
        };
    }
    
    /**
     * Busca a versão mais recente da API Data Dragon
     */
    public String getLatestVersion() {
        try {
            String versionsUrl = "https://ddragon.leagueoflegends.com/api/versions.json";
            String[] versions = restTemplate.getForObject(versionsUrl, String[].class);
            
            if (versions != null && versions.length > 0) {
                log.info("📌 Versão mais recente da API: {}", versions[0]);
                return versions[0];
            }
            
            return RIOT_API_VERSION;
        } catch (Exception e) {
            log.warn("⚠️ Não foi possível obter versão mais recente, usando: {}", RIOT_API_VERSION);
            return RIOT_API_VERSION;
        }
    }
}
