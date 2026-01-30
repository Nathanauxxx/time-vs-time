package com.lol.teambuilder.controller;

import com.lol.teambuilder.model.Champion;
import com.lol.teambuilder.service.ChampionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/champions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ChampionController {
    
    private final ChampionService championService;
    
    @GetMapping
    public ResponseEntity<List<Champion>> getAllChampions() {
        return ResponseEntity.ok(championService.getAllChampions());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Champion> getChampionById(@PathVariable Long id) {
        return ResponseEntity.ok(championService.getChampionById(id));
    }
    
    @GetMapping("/class/{championClass}")
    public ResponseEntity<List<Champion>> getChampionsByClass(@PathVariable String championClass) {
        return ResponseEntity.ok(championService.getChampionsByClass(championClass));
    }
    
    @GetMapping("/lane/{lane}")
    public ResponseEntity<List<Champion>> getChampionsByLane(@PathVariable String lane) {
        return ResponseEntity.ok(championService.getChampionsByLane(lane));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Champion>> searchChampions(@RequestParam String name) {
        return ResponseEntity.ok(championService.searchChampions(name));
    }
    
    @PostMapping
    public ResponseEntity<Champion> createChampion(@RequestBody Champion champion) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(championService.createChampion(champion));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Champion> updateChampion(
            @PathVariable Long id, 
            @RequestBody Champion champion) {
        return ResponseEntity.ok(championService.updateChampion(id, champion));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChampion(@PathVariable Long id) {
        championService.deleteChampion(id);
        return ResponseEntity.noContent().build();
    }
}
