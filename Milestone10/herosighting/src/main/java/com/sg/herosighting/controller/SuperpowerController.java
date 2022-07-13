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
import com.sg.herosighting.dto.Superpower;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author adrees
 */
@Controller
public class SuperpowerController {

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

    Set<ConstraintViolation<Superpower>> violations = new HashSet<>();

    @GetMapping("/superpowers")
    public String displaySuperpowers(Model model) {

        
        model.addAttribute("errors", violations);

        violations.clear();
       
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        List<Superpower> sortedSuperpowers = new ArrayList<Superpower>();
        Comparator<Superpower> nameComparator = Comparator.comparing(Superpower::getSuperpowerName, String.CASE_INSENSITIVE_ORDER);
        sortedSuperpowers = superpowers.stream().sorted(nameComparator).collect(Collectors.toList());
        
        model.addAttribute("superpowers", sortedSuperpowers);

        return "superpowers";
    }

    @PostMapping("/addSuperpower")
    public String addSuperpower(HttpServletRequest request, Model model) {

        violations.clear();
        String superpowerName = request.getParameter("superpowerName");

        Superpower superpower = new Superpower();
        superpower.setSuperpowerName(superpowerName);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superpower);

        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        if (!violations.isEmpty()) {
             model.addAttribute("errors", violations);
             model.addAttribute("superpowers", superpowers);
             
            return "superpowers";
        }
        
        model.addAttribute("errors", violations);
        model.addAttribute("superpowers", superpowers);
        superpowerDao.addSuperpower(superpower);

        return "redirect:/superpowers";
    }

    @GetMapping("/deleteSuperpower")
    public String deleteSuperpower(Model model, Integer id) {
        //superpowerDao.deleteSuperpowerById(id);
        Superpower superpower = superpowerDao.getSuperpowerById(id);
        model.addAttribute("superpower", superpower);

        return "deleteSuperpower";
    }

    @PostMapping("/deleteSuperpower")
    public String performDeleteSuperpower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("superpowerId"));
        //model.addAttribute("superpower", superpower);
        superpowerDao.deleteSuperpowerById(id);
        return "redirect:/superpowers";
    }

    @GetMapping("/editSuperpower")
    public String editSuperpower(Integer id, Model model) {
        model.addAttribute("errors", violations);

        Superpower superpower = superpowerDao.getSuperpowerById(id);
        model.addAttribute("superpower", superpower);
        return "editSuperpower";
    }

//    @PostMapping("editSuperpower")
//    public String performEditSuperpower(HttpServletRequest request, Model model) {
//        
//        int id = Integer.parseInt(request.getParameter("id"));
//        Superpower superpower = superpowerDao.getSuperpowerById(id);
//        
//        superpower.setSuperpowerName(request.getParameter("superpowerName"));
//        
//        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
//        violations = validate.validate(superpower);
//        
//        model.addAttribute("errors", violations);
//        
//        if(violations.isEmpty()){
//            superpowerDao.updateSuperpower(superpower);
//        } else {
//            return "editSuperpower";
//        }
//        
//        return "redirect:/superpowers";
//    }
    @PostMapping("/editSuperpower")
    public String performEditSuperpower(@Valid Superpower superpower, BindingResult result) {
        if (result.hasErrors()) {
            return "editSuperpower";
        }
        superpowerDao.updateSuperpower(superpower);
        return "redirect:/superpowers";
    }
}
