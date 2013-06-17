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

/**
 *
 * @author Roy Braam
 */
@Entity
public class Poule {
    @Id
    private long id;
    
    @Enumerated(EnumType.STRING)
    private Kyu startKyu;    
    
    @Enumerated(EnumType.STRING)
    private Kyu endKyu;
    private int startAge;
    private int endAge;
    
    @Enumerated(EnumType.STRING)
    private CompetitionType type;

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
    
    public CompetitionType getType() {
        return type;
    }

    public void setType(CompetitionType type) {
        this.type = type;
    }
    //</editor-fold>

}
