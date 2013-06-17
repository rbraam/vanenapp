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
public class Poule {
    @Id
    private long id;
    
    @Enumerated(EnumType.STRING)
    private Kyu startKyu;    
    
    @Enumerated(EnumType.STRING)
    private Kyu endKyu;
    private int startAge;
    private int endAge;
    
    @Enumerated(EnumType.STRING)
    private CompetitionType type;

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public Kyu getStartKyu() {
        return startKyu;
    }
    
    public void setStartKyu(Kyu startKyu) {
        this.startKyu = startKyu;
    }
    
    public Kyu getEndKyu() {
        return endKyu;
    }
    
    public void setEndKyu(Kyu endKyu) {
        this.endKyu = endKyu;
    }
    
    public int getStartAge() {
        return startAge;
    }
    
    public void setStartAge(int startAge) {
        this.startAge = startAge;
    }
    
    public int getEndAge() {
        return endAge;
    }
    
    public void setEndAge(int endAge) {
        this.endAge = endAge;
    }
    
    public CompetitionType getType() {
        return type;
    }

    public void setType(CompetitionType type) {
        this.type = type;
    }
    //</editor-fold>

}
