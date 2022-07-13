/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.herosighting.dao;

import com.sg.herosighting.dao.SuperpowerDaoDB.SuperpowerMapper;
import com.sg.herosighting.dto.Hero;
import com.sg.herosighting.dto.Superpower;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class HeroDaoDB implements HeroDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Hero getHeroById(int id) {
        try {
            final String GET_HERO_BY_ID = "SELECT * FROM hero WHERE heroId = ?";

            Hero hero = jdbc.queryForObject(GET_HERO_BY_ID, new HeroMapper(), id);
            //add superpower to hero
            hero.setSuperpower(getSuperpowersForHero(id));
            return hero;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Hero> getAllHeroes() {
        final String GET_ALL_HEROES = "SELECT * FROM hero";
        List<Hero> heroes = jdbc.query(GET_ALL_HEROES, new HeroMapper());
        associatePowers(heroes);
        return heroes;
    }

    @Override
    @Transactional
    public Hero addHero(Hero hero) {
        final String INSERT_HERO = "INSERT INTO hero(heroName, heroDescription, isVillian) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_HERO,
                hero.getHeroName(),
                hero.getDescription(),
                hero.getIsVillian());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setHeroId(newId);
        //create superpower_hero
        insertSuperpowerHero(hero);
        return hero;
    }

    @Override
    public void updateHero(Hero hero) {
        final String UPDATE_HERO = "UPDATE hero SET heroName = ?, heroDescription = ?, isVillian = ? WHERE heroId = ?";
        jdbc.update(UPDATE_HERO,
                hero.getHeroName(),
                hero.getDescription(),
                hero.getIsVillian(),
                hero.getHeroId());

        final String DELETE_SUPERPOWER_HERO = "DELETE FROM superpower_Hero WHERE heroId = ?";
        jdbc.update(DELETE_SUPERPOWER_HERO, hero.getHeroId());
        insertSuperpowerHero(hero);
    }

    @Override
    @Transactional
    public void deleteHeroById(int id) {
        //delete superpower_Hero
        final String DELETE_FROM_SUPERPOWER_HERO = "DELETE FROM superpower_Hero WHERE heroId= ?";
        jdbc.update(DELETE_FROM_SUPERPOWER_HERO, id);
        //delete sightings
        final String DELETE_FROM_SIGHTING = "DELETE FROM sighting WHERE heroId = ?";
        jdbc.update(DELETE_FROM_SIGHTING, id);
        //delete hero_Organization
        final String DELETE_FROM_HERO_ORGANIZATION = "DELETE FROM hero_Organization WHERE heroId = ?";
        jdbc.update(DELETE_FROM_HERO_ORGANIZATION, id);
        //delete hero
        final String DELETE_FROM_HERO = "DELETE FROM hero WHERE heroId = ?";
        jdbc.update(DELETE_FROM_HERO, id);
    }

    public static final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {
            Hero hero = new Hero();
            hero.setHeroId(rs.getInt("heroId"));
            hero.setHeroName(rs.getString("heroName"));
            hero.setDescription(rs.getString("heroDescription"));

            hero.setIsVillian(rs.getBoolean(("isVillian")));

            return hero;
        }
    }

    private List<Superpower> getSuperpowersForHero(int id) {
        final String SELECT_POWERS_FOR_HERO = "SELECT p.* FROM superpower p "
                + "JOIN superpower_Hero sh ON sh.superpowerId = p.superpowerId WHERE sh.heroId = ?";
        return jdbc.query(SELECT_POWERS_FOR_HERO, new SuperpowerMapper(), id);
    }

    private void associatePowers(List<Hero> heroes) {
        for (Hero hero : heroes) {
            hero.setSuperpower(getSuperpowersForHero(hero.getHeroId()));
        }
    }

    private void insertSuperpowerHero(Hero hero) {
        final String INSERT_SUPERPOWER_HERO = "INSERT INTO superpower_Hero(heroId, superpowerId) "
                + "VALUES(?,?)";

        for (Superpower superpower : hero.getSuperpower()) {
            jdbc.update(INSERT_SUPERPOWER_HERO,
                    hero.getHeroId(),
                    superpower.getSuperpowerId());
        }
    }
}
