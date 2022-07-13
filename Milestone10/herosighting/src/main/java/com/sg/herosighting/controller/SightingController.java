/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.herosighting.controller;

import com.sg.herosighting.dao.HeroDao;
import com.sg.herosighting.dao.LocationDao;
import com.sg.herosighting.dao.OrganizationDao;
import com.sg.herosighting.dao.SightingDao;
import com.sg.herosighting.dao.SuperpowerDao;
import com.sg.herosighting.dto.Hero;
import com.sg.herosighting.dto.Location;
import com.sg.herosighting.dto.Sighting;
import com.sg.herosighting.dto.Superpower;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author adrees
 */
@Controller
public class SightingController {

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    SuperpowerDao superpowerDao;

    @Autowired
    OrganizationDao organizationDao;

    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();

    @GetMapping("/home")
    public String displayHome(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        sightings.sort(Comparator.comparing(o -> o.getSightingDate()));

        List<Sighting> sortedSightings = sightings.stream().collect(Collectors.toList());
        Collections.reverse(sortedSightings);
        sortedSightings = sortedSightings.stream().limit(10).collect(Collectors.toList());

        model.addAttribute("sightings", sortedSightings);

        return "home";
    }

    @GetMapping("/sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        sightings.sort(Comparator.comparing(o -> o.getSightingDate()));
        List<Sighting> sortedSightings = sightings.stream().collect(Collectors.toList());
        Collections.reverse(sortedSightings);

        model.addAttribute("sightings", sortedSightings);

        return "sightings";
    }

    @GetMapping("/sighting")
    public String displaySighting(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);
        model.addAttribute("sighting", sighting);

        return "sighting";
    }

    @GetMapping("/addSighting")
    public String addSighting(Model model) {
        model.addAttribute("errors", violations);
        violations.clear();
        List<Location> locations = locationDao.getAllLocations();
        List<Hero> heroes = heroDao.getAllHeroes();

//        if(locations.isEmpty()){
//            
//            return "redirect:/sightings";
//        }
//        
//        if(heroes.isEmpty()){
//           
//            return "/addSighting";
//        }
        model.addAttribute("locations", locations);
        model.addAttribute("heroes", heroes);

        return "addSighting";
    }

    @PostMapping("/addSighting")
    public String performAddSighting(HttpServletRequest request, Model model) {
        String dateString = request.getParameter("date");
        Sighting sighting = new Sighting();
        if (dateString.isBlank()) {
            dateString = LocalDate.now().toString();
        }
        LocalDate date = LocalDate.parse(dateString);
        sighting.setSightingDate(date);

        String locationId = request.getParameter("locationId");
        String heroId = request.getParameter("heroId");

        sighting.setHero(heroDao.getHeroById(Integer.parseInt(heroId)));
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);

        List<Location> locations = locationDao.getAllLocations();
        List<Hero> heroes = heroDao.getAllHeroes();

        if (!violations.isEmpty()) {
            model.addAttribute("errors", violations);
            model.addAttribute("locations", locations);
            model.addAttribute("heroes", heroes);
            return "addSighting";
        }

        sightingDao.addSighting(sighting);

        return "redirect:/sightings";
    }

    @GetMapping("/deleteSighting")
    public String deleteSighting(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);
        model.addAttribute("sighting", sighting);

        return "deleteSighting";
    }

    @PostMapping("/deleteSighting")
    public String performDeleteSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("sightingId"));
        sightingDao.deleteSightingById(id);
        return "redirect:/sightings";
    }

    @GetMapping("/editSighting")
    public String editSighting(Integer id, Model model) {
        model.addAttribute("errors", violations);
        violations.clear();
        Sighting sighting = sightingDao.getSightingById(id);
        List<Location> locations = locationDao.getAllLocations();
        List<Hero> heroes = heroDao.getAllHeroes();

        model.addAttribute("sighting", sighting);
        model.addAttribute("locations", locations);
        model.addAttribute("heroes", heroes);

        return "editSighting";
    }

    @PostMapping("/editSighting")
    public String performEditSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("sightingId"));
        Sighting sighting = sightingDao.getSightingById(id);
        String heroId = request.getParameter("heroId");
        String locationId = request.getParameter("locationId");

        sighting.setSightingDate(LocalDate.parse(request.getParameter("date")));
        sighting.setHero(heroDao.getHeroById(Integer.parseInt(heroId)));
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);

        List<Location> locations = locationDao.getAllLocations();
        List<Hero> heroes = heroDao.getAllHeroes();
        if (!violations.isEmpty()) {
            model.addAttribute("errors", violations);
            model.addAttribute("locations", locations);
            model.addAttribute("heroes", heroes);
            model.addAttribute("sighting", sighting);

            return "editSighting";
        }
        sightingDao.updateSighting(sighting);

        return "redirect:/sightings";
    }

}
