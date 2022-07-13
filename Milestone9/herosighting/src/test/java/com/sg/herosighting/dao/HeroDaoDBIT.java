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
public class HeroDaoDBIT {

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

    public HeroDaoDBIT() {
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
     * Test of getHeroById method and addHero method, of class HeroDaoDB.
     */
    @Test
    public void testAddAndGetHeroById() {

        Superpower power = new Superpower();
        power.setSuperpowerName("flight");
        superpowerDao.addSuperpower(power);

        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(power);

        Hero hero = new Hero();
        hero.setHeroName("Test Name");
        hero.setIsVillian(true);
        hero.setDescription("Test description");
        hero.setSuperpower(superpowers);
        hero = heroDao.addHero(hero);

        Hero fromDao = heroDao.getHeroById(hero.getHeroId());

        assertEquals(hero, fromDao);
    }

    /**
     * Test of getAllHeroes method, of class HeroDaoDB.
     */
    @Test
    public void testGetAllHeroes() {
        Superpower power = new Superpower();
        power.setSuperpowerName("flight");
        power = superpowerDao.addSuperpower(power);

        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(power);

        Superpower superpower2 = new Superpower();
        superpower2.setSuperpowerName("Test power");
        superpower2 = superpowerDao.addSuperpower(superpower2);

        List<Superpower> superpowers2 = new ArrayList<>();
        superpowers2.add(power);
        superpowers2.add(superpower2);

        Hero hero = new Hero();
        hero.setHeroName("Test Name");
        hero.setIsVillian(true);
        hero.setDescription("Test description");
        hero.setSuperpower(superpowers);
        hero = heroDao.addHero(hero);

        Hero hero2 = new Hero();
        hero2.setHeroName("Test Name 2");
        hero2.setIsVillian(false);
        hero2.setDescription("Test Description 2");
        hero2.setSuperpower(superpowers2);
        hero2 = heroDao.addHero(hero2);

        List<Hero> heroes = heroDao.getAllHeroes();

        assertEquals(2, heroes.size());
        assertTrue(heroes.contains(hero));
        assertTrue(heroes.contains(hero2));
    }

    /**
     * Test of updateHero method, of class HeroDaoDB.
     */
    @Test
    public void testUpdateHero() {
        Superpower power = new Superpower();
        power.setSuperpowerName("flight");
        power = superpowerDao.addSuperpower(power);

        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(power);

        Hero hero = new Hero();
        hero.setHeroName("Test Name");
        hero.setIsVillian(true);
        hero.setDescription("Test description");
        hero.setSuperpower(superpowers);
        hero = heroDao.addHero(hero);

        Hero fromDao = heroDao.getHeroById(hero.getHeroId());

        assertEquals(hero, fromDao);

        hero.setHeroName("New Test Name");
        Superpower power2 = new Superpower();
        power2.setSuperpowerName("super strength");
        power2 = superpowerDao.addSuperpower(power2);

        superpowers.add(power2);
        hero.setSuperpower(superpowers);

        heroDao.updateHero(hero);

        assertNotEquals(hero, fromDao);

        fromDao = heroDao.getHeroById(hero.getHeroId());
        assertEquals(hero, fromDao);
    }

    /**
     * Test of deleteHeroById method, of class HeroDaoDB.
     */
    @Test
    public void testDeleteHeroById() {
        Superpower power = new Superpower();
        power.setSuperpowerName("flight");
        power = superpowerDao.addSuperpower(power);

        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(power);

        Hero hero = new Hero();
        hero.setHeroName("Test Name");
        hero.setIsVillian(true);
        hero.setDescription("Test description");
        hero.setSuperpower(superpowers);
        hero = heroDao.addHero(hero);

        Hero fromDao = heroDao.getHeroById(hero.getHeroId());

        assertEquals(hero, fromDao);

        heroDao.deleteHeroById(hero.getHeroId());

        fromDao = heroDao.getHeroById(hero.getHeroId());
        assertNull(fromDao);
    }

}
