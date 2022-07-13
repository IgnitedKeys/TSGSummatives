/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sg.herosighting.dao;

import com.sg.herosighting.dto.Hero;
import com.sg.herosighting.dto.Location;
import com.sg.herosighting.dto.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author adrees
 */
public interface SightingDao {

    Sighting getSightingById(int id);

    List<Sighting> getAllSightings();

    Sighting addSighting(Sighting sighting);

    void updateSighting(Sighting sighting);

    void deleteSightingById(int id);

    //all superheroes sighted at a location
    List<Hero> allHeroesAtLocation(Location location);

    //all locations where hero was sighted
    List<Location> allLocationsWithHero(Hero hero);

    //sightings for date
    List<Sighting> allSightingsForDate(LocalDate date);
}
