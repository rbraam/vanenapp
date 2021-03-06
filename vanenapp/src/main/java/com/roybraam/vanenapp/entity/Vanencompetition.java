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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Roy Braam
 */
@Entity
public class Vanencompetition {
    @OneToMany(mappedBy = "vanencompetition", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Poule> poules;
    @Id
    private long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date subscriptionEnd;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    @ManyToOne
    private Organisation organisation;
    private String location;
    @OneToMany(orphanRemoval=true, cascade=CascadeType.ALL, mappedBy="vanencompetition")
    @JsonIgnore
    private Set<Participant> participants = new HashSet<Participant>();
    
    
    public String getDatumString(){
        Date date=this.getDate();
        if (date==null){
            return null;
        }
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String datum=df.format(date);
        return datum;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public Date getSubscriptionEnd() {
        return subscriptionEnd;
    }
    
    public void setSubscriptionEnd(Date subscriptionEnd) {
        this.subscriptionEnd = subscriptionEnd;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public Organisation getOrganisation() {
        return organisation;
    }
    
    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public Set<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
    }
    //</editor-fold>

    public boolean isAllowed(User user) {
        if(user.checkRole(Role.SUPERADMIN.name())){
            return true;
        }
        if (getOrganisation().getId().equals(user.getOrganisation().getId())){
            return true;
        }
        return false;
    }
    
    public String toString(){
        return String.format("%s (%s)", location,getDatumString());
    }

}
