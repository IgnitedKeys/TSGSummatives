/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sg.herosighting.dao;

import com.sg.herosighting.dto.Hero;
import com.sg.herosighting.dto.Organization;
import java.util.List;

/**
 *
 * @author adrees
 */
public interface OrganizationDao {

    Organization getOrganizationById(int id);

    List<Organization> getAllOrganizations();

    Organization addOrganization(Organization organization);

    void updateOrganization(Organization organization);

    void deleteOrganizationById(int id);

    //get list of members in organization
    List<Hero> getHeroesInOrganization(Organization organization);

    //get list of organizations a hero belongs to
    List<Organization> getOrganizationsForHero(Hero hero);
}
