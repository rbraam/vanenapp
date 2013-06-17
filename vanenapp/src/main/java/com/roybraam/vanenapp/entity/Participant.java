/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Roy Braam
 */
@Entity
public class Participant {
    @Id
    private Long id;
    @ManyToOne
    private Karateka karateka;
    @ManyToOne
    private Vanencompetition vanencompetition;
    @ManyToOne
    private Poule poule;
    private Integer points;
    @Enumerated(EnumType.STRING)
    private CompetitionType type;

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Karateka getKarateka() {
        return karateka;
    }
    
    public void setKarateka(Karateka karateka) {
        this.karateka = karateka;
    }
    
    public Vanencompetition getVanencompetition() {
        return vanencompetition;
    }
    
    public void setVanencompetition(Vanencompetition vanencompetition) {
        this.vanencompetition = vanencompetition;
    }
    
    public Poule getPoule() {
        return poule;
    }
    
    public void setPoule(Poule poule) {
        this.poule = poule;
    }
    
    public Integer getPoints() {
        return points;
    }
    
    public void setPoints(Integer points) {
        this.points = points;
    }
    public CompetitionType getType() {
        return type;
    }

    public void setType(CompetitionType type) {
        this.type = type;
    }
}
    //</editor-fold>
