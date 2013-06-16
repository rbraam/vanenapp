/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.entity;

/**
 *
 * @author Roy Braam
 */
public enum Kyu{
    KYU_8(0),
    KYU_7(1),
    KYU_6(2),
    KYU_5(3),
    KYU_4(4),
    KYU_3(5),
    KYU_2(6),
    KYU_1(7);
    
    public final int id;
    
    Kyu(int id){
        this.id=id;
    }
}
