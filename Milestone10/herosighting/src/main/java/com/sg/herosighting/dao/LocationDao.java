/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sg.herosighting.dao;

import com.sg.herosighting.dto.Location;
import java.util.List;

/**
 *
 * @author adrees
 */
public interface LocationDao {

    Location getLocationById(int id);

    List<Location> getAllLocations();

    Location addLocation(Location location);

    void updateLocation(Location location);

    void deleteLocationById(int id);

    // List<Location> getAllLocationsForHero(Hero hero);
}
