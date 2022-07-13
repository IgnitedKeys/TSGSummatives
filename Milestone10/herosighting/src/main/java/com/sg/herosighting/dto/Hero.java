/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.herosighting.dto;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author adrees
 */
public class Hero {

    private int heroId;
    
    @NotBlank(message = "hero name can not be empty")
    @Size(max=30, message="name must be less than 30 characters")
    private String heroName;
    
    @NotBlank(message="description can not be empty")
    @Size(max = 255, message="description must be less than 255 characters")
    private String description;
    
    private Boolean isVillain;
    
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

    public Boolean getIsVillain() {
        return isVillain;
    }

    public void setIsVillain(Boolean isVillain) {
        this.isVillain = isVillain;
    }

    public List<Superpower> getSuperpower() {
        return superpower;
    }

    public void setSuperpower(List<Superpower> superpower) {
        this.superpower = superpower;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.heroId;
        hash = 37 * hash + Objects.hashCode(this.heroName);
        hash = 37 * hash + Objects.hashCode(this.description);
        hash = 37 * hash + Objects.hashCode(this.isVillain);
        hash = 37 * hash + Objects.hashCode(this.superpower);
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
        if (!Objects.equals(this.isVillain, other.isVillain)) {
            return false;
        }
        return Objects.equals(this.superpower, other.superpower);
    }

    
    
}
