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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author adrees
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganizationDaoDBIT {

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

    public OrganizationDaoDBIT() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
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

    @After
    public void tearDown() {
    }

    /**
     * Test of getOrganizationById method and addOrganization method, of class
     * OrganizationDaoDB.
     */
    @Test
    public void testAddAndGetOrganizationById() {
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

        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);

        Organization organization = new Organization();
        organization.setOrganizationName("Test Organization Name");
        organization.setOrganizationDescription("Test Description");
        organization.setAddress("Test Address");
        organization.setContact("Test Contact");
        organization.setHeroes(heroes);
        organization = organizationDao.addOrganization(organization);

        Organization fromDao = organizationDao.getOrganizationById(organization.getOrganizationId());

        assertEquals(organization, fromDao);

    }

    /**
     * Test of getAllOrganizations method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetAllOrganizations() {
        Superpower superpower = new Superpower();
        superpower.setSuperpowerName("Test power");
        superpower = superpowerDao.addSuperpower(superpower);

        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);

        Superpower superpower2 = new Superpower();
        superpower2.setSuperpowerName("Test power");
        superpower2 = superpowerDao.addSuperpower(superpower2);

        List<Superpower> superpowers2 = new ArrayList<>();
        superpowers2.add(superpower);
        superpowers2.add(superpower2);

        Hero hero = new Hero();
        hero.setHeroName("Test Hero Name");
        hero.setDescription("Test Hero Description");
        hero.setIsVillain(false);
        hero.setSuperpower(superpowers);
        hero = heroDao.addHero(hero);

        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);

        Hero hero2 = new Hero();
        hero2.setHeroName("Test Hero Name 2");
        hero2.setDescription("Test Hero Description 2");
        hero2.setIsVillain(true);
        hero2.setSuperpower(superpowers2);
        hero2 = heroDao.addHero(hero2);

        List<Hero> heroes2 = new ArrayList<>();
        heroes2.add(hero);
        heroes2.add(hero2);

        Organization organization = new Organization();
        organization.setOrganizationName("Test Organization Name");
        organization.setOrganizationDescription("Test Description");
        organization.setAddress("Test Address");
        organization.setContact("Test Contact");
        organization.setHeroes(heroes);
        organization = organizationDao.addOrganization(organization);

        Organization organization2 = new Organization();
        organization2.setOrganizationName("Test Name 2");
        organization2.setOrganizationDescription("Test Description 2");
        organization2.setAddress("Test Address 2");
        organization2.setContact("Test Contact 2");
        organization2.setHeroes(heroes2);
        organization2 = organizationDao.addOrganization(organization2);

        List<Organization> organizations = organizationDao.getAllOrganizations();

        assertEquals(2, organizations.size());
        assertTrue(organizations.contains(organization));
        assertTrue(organizations.contains(organization2));

    }
    
    /**
     * Test of getOrganizationsForHero method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetOrganizationsForHero() {
        Superpower superpower = new Superpower();
        superpower.setSuperpowerName("Test power");
        superpower = superpowerDao.addSuperpower(superpower);

        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);

        Superpower superpower2 = new Superpower();
        superpower2.setSuperpowerName("Test power");
        superpower2 = superpowerDao.addSuperpower(superpower2);

        List<Superpower> superpowers2 = new ArrayList<>();
        superpowers2.add(superpower);
        superpowers2.add(superpower2);

        Hero hero = new Hero();
        hero.setHeroName("Test Hero Name");
        hero.setDescription("Test Hero Description");
        hero.setIsVillain(false);
        hero.setSuperpower(superpowers);
        hero = heroDao.addHero(hero);

        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);


        Organization organization = new Organization();
        organization.setOrganizationName("Test Organization Name");
        organization.setOrganizationDescription("Test Description");
        organization.setAddress("Test Address");
        organization.setContact("Test Contact");
        organization.setHeroes(heroes);
        organization = organizationDao.addOrganization(organization);

        Organization organization2 = new Organization();
        organization2.setOrganizationName("Test Name 2");
        organization2.setOrganizationDescription("Test Description 2");
        organization2.setAddress("Test Address 2");
        organization2.setContact("Test Contact 2");
        organization2.setHeroes(heroes);
        organization2 = organizationDao.addOrganization(organization2);
        
        Organization organization3 = new Organization();
        organization3.setOrganizationName("Test Name 3");
        organization3.setOrganizationDescription("Test Description 3");
        organization3.setAddress("Test Address 3");
        organization3.setContact("Test Contact 3");
        organization3.setHeroes(heroes);
        organization3 = organizationDao.addOrganization(organization3);
        
        List<Organization> organizations = organizationDao.getOrganizationsForHero(hero);
        
        assertEquals(organizations.size(), 3);
        assertTrue(organizations.contains(organization));
        assertTrue(organizations.contains(organization2));
        assertTrue(organizations.contains(organization3));
        
    }
    /**
     * Test of updateOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testUpdateOrganization() {
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
        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);

        Organization organization = new Organization();
        organization.setOrganizationName("Test Organization Name");
        organization.setOrganizationDescription("Test Description");
        organization.setAddress("Test Address");
        organization.setContact("Test Contact");
        organization.setHeroes(heroes);
        organization = organizationDao.addOrganization(organization);

        Organization fromDao = organizationDao.getOrganizationById(organization.getOrganizationId());
        assertEquals(organization, fromDao);

        organization.setOrganizationName("New Test Name");
        organizationDao.updateOrganization(organization);

        assertNotEquals(organization, fromDao);

        fromDao = organizationDao.getOrganizationById(organization.getOrganizationId());
        assertEquals(organization, fromDao);

    }

    /**
     * Test of getHeroesInOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetHeroesInOrganization() {
        Superpower superpower = new Superpower();
        superpower.setSuperpowerName("Test power");
        superpower = superpowerDao.addSuperpower(superpower);

        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);

        Superpower superpower2 = new Superpower();
        superpower2.setSuperpowerName("Test power");
        superpower2 = superpowerDao.addSuperpower(superpower2);

        List<Superpower> superpowers2 = new ArrayList<>();
        superpowers2.add(superpower);
        superpowers2.add(superpower2);

        Hero hero = new Hero();
        hero.setHeroName("Test Hero Name");
        hero.setDescription("Test Hero Description");
        hero.setIsVillain(false);
        hero.setSuperpower(superpowers);
        hero = heroDao.addHero(hero);

        Hero hero2 = new Hero();
        hero2.setHeroName("Test Hero Name 2");
        hero2.setDescription("Test Hero Description 2");
        hero2.setIsVillain(true);
        hero2.setSuperpower(superpowers2);
        hero2 = heroDao.addHero(hero2);

        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);
        heroes.add(hero2);

        Organization organization = new Organization();
        organization.setOrganizationName("Test Organization Name");
        organization.setOrganizationDescription("Test Description");
        organization.setAddress("Test Address");
        organization.setContact("Test Contact");
        organization.setHeroes(heroes);
        organization = organizationDao.addOrganization(organization);

        List<Hero> allMembers = organizationDao.getHeroesInOrganization(organization);

        assertEquals(allMembers.size(), 2);
        assertTrue(allMembers.contains(hero));
        assertTrue(allMembers.contains(hero2));

    }

    /**
     * Test of deleteOrganizationById method, of class OrganizationDaoDB.
     */
    @Test
    @Transactional
    public void testDeleteOrganizationById() {
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

        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);

        Organization organization = new Organization();
        organization.setOrganizationName("Test Organization Name");
        organization.setOrganizationDescription("Test Description");
        organization.setAddress("Test Address");
        organization.setContact("Test Contact");
        organization.setHeroes(heroes);
        organization = organizationDao.addOrganization(organization);

        Organization fromDao = organizationDao.getOrganizationById(organization.getOrganizationId());

        assertEquals(organization, fromDao);

        organizationDao.deleteOrganizationById(organization.getOrganizationId());

        fromDao = organizationDao.getOrganizationById(organization.getOrganizationId());
        assertNull(fromDao);

    }

}
