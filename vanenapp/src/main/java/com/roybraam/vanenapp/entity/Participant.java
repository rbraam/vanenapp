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

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Roy Braam
 */
@Entity
public class Participant {
    @Id
    private Long id;
    @ManyToOne
    private Karateka karateka;
    @ManyToOne
    private Vanencompetition vanencompetition;
    @ManyToOne
    private Poule poule;
    private Integer points;
    @Enumerated(EnumType.STRING)
    private CompetitionType type;
    
    public Participant(){
    }

    public Participant(Vanencompetition vanencompetition, Karateka k) {
        this.karateka = k;
        this.vanencompetition=vanencompetition;
    }
    
    public String toString(){
        return this.karateka.toString();
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Karateka getKarateka() {
        return karateka;
    }
    
    public void setKarateka(Karateka karateka) {
        this.karateka = karateka;
    }
    
    public Vanencompetition getVanencompetition() {
        return vanencompetition;
    }
    
    public void setVanencompetition(Vanencompetition vanencompetition) {
        this.vanencompetition = vanencompetition;
    }
    
    public Poule getPoule() {
        return poule;
    }
    
    public void setPoule(Poule poule) {
        this.poule = poule;
    }
    
    public Integer getPoints() {
        return points;
    }
    
    public void setPoints(Integer points) {
        this.points = points;
    }
    public CompetitionType getType() {
        return type;
    }

    public void setType(CompetitionType type) {
        this.type = type;
    }
}
    //</editor-fold>
