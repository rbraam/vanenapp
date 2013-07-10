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

/**
 *
 * @author Roy Braam
 */
public enum Kyu{
    KYU_8(0,"8e Kyu"),
    KYU_7(1,"7e Kyu"),
    KYU_6(2,"6e Kyu"),
    KYU_5(3,"5e Kyu"),
    KYU_4(4,"4e Kyu"),
    KYU_3(5,"3e Kyu"),
    KYU_2(6,"2e Kyu"),
    KYU_1(7,"1e Kyu");
    
    private int id;
    private String description;
    
    Kyu(int id, String description){
        this.id=id;
        this.description=description;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and setters">
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    //</editor-fold>
}
