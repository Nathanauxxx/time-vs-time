package com.lol.teambuilder.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "champions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Champion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(nullable = true)
    private String role;
    
    @Column(nullable = false)
    private String championClass;
    
    @Column(nullable = true)
    private String icon;
    
    @Column(nullable = false)
    private Integer physicalDamage;
    
    @Column(nullable = false)
    private Integer magicDamage;
    
    @Column(nullable = false)
    private Integer tankiness;
    
    @Column(nullable = false)
    private Integer crowdControl;
    
    @Column(nullable = false)
    private String lanes; // Separado por vírgulas: "top,mid"
    
    public Champion(String name, String role, String championClass, String icon,
                   Integer physicalDamage, Integer magicDamage, Integer tankiness,
                   Integer crowdControl, String lanes) {
        this.name = name;
        this.role = role;
        this.championClass = championClass;
        this.icon = icon;
        this.physicalDamage = physicalDamage;
        this.magicDamage = magicDamage;
        this.tankiness = tankiness;
        this.crowdControl = crowdControl;
        this.lanes = lanes;
    }
}
