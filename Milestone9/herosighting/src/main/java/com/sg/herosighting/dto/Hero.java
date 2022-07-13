/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.herosighting.dto;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author adrees
 */
public class Hero {

    private int heroId;
    private String heroName;
    private String description;
    private Boolean isVillian;
    private List<Superpower> superpower;

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsVillian() {
        return isVillian;
    }

    public void setIsVillian(Boolean isVillian) {
        this.isVillian = isVillian;
    }

    public List<Superpower> getSuperpower() {
        return superpower;
    }

    public void setSuperpower(List<Superpower> superpower) {
        this.superpower = superpower;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + this.heroId;
        hash = 73 * hash + Objects.hashCode(this.heroName);
        hash = 73 * hash + Objects.hashCode(this.description);
        hash = 73 * hash + Objects.hashCode(this.isVillian);
        hash = 73 * hash + Objects.hashCode(this.superpower);
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
        final Hero other = (Hero) obj;
        if (this.heroId != other.heroId) {
            return false;
        }
        if (!Objects.equals(this.heroName, other.heroName)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.isVillian, other.isVillian)) {
            return false;
        }
        return Objects.equals(this.superpower, other.superpower);
    }
    
}
