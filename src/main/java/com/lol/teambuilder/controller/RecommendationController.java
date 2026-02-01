package com.lol.teambuilder.controller;

import com.lol.teambuilder.service.TeamRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recommendations")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RecommendationController {
    
    private final TeamRecommendationService recommendationService;
    
    @PostMapping("/analyze")
    public ResponseEntity<Map<String, Object>> analyzeAndRecommend(
            @RequestBody Map<String, Object> request) {
        
        @SuppressWarnings("unchecked")
        List<Long> championIds = (List<Long>) request.get("championIds");
        String lane = (String) request.get("lane");
        
        Map<String, Object> result = recommendationService.analyzeTeamAndRecommend(championIds, lane);
        
        return ResponseEntity.ok(result);
    }
}
