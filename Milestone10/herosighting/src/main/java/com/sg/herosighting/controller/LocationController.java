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
import com.sg.herosighting.dto.Location;
import java.util.ArrayList;
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
public class LocationController {

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

    Set<ConstraintViolation<Location>> violations = new HashSet<>();

    @GetMapping("/locations")
    public String displayLocations(Model model) {
        model.addAttribute("errors", violations);
        violations.clear();

        List<Location> locations = locationDao.getAllLocations();
        List<Location> sortedLocations = new ArrayList<Location>();
        Comparator<Location> nameComparator = Comparator.comparing(Location::getLocationName, String.CASE_INSENSITIVE_ORDER);
        sortedLocations = locations.stream().sorted(nameComparator).collect(Collectors.toList());
        
        model.addAttribute("locations", sortedLocations);

        return "locations";
    }

    @GetMapping("/location")
    public String displayLocation(Integer id, Model model){
        
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);
        
        return "location";
    }
    @PostMapping("/addLocation")
    public String addLocation(HttpServletRequest request, Model model) {
        
        violations.clear();
        String locationName = request.getParameter("locationName");
        String locationDescription = request.getParameter("locationDescription");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");

        if (latitude.isBlank()) {
            latitude = "-91";
        }

        if (longitude.isBlank()) {
            longitude = "-181.0009";
        }
        Location location = new Location();
        location.setLocationName(locationName);
        location.setLocationDescription(locationDescription);
        location.setLatitude(Double.parseDouble(latitude));
        location.setLongitude(Double.parseDouble(longitude));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);

        List<Location> locations = locationDao.getAllLocations();
        if (!violations.isEmpty()) {
            model.addAttribute("locations", locations);
            model.addAttribute("errors", violations);
            
            return "locations";
        } 



        model.addAttribute("locations", locations);
            model.addAttribute("errors", violations);

        locationDao.addLocation(location);

        return "redirect:/locations";
    }

    @GetMapping("/deleteLocation")
    public String deleteLocation(Integer id, Model model) {
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);

        return "deleteLocation";
    }
    
    @PostMapping("/deleteLocation")
    public String performDeleteLocation(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("locationId"));
        locationDao.deleteLocationById(id);
        return "redirect:/locations";
    }

    @GetMapping("/editLocation")
    public String editLocation(Integer id, Model model) {
        model.addAttribute("errors", violations);
        violations.clear();
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);
        return "editLocation";
    }

//    @PostMapping("/editLocation")
//    public String performEditLocation(@Valid Location location, BindingResult result, HttpServletRequest request, Model model) {
////        int id = Integer.parseInt(request.getParameter("id"));
////        Location location = locationDao.getLocationById(id);
//
//        String latitude = request.getParameter("latitude");
//        String longitude = request.getParameter("longitude");
//////        
//
//       // Double latitude = Double.parseDouble(request.getParameter("latitude"));
//        
////        if (latitude.isBlank()) {
////            FieldError error = new FieldError("location", "latitude", "Latitude needed");
////            result.addError(error);
////        }
//
//        if (longitude.isBlank()) {
//            longitude = "-181.0009";
//        }
////        location.setLocationName(request.getParameter("locationName"));
////        location.setLocationDescription(request.getParameter("locationDescription"));
////        location.setLatitude(Double.parseDouble(request.getParameter("latitude")));
////        location.setLongitude(Double.parseDouble(request.getParameter("longitude")));
//
//        if (result.hasErrors()) {
//            model.addAttribute("location", location);
//            return "editLocation";
//        }
//
//        locationDao.updateLocation(location);
//
//        return "redirect:/locations";
//    }
    @PostMapping("/editLocation")
    public String performEditLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("locationId"));
        Location location = locationDao.getLocationById(id);
        
        Double latitude1 = location.getLatitude();
        Double longitude1 = location.getLongitude();

        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");

        if (latitude.isBlank()) {
            latitude = "-91";
        }

        if (longitude.isBlank()) {
            longitude = "-181";
        }

        location.setLocationName(request.getParameter("locationName"));
        location.setLocationDescription(request.getParameter("locationDescription"));
        location.setLatitude(Double.parseDouble(latitude));
        location.setLongitude(Double.parseDouble(longitude));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);

        if (!violations.isEmpty()) {
            location.setLatitude(latitude1);
            location.setLongitude(longitude1);
            model.addAttribute("location", location);
            model.addAttribute("errors", violations);
            return "editLocation";
        }

        locationDao.updateLocation(location);

        return "redirect:/locations";
    }
}
