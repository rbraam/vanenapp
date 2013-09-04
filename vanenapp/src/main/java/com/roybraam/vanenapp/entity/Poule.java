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
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Roy Braam
 */
@Entity
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
    
    @Enumerated(EnumType.STRING)
    private CompetitionType type;

    @ManyToOne
    private Vanencompetition vanencompetition;
    
    @OneToMany(mappedBy = "poule" )
    @JsonIgnore
    private List<Participant> participants = new ArrayList<Participant>();
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
            s+= name+" ";
        }
        s+="(";
        if (startKyu!=null){
            s+= startKyu.getDescription();
        }
        if (endKyu!=null && endKyu!= startKyu){
            s+=" - "+endKyu.getDescription();
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
        s+=")";
        return s;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
    //</editor-fold>

}
