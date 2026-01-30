package com.lol.teambuilder.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "team_compositions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamComposition {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String description;
    
    // Time Azul
    private Long blueTop;
    private Long blueJungle;
    private Long blueMid;
    private Long blueAdc;
    private Long blueSupport;
    
    // Time Vermelho
    private Long redTop;
    private Long redJungle;
    private Long redMid;
    private Long redAdc;
    private Long redSupport;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
