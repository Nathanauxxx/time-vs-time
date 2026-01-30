package com.lol.teambuilder.controller;

import com.lol.teambuilder.dto.TeamAnalysisDTO;
import com.lol.teambuilder.service.TeamAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TeamAnalysisController {
    
    private final TeamAnalysisService teamAnalysisService;
    
    @PostMapping("/team")
    public ResponseEntity<TeamAnalysisDTO> analyzeTeam(@RequestBody List<Long> championIds) {
        return ResponseEntity.ok(teamAnalysisService.analyzeTeam(championIds));
    }
}
