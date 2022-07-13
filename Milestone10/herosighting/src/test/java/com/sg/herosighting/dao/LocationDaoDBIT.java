/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
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
public class LocationDaoDBIT {

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

    public LocationDaoDBIT() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations) {
            locationDao.deleteLocationById(location.getLocationId());
        }

        List<Organization> organizations = organizationDao.getAllOrganizations();
        for (Organization organization : organizations) {
            organizationDao.deleteOrganizationById(organization.getOrganizationId());
        }

        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getSightingId());
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

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getLocationById method and addLocation method, of class
     * LocationDaoDB.
     */
    @Test
    public void testAddAndGetLocationById() {
        Location location = new Location();
        location.setLocationName("Test Name");
        location.setLocationDescription("Test Description");
        location.setLatitude((long) 44.9537);
        location.setLongitude((long) 93.09);
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getLocationId());

        assertEquals(location, fromDao);

    }

    /**
     * Test of getAllLocations method, of class LocationDaoDB.
     */
    @Test
    public void testGetAllLocations() {
        Location location = new Location();
        location.setLocationName("Test Name");
        location.setLocationDescription("Test Description");
        location.setLatitude((long) 44.9537);
        location.setLongitude((long) 93.09);
        location = locationDao.addLocation(location);

        Location location2  = new Location();
        location2.setLocationName("Test Name 2");
        location2.setLocationDescription("Test Description 2");
        location2.setLatitude((long) 35.6762);
        location2.setLongitude((long) 139.6503);
        location2 = locationDao.addLocation(location2);
        
        List<Location> locations = locationDao.getAllLocations();
        
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }

    /**
     * Test of updateLocation method, of class LocationDaoDB.
     */
    @Test
    public void testUpdateLocation() {
        Location location = new Location();
        location.setLocationName("Test Name");
        location.setLocationDescription("Test Description");
        location.setLatitude((long) 44.9537);
        location.setLongitude((long) 93.09);
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getLocationId());
        assertEquals(location, fromDao);
        
        location.setLocationName("New Test Name");
        locationDao.updateLocation(location);
        
        assertNotEquals(location, fromDao);
        
        fromDao = locationDao.getLocationById(location.getLocationId());
        assertEquals(location, fromDao);
    }

    /**
     * Test of deleteLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testDeleteLocationById() {
        Location location = new Location();
        location.setLocationName("Test Name");
        location.setLocationDescription("Test Description");
        location.setLatitude((long) 44.9537);
        location.setLongitude((long) 93.09);
        location = locationDao.addLocation(location);
        
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
        
        Sighting sighting = new Sighting();
        sighting.setSightingDate(LocalDate.now());
        sighting.setLocation(location);
        sighting.setHero(hero);
        sighting = sightingDao.addSighting(sighting);
        
        Location fromDao = locationDao.getLocationById(location.getLocationId());
        assertEquals(location, fromDao);
        
        locationDao.deleteLocationById(location.getLocationId());
        
        fromDao = locationDao.getLocationById(location.getLocationId());
        assertNull(fromDao);
        
    }

}
