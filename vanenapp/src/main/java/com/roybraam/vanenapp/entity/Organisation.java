/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Roy Braam
 */
@Entity
public class Organisation {
    @Id
    private Long id;
    private String naam;

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNaam() {
        return naam;
    }
    
    public void setNaam(String naam) {
        this.naam = naam;
    }
    //</editor-fold>
}
