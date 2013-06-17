/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.roybraam.vanenapp.entity;

import java.security.Principal;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

/**
 *
 * @author Roy
 */
@Entity
public class User implements Principal{
    @Id
    private Integer id;
    private String naam;
    private String gebruikersnaam;
    private String wachtwoord;    
    @ManyToOne
    private Organisation organisation;
    
    @ElementCollection
    @JoinTable(name="user_role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    
    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
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
    public String getName() {
        return naam;
    }

    public Set getRoles() {
        return roles;
    }

    public void setRoles(Set roles) {
        this.roles = roles;
    }

    public String toString(){
        return getNaam();

    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

}
