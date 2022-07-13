/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.herosighting.dao;

import com.sg.herosighting.dao.HeroDaoDB.HeroMapper;
import com.sg.herosighting.dto.Hero;
import com.sg.herosighting.dto.Organization;
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
public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationById(int id) {
        try {
            final String SELECT_ORGANIZATION_BY_ID = "SELECT * FROM organization WHERE organizationId = ?";
            Organization organization = jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID, new OrganizationMapper(), id);

            organization.setHeroes(getHeroesForOrganization(id));

            List<Hero> heroes = organization.getHeroes();
            for (Hero hero : heroes) {
                hero.setSuperpower(getSuperpowersForHero(hero.getHeroId()));
            }

            return organization;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM organization";
        List<Organization> organizations = jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
        associateHeroes(organizations);

        for (Organization organization : organizations) {
            List<Hero> heroes = organization.getHeroes();
            associatePowers(heroes);
        }
        return organizations;
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
        final String INSERT_ORGANIZATION = "INSERT INTO organization(organizationName, organizationDescription, contact, address) "
                + "VALUES(?,?,?,?)";
        jdbc.update(INSERT_ORGANIZATION,
                organization.getOrganizationName(),
                organization.getOrganizationDescription(),
                organization.getContact(),
                organization.getAddress());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setOrganizationId(newId);
        insertHeroOrganization(organization);

        return organization;
    }

    @Override
    public void updateOrganization(Organization organization) {
        final String UPDATE_ORGANIZATION = "UPDATE organization SET organizationName = ?,"
                + "organizationDescription = ?, contact = ?, address = ? "
                + "WHERE organizationId = ?";
        jdbc.update(UPDATE_ORGANIZATION,
                organization.getOrganizationName(),
                organization.getOrganizationDescription(),
                organization.getContact(),
                organization.getAddress(),
                organization.getOrganizationId());

        final String DELETE_HERO_ORGANIZATION = "DELETE FROM hero_Organization WHERE organizationId = ?";
        jdbc.update(DELETE_HERO_ORGANIZATION, organization.getOrganizationId());
        insertHeroOrganization(organization);
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int id) {
        final String DELETE_HERO_ORGANIZATION = "DELETE FROM hero_Organization WHERE organizationId = ?";
        jdbc.update(DELETE_HERO_ORGANIZATION, id);

        final String DELETE_ORGANIZATION = "DELETE FROM organization WHERE organizationId = ?";
        jdbc.update(DELETE_ORGANIZATION, id);
    }

    @Override
    public List<Hero> getHeroesInOrganization(Organization organization) {
        List<Hero> heroes = getHeroesForOrganization(organization.getOrganizationId());
        associatePowers(heroes);
        return heroes;
    }

    @Override
    public List<Organization> getOrganizationsForHero(Hero hero) {
        final String SELECT_ORGANIZATIONS_FOR_HERO = "SELECT o.* FROM organization o JOIN hero_Organization ho ON o.organizationId = ho.organizationId WHERE ho.heroId = ?";
        List<Organization> organizations = jdbc.query(SELECT_ORGANIZATIONS_FOR_HERO, new OrganizationMapper(), hero.getHeroId());
        associateHeroes(organizations);
        for (Organization organization : organizations) {
            List<Hero> heroes = organization.getHeroes();
            associatePowers(heroes);
        }

        return organizations;
    }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setOrganizationId(rs.getInt("organizationId"));
            organization.setOrganizationName(rs.getString("organizationName"));
            organization.setOrganizationDescription(rs.getString("organizationDescription"));
            organization.setContact(rs.getString("contact"));
            organization.setAddress(rs.getString("address"));

            return organization;
        }
    }

    private void insertHeroOrganization(Organization organization) {
        final String INSERT_HERO_ORGANIZATION = "INSERT INTO "
                + "hero_Organization(heroId, organizationId) VALUES(?,?)";
        for (Hero hero : organization.getHeroes()) {
            jdbc.update(INSERT_HERO_ORGANIZATION,
                    hero.getHeroId(),
                    organization.getOrganizationId());
        }
    }

    private List<Hero> getHeroesForOrganization(int id) {
        final String SELECT_HEROES_FOR_ORGANIZATION = "SELECT h.* FROM "
                + "hero h JOIN hero_Organization ho ON ho.heroId = h.heroId "
                + "WHERE ho.organizationId = ?";
        return jdbc.query(SELECT_HEROES_FOR_ORGANIZATION, new HeroMapper(), id);
    }

    private void associateHeroes(List<Organization> organizations) {
        for (Organization organization : organizations) {
            organization.setHeroes(getHeroesForOrganization(organization.getOrganizationId()));
        }

    }

    private List<Superpower> getSuperpowersForHero(int id) {
        final String SELECT_POWERS_FOR_HERO = "SELECT p.* FROM superpower p "
                + "JOIN superpower_Hero sh ON sh.superpowerId = p.superpowerId WHERE sh.heroId = ?";
        return jdbc.query(SELECT_POWERS_FOR_HERO, new SuperpowerDaoDB.SuperpowerMapper(), id);
    }

    private void associatePowers(List<Hero> heroes) {
        for (Hero hero : heroes) {
            hero.setSuperpower(getSuperpowersForHero(hero.getHeroId()));
        }
    }
}
