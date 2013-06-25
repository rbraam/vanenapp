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

import java.security.Principal;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Roy
 */
@Entity
@Table(name ="user_")
public class User implements Principal{
    @Id
    private Integer id;
    private String name;
    private String username;
    private String password;    
    @ManyToOne
    private Organisation organisation;
    /*
     * 
    @JoinTable(name="user_groups", joinColumns=@JoinColumn(name="username"), inverseJoinColumns=@JoinColumn(name="group_"))
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name="user_role", joinColumns = @JoinColumn(name="user_"))
    @Enumerated(value = EnumType.STRING)
    private Set<Role> roles;

    
    

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
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
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean checkRole(String role) {
        if(roles == null) {
            return false;
        }
        for (Role theUserroles : roles) {
            if (role.equalsIgnoreCase(theUserroles.name())) {
                return true;
            }
        }
        return false;
    }
    
    public Set getRoles() {
        return roles;
    }
    
    public void setRoles(Set roles) {
        this.roles = roles;
    }
    
    public Organisation getOrganisation() {
        return organisation;
    }
    
    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }
    
    @Override
    public String toString(){
        return getName();
        
    }
    //</editor-fold>
}
