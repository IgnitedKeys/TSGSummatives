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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
public class SightingDaoDBIT {

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

    public SightingDaoDBIT() {
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
     * Test of getSightingById method and addSighting method, of class SightingDaoDB.
     */
    @Test
    public void testAddAndGetSightingById() {
        //hero and location
        Superpower superpower = new Superpower();
        superpower.setSuperpowerName("Test power");
        superpower = superpowerDao.addSuperpower(superpower);

        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);

        Hero hero = new Hero();
        hero.setHeroName("Test Hero Name");
        hero.setDescription("Test Hero Description");
        hero.setIsVillain(false);
        hero.setSuperpower(superpowers);
        hero = heroDao.addHero(hero);
        
        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location Description");
        location.setLatitude((long) 44.9537);
        location.setLongitude((long) 93.09);
        location = locationDao.addLocation(location);
        
        Sighting sighting = new Sighting();
        sighting.setSightingDate(LocalDate.now());
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getSightingId());
        assertEquals(sighting, fromDao);
        
    }

    /**
     * Test of getAllSightings method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightings() {
        Superpower superpower = new Superpower();
        superpower.setSuperpowerName("Test power");
        superpower = superpowerDao.addSuperpower(superpower);

        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);

        Hero hero = new Hero();
        hero.setHeroName("Test Hero Name");
        hero.setDescription("Test Hero Description");
        hero.setIsVillain(false);
        hero.setSuperpower(superpowers);
        hero = heroDao.addHero(hero);
        
        Hero hero2 = new Hero();
        hero2.setHeroName("Test Hero Name 2");
        hero2.setDescription("Test Description 2");
        hero2.setIsVillain(true);
        hero2.setSuperpower(superpowers);
        hero2 = heroDao.addHero(hero2);
        
        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location Description");
        location.setLatitude((long) 44.9537);
        location.setLongitude((long) 93.09);
        location = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setLocationName("Test Location Name");
        location2.setLocationDescription("Test Location Description");
        location2.setLatitude((long) 44.9537);
        location2.setLongitude((long) 93.09);
        location2 = locationDao.addLocation(location2);
        
        Sighting sighting = new Sighting();
        sighting.setSightingDate(LocalDate.now());
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting sighting2 = new Sighting();
        sighting2.setSightingDate(LocalDate.now());
        sighting2.setHero(hero2);
        sighting2.setLocation(location2);
        sighting2 = sightingDao.addSighting(sighting2);
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));
    }

    /**
     * Test of updateSighting method, of class SightingDaoDB.
     */
    @Test
    public void testUpdateSighting() {
        Superpower superpower = new Superpower();
        superpower.setSuperpowerName("Test power");
        superpower = superpowerDao.addSuperpower(superpower);

        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);

        Hero hero = new Hero();
        hero.setHeroName("Test Hero Name");
        hero.setDescription("Test Hero Description");
        hero.setIsVillain(false);
        hero.setSuperpower(superpowers);
        hero = heroDao.addHero(hero);
        
        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location Description");
        location.setLatitude((long) 44.9537);
        location.setLongitude((long) 93.09);
        location = locationDao.addLocation(location);
        
        Sighting sighting = new Sighting();
        sighting.setSightingDate(LocalDate.now());
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getSightingId());
        assertEquals(sighting, fromDao);
        
        Hero hero2 = new Hero();
        hero2.setHeroName("Test Hero Name 2");
        hero2.setDescription("Test Description 2");
        hero2.setIsVillain(true);
        hero2.setSuperpower(superpowers);
        hero2 = heroDao.addHero(hero2);
        
        sighting.setHero(hero2);
        sightingDao.updateSighting(sighting);
        
        assertNotEquals(sighting, fromDao);
        
        fromDao = sightingDao.getSightingById(sighting.getSightingId());
        assertEquals(sighting, fromDao);
        
        
    }
      /**
     * Test of allHeroesAtLocation method, of class SightingDaoDB.
     */
    @Test
    public void testAllHeroesAtLocation() {
        Superpower superpower = new Superpower();
        superpower.setSuperpowerName("Test power");
        superpower = superpowerDao.addSuperpower(superpower);

        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);

        Hero hero = new Hero();
        hero.setHeroName("Test Hero Name");
        hero.setDescription("Test Hero Description");
        hero.setIsVillain(false);
        hero.setSuperpower(superpowers);
        hero = heroDao.addHero(hero);
        
        Hero hero2 = new Hero();
        hero2.setHeroName("Test Hero Name 2");
        hero2.setDescription("Test Description 2");
        hero2.setIsVillain(true);
        hero2.setSuperpower(superpowers);
        hero2 = heroDao.addHero(hero2);
        
        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location Description");
        location.setLatitude((long) 44.9537);
        location.setLongitude((long) 93.09);
        location = locationDao.addLocation(location);
        
        Sighting sighting = new Sighting();
        sighting.setSightingDate(LocalDate.now());
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting sighting2 = new Sighting();
        sighting2.setSightingDate(LocalDate.now());
        sighting2.setHero(hero2);
        sighting2.setLocation(location);
        sighting2 = sightingDao.addSighting(sighting2);
        
        List<Hero> heroes = sightingDao.allHeroesAtLocation(location);
        
        assertEquals(2, heroes.size());
        assertTrue(heroes.contains(hero));
        assertTrue(heroes.contains(hero2));
        
        
    }
    
     /**
     * Test of allLocationsWithHero method, of class SightingDaoDB.
     */
    @Test
    public void testAllLocationsWithHero() {
        Superpower superpower = new Superpower();
        superpower.setSuperpowerName("Test power");
        superpower = superpowerDao.addSuperpower(superpower);

        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);

        Hero hero = new Hero();
        hero.setHeroName("Test Hero Name");
        hero.setDescription("Test Hero Description");
        hero.setIsVillain(false);
        hero.setSuperpower(superpowers);
        hero = heroDao.addHero(hero);
        
        
        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location Description");
        location.setLatitude((long) 44.9537);
        location.setLongitude((long) 93.09);
        location = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setLocationName("Test Location Name");
        location2.setLocationDescription("Test Location Description");
        location2.setLatitude((long) 44.9537);
        location2.setLongitude((long) 93.09);
        location2 = locationDao.addLocation(location2);
        
        Sighting sighting = new Sighting();
        sighting.setSightingDate(LocalDate.now());
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting sighting2 = new Sighting();
        sighting2.setSightingDate(LocalDate.now());
        sighting2.setHero(hero);
        sighting2.setLocation(location2);
        sighting2 = sightingDao.addSighting(sighting2);
        
        List<Location> locations = sightingDao.allLocationsWithHero(hero);
        
        assertEquals(locations.size(), 2);
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
        
    }

       /**
     * Test of allSightingsForDate method, of class SightingDaoDB.
     */
    @Test
    public void testAllSightingsForDate() {
        
        LocalDate date = LocalDate.now();
        Superpower superpower = new Superpower();
        superpower.setSuperpowerName("Test power");
        superpower = superpowerDao.addSuperpower(superpower);

        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);

        Hero hero = new Hero();
        hero.setHeroName("Test Hero Name");
        hero.setDescription("Test Hero Description");
        hero.setIsVillain(false);
        hero.setSuperpower(superpowers);
        hero = heroDao.addHero(hero);
        
        Hero hero2 = new Hero();
        hero2.setHeroName("Test Hero Name 2");
        hero2.setDescription("Test Description 2");
        hero2.setIsVillain(true);
        hero2.setSuperpower(superpowers);
        hero2 = heroDao.addHero(hero2);
        
        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location Description");
        location.setLatitude((long) 44.9537);
        location.setLongitude((long) 93.09);
        location = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setLocationName("Test Location Name");
        location2.setLocationDescription("Test Location Description");
        location2.setLatitude((long) 44.9537);
        location2.setLongitude((long) 93.09);
        location2 = locationDao.addLocation(location2);
        
        Sighting sighting = new Sighting();
        sighting.setSightingDate(date);
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting sighting2 = new Sighting();
        sighting2.setSightingDate(date);
        sighting2.setHero(hero2);
        sighting2.setLocation(location2);
        sighting2 = sightingDao.addSighting(sighting2);
        
        Sighting sighting3 = new Sighting();
        sighting3.setSightingDate(LocalDate.now().plusDays(1));
        sighting3.setHero(hero);
        sighting3.setLocation(location);
        sighting3 = sightingDao.addSighting(sighting3);
        
        List<Sighting> sightings = sightingDao.allSightingsForDate(date);
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));
        assertFalse(sightings.contains(sighting3));
    }
    
    /**
     * Test of deleteSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testDeleteSightingById() {
        Superpower superpower = new Superpower();
        superpower.setSuperpowerName("Test power");
        superpower = superpowerDao.addSuperpower(superpower);

        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);

        Hero hero = new Hero();
        hero.setHeroName("Test Hero Name");
        hero.setDescription("Test Hero Description");
        hero.setIsVillain(false);
        hero.setSuperpower(superpowers);
        hero = heroDao.addHero(hero);
        
        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location Description");
        location.setLatitude((long) 44.9537);
        location.setLongitude((long) 93.09);
        location = locationDao.addLocation(location);
        
        Sighting sighting = new Sighting();
        sighting.setSightingDate(LocalDate.now());
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getSightingId());
        assertEquals(sighting, fromDao);
        
        sightingDao.deleteSightingById(sighting.getSightingId());
        
        fromDao = sightingDao.getSightingById(sighting.getSightingId());
        assertNull(fromDao);
    }

}
