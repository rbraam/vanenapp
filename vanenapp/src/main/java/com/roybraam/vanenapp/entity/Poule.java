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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Roy Braam
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Poule {
    @Id
    private long id;
    
    private String name;
    
    @Enumerated(EnumType.STRING)
    private Kyu startKyu;    
    @Enumerated(EnumType.STRING)
    private Kyu endKyu;
    
    private Integer startAge;
    private Integer endAge;
    
    @Column(insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private CompetitionType type;
    
    private String gender;
    @ManyToOne
    private Vanencompetition vanencompetition;
    
    @OneToMany(mappedBy = "poule")
    @JsonIgnore
    private List<Participant> participants = new ArrayList<Participant>();
    
    public Poule(){}
    
    public Poule(Poule p){
        this.id = p.getId();
        this.name= p.getName();
        this.startKyu = p.getStartKyu();
        this.endKyu = p.getEndKyu();
        this.startAge= p.getStartAge();
        this.endAge = p.getEndAge();
        this.type = p.getType();
        this.vanencompetition = p.getVanencompetition();
        this.participants = p.getParticipants();
        this.gender = p.getGender();
    }
    
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
    
    public Integer getStartAge() {
        return startAge;
    }
    
    public void setStartAge(Integer startAge) {
        this.startAge = startAge;
    }
    
    public Integer getEndAge() {
        return endAge;
    }
    
    public void setEndAge(Integer endAge) {
        this.endAge = endAge;
    }
    
    public CompetitionType getType() {
        return type;
    }

    public void setType(CompetitionType type) {
        this.type = type;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vanencompetition getVanencompetition() {
        return vanencompetition;
    }

    public void setVanencompetition(Vanencompetition vanencompetition) {
        this.vanencompetition = vanencompetition;
    }
    
    public String toString(){
        String s="";
        if (name!=null){
            s+= name+" (";
        }
        
        if (startKyu!=null){
            s+=" "+ startKyu.getColor();
        }
        if (endKyu!=null && endKyu!= startKyu){
            s+=" - "+endKyu.getColor();
        }
        if (startAge !=null){
            s+=" "+startAge;
        }
        if (endAge !=null && endAge!=startAge){
            s+=" t/m "+endAge;
        }
        if (startAge!=null){
            s+= " jaar";
        }
        if (name!=null){
            s+=")";
        }
        if (this.type!=null){
            s+= " "+this.type;
        }
        return s;
    }

    public List<Participant> getParticipants() {
        Collections.sort(participants);
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
    
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    //</editor-fold>

}
