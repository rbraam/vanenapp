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
