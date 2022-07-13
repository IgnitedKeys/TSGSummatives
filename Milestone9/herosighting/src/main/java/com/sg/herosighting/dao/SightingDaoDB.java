/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.herosighting.dao;

import com.sg.herosighting.dao.HeroDaoDB.HeroMapper;
import com.sg.herosighting.dao.LocationDaoDB.LocationMapper;
import com.sg.herosighting.dto.Hero;
import com.sg.herosighting.dto.Location;
import com.sg.herosighting.dto.Sighting;
import com.sg.herosighting.dto.Superpower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author adrees
 */
@Repository
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting getSightingById(int id) {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE sightingId= ?";
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), id);
            sighting.setLocation(getLocationForSighting(id));
            sighting.setHero(getHeroForSighting(id));

            Hero hero = sighting.getHero();
            hero.setSuperpower(getSuperpowersForHero(hero.getHeroId()));

            return sighting;

        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        associateLocationAndHero(sightings);

        for (Sighting sighting : sightings) {
            Hero hero = sighting.getHero();
            associatePower(hero);
        }

        return sightings;
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO sighting(sightingDate, locationId, heroId) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_SIGHTING,
                sighting.getSightingDate(),
                sighting.getLocation().getLocationId(),
                sighting.getHero().getHeroId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setSightingId(newId);
        return sighting;

    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE sighting SET sightingDate = ?, "
                + "heroId = ?, locationId = ?";

        jdbc.update(UPDATE_SIGHTING,
                sighting.getSightingDate(),
                sighting.getHero().getHeroId(),
                sighting.getLocation().getLocationId());

    }

    @Override
    @Transactional
    public void deleteSightingById(int id) {

//        final String DELETE_FROM_LOCATION = "DELETE  s.* FROM sighting s JOIN location l "
//                + "ON s.locationId = l.locationId WHERE s.sightingId = ?";
//        jdbc.update(DELETE_FROM_LOCATION, id);
//        //delete hero
//        final String DELETE_FROM_HERO = "DELETE h.* FROM hero h "
//                + "JOIN sighting s ON s.heroId = h.heroId WHERE s.sightingId = ?";
//        jdbc.update(DELETE_FROM_HERO, id);
        //delete sighting
        final String DELETE_FROM_SIGHTING = "DELETE FROM sighting WHERE sightingId = ?";
        jdbc.update(DELETE_FROM_SIGHTING, id);
    }

    @Override
    public List<Hero> allHeroesAtLocation(Location location) {

        final String SELECT_HEROES_FOR_LOCATION = "SELECT h.* FROM sighting s JOIN location l ON l.locationId = s.locationId JOIN hero h ON h.heroId = s.heroId WHERE l.locationId = ?";
        List<Hero> heroes = jdbc.query(SELECT_HEROES_FOR_LOCATION, new HeroMapper(), location.getLocationId());

        for (Hero hero : heroes) {
            associatePower(hero);
        }
        return heroes;
    }

    @Override
    public List<Location> allLocationsWithHero(Hero hero) {
        final String SELECT_LOCATIONS_WITH_HERO = "SELECT l.* FROM sighting s JOIN location l ON l.locationId = s.locationId JOIN hero h ON h.heroId = s.heroId WHERE h.heroId = ?";
        List<Location> locations = jdbc.query(SELECT_LOCATIONS_WITH_HERO, new LocationMapper(), hero.getHeroId());

        return locations;
    }

    @Override
    public List<Sighting> allSightingsForDate(LocalDate date) {
        final String SELECT_SIGHTINGS_FOR_DATE = "SELECT * FROM sighting WHERE sightingDate = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_DATE, new SightingMapper(), date);
        associateLocationAndHero(sightings);
        for (Sighting sighting : sightings) {
            associatePower(sighting.getHero());
        }
        return sightings;
    }

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setSightingId(rs.getInt("sightingId"));
            sighting.setSightingDate(rs.getDate("sightingDate").toLocalDate());
            return sighting;

        }
    }

    private Location getLocationForSighting(int id) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM location l "
                + "JOIN sighting s ON s.locationId = l.locationId WHERE s.sightingId = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationMapper(), id);
    }

    private Hero getHeroForSighting(int id) {
        final String SELECT_HERO_FOR_SIGHTING = "SELECT h.* FROM hero h "
                + "JOIN sighting s On s.heroId = h.heroId WHERE s.sightingId = ?";
        return jdbc.queryForObject(SELECT_HERO_FOR_SIGHTING, new HeroMapper(), id);
    }

    private void associateLocationAndHero(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setLocation(getLocationForSighting(sighting.getSightingId()));
            sighting.setHero(getHeroForSighting(sighting.getSightingId()));
        }
    }

    private List<Superpower> getSuperpowersForHero(int id) {
        final String SELECT_POWERS_FOR_HERO = "SELECT p.* FROM superpower p "
                + "JOIN superpower_Hero sh ON sh.superpowerId = p.superpowerId WHERE sh.heroId = ?";
        return jdbc.query(SELECT_POWERS_FOR_HERO, new SuperpowerDaoDB.SuperpowerMapper(), id);
    }

    private void associatePower(Hero hero) {
        hero.setSuperpower(getSuperpowersForHero(hero.getHeroId()));
    }

}
