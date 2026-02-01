package com.lol.teambuilder.controller;

import com.lol.teambuilder.model.Champion;
import com.lol.teambuilder.service.RiotApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller para operações relacionadas à API da Riot Games
 */
@RestController
@RequestMapping("/api/riot")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class RiotApiController {
    
    private final RiotApiService riotApiService;
    
    /**
     * Sincroniza todos os campeões da API da Riot com o banco de dados
     * 
     * @return Lista de campeões sincronizados
     */
    @PostMapping("/sync-champions")
    public ResponseEntity<Map<String, Object>> syncChampions() {
        log.info("📥 Recebida requisição para sincronizar campeões da API da Riot");
        
        try {
            List<Champion> champions = riotApiService.syncAllChampions();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Campeões sincronizados com sucesso!");
            response.put("total", champions.size());
            response.put("champions", champions);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("❌ Erro ao sincronizar campeões", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Erro ao sincronizar: " + e.getMessage());
            errorResponse.put("total", 0);
            
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * Obtém a versão mais recente da API Data Dragon
     * 
     * @return Versão mais recente
     */
    @GetMapping("/version")
    public ResponseEntity<Map<String, String>> getLatestVersion() {
        log.info("📌 Recebida requisição para obter versão da API");
        
        try {
            String version = riotApiService.getLatestVersion();
            
            Map<String, String> response = new HashMap<>();
            response.put("version", version);
            response.put("message", "Versão obtida com sucesso");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("❌ Erro ao obter versão", e);
            
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}
