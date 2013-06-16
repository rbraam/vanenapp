/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Roy Braam
 */
public class Vanencompetition {
    private long id;
    private Date subscriptionEnd;
    private Date date;
    private Organisation organisation;
    private String location;
    
    
    public String getDatumString(){
        Date date=this.getDate();
        if (date==null){
            return null;
        }
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String datum=df.format(date);
        return datum;
    }
    public String toString(){
        StringBuffer sb= new StringBuffer();
        if (getDatumString()!=null){
            sb.append(getDatumString());
            sb.append(" ");
        }
        if (getLocation()!=null){
            sb.append(getLocation());            
        }
       return sb.toString();
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
    //</editor-fold>
}
