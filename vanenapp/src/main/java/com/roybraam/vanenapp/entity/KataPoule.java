/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Roy Braam
 */
@Entity
@DiscriminatorValue(value = "KATA")
public class KataPoule extends Poule{
    
    public KataPoule(){
        super();
    }
    public KataPoule(Poule p){
        super(p);
    }
        
}
