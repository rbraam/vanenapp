/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Roy Braam
 */
@Entity
@DiscriminatorValue(value = "KUMITE")
public class KumitePoule extends Poule{
    
    private Double startWeight;
    private Double endWeight;
    
    public KumitePoule(){
        super();
    }
    public KumitePoule(Poule p){
        super(p);
        if (p instanceof KumitePoule){
            this.startWeight = ((KumitePoule)p).getStartWeight();
            this.endWeight = ((KumitePoule)p).getEndWeight();
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters / Setters">
    public Double getStartWeight() {
        return startWeight;
    }
    
    public void setStartWeight(Double startWeight) {
        this.startWeight = startWeight;
    }
    
    public Double getEndWeight() {
        return endWeight;
    }
    
    public void setEndWeight(Double endWeight) {
        this.endWeight = endWeight;
    }
    //</editor-fold>
}
