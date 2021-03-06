/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.herosighting.dao;

import com.sg.herosighting.dto.Location;
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
public class LocationDaoDB implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int id) {
        try {
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM location WHERE locationId = ?";
            return jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        final String SELECT_ALL_LOCATIONS = "SELECT * FROM location";
        return jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO location(locationName, locationDescription, latitude, longitude) "
                + "VALUES(?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getLocationName(),
                location.getLocationDescription(),
                location.getLatitude(),
                location.getLongitude());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setLocationId(newId);
        return location;
    }

    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE location SET locationName = ?, locationDescription = ?, latitude = ?, longitude = ? "
                + "WHERE locationId = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getLocationName(),
                location.getLocationDescription(),
                location.getLatitude(),
                location.getLongitude(),
                location.getLocationId());
    }

    @Override
    public void deleteLocationById(int id) {
        //delete sighting 
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE locationId = ?";
        jdbc.update(DELETE_SIGHTING, id);
        //delete location
        final String DELETE_LOCATION = "DELETE FROM location WHERE locationId = ?";
        jdbc.update(DELETE_LOCATION, id);
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setLocationId(rs.getInt("locationId"));
            location.setLocationName(rs.getString("locationName"));
            location.setLocationDescription(rs.getString("locationDescription"));
            location.setLatitude(rs.getFloat("latitude"));
            location.setLongitude(rs.getFloat("longitude"));

            return location;
        }
    }

}
