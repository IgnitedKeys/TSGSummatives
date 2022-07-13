/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.herosighting.dto;

import java.util.Objects;

/**
 *
 * @author adrees
 */
public class Superpower {
    
    private int superpowerId;
    private String superpowerName;

    public int getSuperpowerId() {
        return superpowerId;
    }

    public void setSuperpowerId(int superpowerId) {
        this.superpowerId = superpowerId;
    }

    public String getSuperpowerName() {
        return superpowerName;
    }

    public void setSuperpowerName(String superpowerName) {
        this.superpowerName = superpowerName;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.superpowerId;
        hash = 89 * hash + Objects.hashCode(this.superpowerName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Superpower other = (Superpower) obj;
        if (this.superpowerId != other.superpowerId) {
            return false;
        }
        return Objects.equals(this.superpowerName, other.superpowerName);
    }
    
    
}
