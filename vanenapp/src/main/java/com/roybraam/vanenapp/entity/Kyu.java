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

import org.codehaus.jackson.annotate.JsonValue;

/**
 *
 * @author Roy Braam
 */
public enum Kyu{
    KYU_8(0,"8e Kyu", "Wit"),
    KYU_7(1,"7e Kyu", "Geel"),
    KYU_6(2,"6e Kyu", "Oranje"),
    KYU_5(3,"5e Kyu", "Groen"),
    KYU_4(4,"4e Kyu", "Blauw"),
    KYU_3(5,"3e Kyu", "Bruin"),
    KYU_2(6,"2e Kyu", "Bruin"),
    KYU_1(7,"1e Kyu", "Bruin");
    
    private int id;
    private String description;
    private String color;
    
    Kyu(int id, String description, String color){
        this.id=id;
        this.description=description;
        this.color=color;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and setters">
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @JsonValue
    public final String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    //</editor-fold>

}
