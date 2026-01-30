package com.lol.teambuilder.controller;

import com.lol.teambuilder.model.TeamComposition;
import com.lol.teambuilder.service.TeamCompositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compositions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TeamCompositionController {
    
    private final TeamCompositionService teamCompositionService;
    
    @GetMapping
    public ResponseEntity<List<TeamComposition>> getAllCompositions() {
        return ResponseEntity.ok(teamCompositionService.getAllCompositions());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TeamComposition> getCompositionById(@PathVariable Long id) {
        return ResponseEntity.ok(teamCompositionService.getCompositionById(id));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<TeamComposition>> searchCompositions(@RequestParam String name) {
        return ResponseEntity.ok(teamCompositionService.searchCompositions(name));
    }
    
    @PostMapping
    public ResponseEntity<TeamComposition> saveComposition(@RequestBody TeamComposition composition) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(teamCompositionService.saveComposition(composition));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComposition(@PathVariable Long id) {
        teamCompositionService.deleteComposition(id);
        return ResponseEntity.noContent().build();
    }
}
