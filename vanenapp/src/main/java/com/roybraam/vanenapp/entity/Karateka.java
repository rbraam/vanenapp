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

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.swing.text.DateFormatter;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

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
    private Double weight;
    private String memberNumber;
    private String emailAdress;
    private String club;
    
    private Integer basePointsKata = new Integer(0);    
    private Integer basePointsKumite = new Integer(0);    
    
    private static SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
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
    
    public Integer getBasePointsKata() {
        return basePointsKata;
    }

    public void setBasePointsKata(Integer basePointsKata){
        this.basePointsKata = basePointsKata;
    }
    
    public Integer getBasePointsKumite() {
        return basePointsKumite;
    }

    public void setBasePointsKumite(Integer basePointsKumite){
        this.basePointsKumite = basePointsKumite;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }
    //</editor-fold>
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.surname);
        if (this.name!=null){
            sb.append(", ").append(this.name);
        }if(this.insert!=null){
            sb.append(" ").append(this.insert);
        }if (this.belt!=null){
            sb.append(" ").append(this.belt.getDescription());
        }if (this.birthdate!=null){
            sb.append(" ").append(df.format(birthdate));
        }if (this.weight!=null){
            sb.append(" ").append(this.weight+" kg");
        }
        return sb.toString();
    }

    public JSONObject toJSON() throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter s = new StringWriter();
        mapper.writeValue(s, this);
        return new JSONObject(s.toString());
    }

    public String getFullName() {
        String result = this.getName();
        if (this.insert!=null){
            result+=" "+this.insert;
        }
        result+=" "+this.getSurname();
        return result;
    }

}
