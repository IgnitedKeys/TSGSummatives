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
public class Organization {
    
    private int organizationId;
    
    @NotBlank(message= "organization name can not be empty")
    @Size(max = 50, message="name must be less than 50 characters")
    private String organizationName;
    
    @NotBlank(message="description can not be empty")
    @Size(max = 255, message = "description must be less than 255 characters")
    private String organizationDescription;
    
    @NotBlank(message="contact can not be empty")
    @Size(max = 50, message="contact must be less than 50 characters")
    private String contact;
    
    @NotBlank(message = "address can not be empty")
    @Size(max = 50, message="address must be less than 50 characters")
    private String address;
    
    private List<Hero> heroes;

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationDescription() {
        return organizationDescription;
    }

    public void setOrganizationDescription(String organizationDescription) {
        this.organizationDescription = organizationDescription;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.organizationId;
        hash = 37 * hash + Objects.hashCode(this.organizationName);
        hash = 37 * hash + Objects.hashCode(this.organizationDescription);
        hash = 37 * hash + Objects.hashCode(this.contact);
        hash = 37 * hash + Objects.hashCode(this.address);
        hash = 37 * hash + Objects.hashCode(this.heroes);
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
        final Organization other = (Organization) obj;
        if (this.organizationId != other.organizationId) {
            return false;
        }
        if (!Objects.equals(this.organizationName, other.organizationName)) {
            return false;
        }
        if (!Objects.equals(this.organizationDescription, other.organizationDescription)) {
            return false;
        }
        if (!Objects.equals(this.contact, other.contact)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        return Objects.equals(this.heroes, other.heroes);
    }
    
    
}
