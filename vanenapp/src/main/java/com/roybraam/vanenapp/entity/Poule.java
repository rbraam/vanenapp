/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.entity;

/**
 *
 * @author Roy Braam
 */
public class Poule {
    private long id;
    private Kyu startKyu;
    private Kyu endKyu;
    private int startAge;
    private int endAge;

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
    //</editor-fold>
}
