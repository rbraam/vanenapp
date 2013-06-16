/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.entity;

/**
 *
 * @author Roy Braam
 */
public class Participant {
    private Long id;
    private Karateka karateka;
    private Vanencompetition vanencompetition;
    private Poule poule;
    private Integer points;

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
    //</editor-fold>
}