/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.entity;

/**
 *
 * @author Roy Braam
 */
public enum Category {
    CATEGORY_C(0,"Categorie C"),
    CATEGORY_B(1, "Categorie B"),
    CATEGORY_A(2, "Categorie A"),
    CATEGORY_SUPER(3, "Categorie Super Vaan");
    
    private Integer id;
    private String name;
    
    Category(Integer id, String name){
        this.id = id;
        this.name=name;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
//</editor-fold>
