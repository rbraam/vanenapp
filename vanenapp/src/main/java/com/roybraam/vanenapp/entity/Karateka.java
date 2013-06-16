/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.entity;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Roy Braam
 */
public class Karateka {    
    private Integer id;
    private String name;
    private String surname;
    private String insert;
    private Kyu belt;
    private String gender;
    private Date birthdata;
    private double weight;
    
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
    
    public String getSurname() {
        return surname;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public String getInsert() {
        return insert;
    }
    
    public void setInsert(String insert) {
        this.insert = insert;
    }
    
    public Kyu getBelt() {
        return belt;
    }
    
    public void setBelt(Kyu belt) {
        this.belt = belt;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public Date getBirthdata() {
        return birthdata;
    }
    
    public void setBirthdata(Date birthdata) {
        this.birthdata = birthdata;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    //</editor-fold>
    
     public int getAgeOnDate(Date vanenDate){
        if (vanenDate!=null){
            GregorianCalendar vanen= new GregorianCalendar();
            vanen.setTime(vanenDate);
            GregorianCalendar geb= new GregorianCalendar();
            geb.setTime(getBirthdata());
            int leeftijd= vanen.get(GregorianCalendar.YEAR)-geb.get(GregorianCalendar.YEAR);
            if (vanen.get(GregorianCalendar.MONTH)<geb.get(GregorianCalendar.MONTH)){
                leeftijd=leeftijd-1;
            }
            else if (vanen.get(GregorianCalendar.MONTH)==geb.get(GregorianCalendar.MONTH)){
                if (vanen.get(GregorianCalendar.DAY_OF_MONTH)<geb.get(GregorianCalendar.DAY_OF_MONTH)){
                    leeftijd=leeftijd-1;
                }
            }            
            return leeftijd;
        }else{
            return 0;
        }
    }
}
