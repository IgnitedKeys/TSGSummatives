/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.sg.herosighting.dao;

import com.sg.herosighting.dto.Location;
import com.sg.herosighting.dto.Hero;
import com.sg.herosighting.dto.Organization;
import com.sg.herosighting.dto.Sighting;
import com.sg.herosighting.dto.Superpower;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author adrees
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SuperpowerDaoDBIT {

    @Autowired
    LocationDao locationDao;

    @Autowired
    HeroDao heroDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    SuperpowerDao superpowerDao;

    public SuperpowerDaoDBIT() {
    }

    @BeforeEach
    public void setUp() {
        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations) {
            locationDao.deleteLocationById(location.getLocationId());
        }

        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getSightingId());
        }

        List<Organization> organizations = organizationDao.getAllOrganizations();
        for (Organization organization : organizations) {
            organizationDao.deleteOrganizationById(organization.getOrganizationId());
        }

        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        for (Superpower superpower : superpowers) {
            superpowerDao.deleteSuperpowerById(superpower.getSuperpowerId());
        }

        List<Hero> heroes = heroDao.getAllHeroes();
        for (Hero hero : heroes) {
            heroDao.deleteHeroById(hero.getHeroId());
        }
    }

    /**
     * Test of getSuperpowerById method and addSuperpower method, of class
     * SuperpowerDaoDB.
     */
    @Test
    public void testAddAndGetSuperpowerById() {
        Superpower power = new Superpower();
        power.setSuperpowerName("Test power name");
        power = superpowerDao.addSuperpower(power);

        Superpower fromDao = superpowerDao.getSuperpowerById(power.getSuperpowerId());

        assertEquals(power, fromDao);
    }

    /**
     * Test of getAllSuperpowers method, of class SuperpowerDaoDB.
     */
    @Test
    public void testGetAllSuperpowers() {
        Superpower power = new Superpower();
        power.setSuperpowerName("Test power name");
        power = superpowerDao.addSuperpower(power);

        Superpower power2 = new Superpower();
        power2.setSuperpowerName("Test power name 2");
        power2 = superpowerDao.addSuperpower(power2);

        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();

        assertEquals(2, superpowers.size());
        assertTrue(superpowers.contains(power));
        assertTrue(superpowers.contains(power2));

    }

    /**
     * Test of deleteSuperpowerById method, of class SuperpowerDaoDB.
     */
    @Test
    public void testDeleteSuperpowerById() {
        Superpower power = new Superpower();
        power.setSuperpowerName("Test power name");
        power = superpowerDao.addSuperpower(power);

        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(power);

        Hero hero = new Hero();
        hero.setHeroName("Test Hero Name");
        hero.setDescription("Test Hero Description");
        hero.setIsVillain(false);
        hero.setSuperpower(superpowers);
        hero = heroDao.addHero(hero);

        Superpower fromDao = superpowerDao.getSuperpowerById(power.getSuperpowerId());

        assertEquals(power, fromDao);

        superpowerDao.deleteSuperpowerById(power.getSuperpowerId());

        fromDao = superpowerDao.getSuperpowerById(power.getSuperpowerId());
        assertNull(fromDao);
    }

    /**
     * Test of updateSuperpower method, of class SuperpowerDaoDB.
     */
    @Test
    public void testUpdateSuperpower() {
        Superpower power = new Superpower();
        power.setSuperpowerName("Test power name");
        power = superpowerDao.addSuperpower(power);

        Superpower fromDao = superpowerDao.getSuperpowerById(power.getSuperpowerId());
        assertEquals(power, fromDao);

        power.setSuperpowerName("New test name");
        superpowerDao.updateSuperpower(power);

        assertNotEquals(power, fromDao);

        fromDao = superpowerDao.getSuperpowerById(power.getSuperpowerId());

        assertEquals(fromDao, power);
    }

}
