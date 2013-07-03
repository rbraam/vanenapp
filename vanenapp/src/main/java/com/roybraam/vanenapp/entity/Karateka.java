/*
 * Copyright (C) 2013 Roy Braam
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.roybraam.vanenapp.entity;

import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author Roy Braam
 */
@Entity
public class Karateka {    
    @Id
    private Integer id;
    private String name;
    private String surname;
    private String insert;
    @Enumerated(EnumType.STRING)
    private Kyu belt;
    private String gender;    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birthdate;
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
    
    public Date getBirthdate() {
        return birthdate;
    }
    
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
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
            geb.setTime(getBirthdate());
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
