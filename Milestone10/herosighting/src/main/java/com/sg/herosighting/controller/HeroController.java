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
import com.sg.herosighting.dto.Organization;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author adrees
 */
@Controller
public class HeroController {

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

    Set<ConstraintViolation<Hero>> violations = new HashSet<>();

    @GetMapping("/supers")
    public String displaySupers(Model model) {

        model.addAttribute("errors", violations);

        List<Hero> heroes = heroDao.getAllHeroes();
        List<Hero> sortedSupers = new ArrayList<Hero>();
        Comparator<Hero> nameComparator = Comparator.comparing(Hero::getHeroName, String.CASE_INSENSITIVE_ORDER);
        sortedSupers = heroes.stream().sorted(nameComparator).collect(Collectors.toList());
        model.addAttribute("heroes", sortedSupers);

        return "supers";
    }

    //can have multiple of each--check html
    @GetMapping("/addSuper")
    public String addSuper(Model model) {
        model.addAttribute("errors", violations);
        
        violations.clear();

        List<Organization> organizations = organizationDao.getAllOrganizations();
        model.addAttribute("organizations", organizations);

        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);

        return "addSuper";
    }

    @PostMapping("/addSuper")
    public String performAddSuper(HttpServletRequest request, Model model) {
        String heroName = request.getParameter("heroName");
        String heroDescription = request.getParameter("heroDescription");
        String isVillain = request.getParameter("isVillain");

        Hero hero = new Hero();
        hero.setHeroName(heroName);
        hero.setDescription(heroDescription);

        hero.setIsVillain(Boolean.valueOf(isVillain));

        //hero.setIsVillain(isVillain);
        String[] organizationIds = request.getParameterValues("organizationId");
        String[] superpowerIds = request.getParameterValues("superpowerId");

        List<Organization> organizations = new ArrayList<>();
        List<Superpower> superpowers = new ArrayList<>();

        for (String organizationId : organizationIds) {
            organizations.add(organizationDao.getOrganizationById(Integer.parseInt(organizationId)));
        }

        for (String superpowerId : superpowerIds) {
            superpowers.add(superpowerDao.getSuperpowerById(Integer.parseInt(superpowerId)));
        }

        hero.setSuperpower(superpowers);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(hero);

        List<Organization> organizationsFromDao = organizationDao.getAllOrganizations();
        List<Superpower> superpowersFromDao = superpowerDao.getAllSuperpowers();

        if (!violations.isEmpty()) {
            model.addAttribute("errors", violations);
            model.addAttribute("organizations", organizationsFromDao);
            model.addAttribute("superpowers", superpowersFromDao);

            return "addSuper";
        }

        heroDao.addHero(hero);

        //add hero to organizations 
        //does it have a hero Id?
        for (Organization organization : organizations) {
            List<Hero> heroes = organization.getHeroes();

            heroes.add(hero);
            organization.setHeroes(heroes);
            organizationDao.updateOrganization(organization);

        }

        //get organizationId
        //update organization with 
        return "redirect:/supers";
    }

    //pass in Organization and superpowers
    @GetMapping("/super")
    public String displaySuper(Integer id, Model model) {

        Hero hero = heroDao.getHeroById(id);
        model.addAttribute("hero", hero);

        List<Organization> organizations = organizationDao.getOrganizationsForHero(hero);
        
        List<Organization> sortedOrganizations = new ArrayList<Organization>();
        Comparator<Organization> nameComparator = Comparator.comparing(Organization::getOrganizationName, String.CASE_INSENSITIVE_ORDER);
        sortedOrganizations = organizations.stream().sorted(nameComparator).collect(Collectors.toList());
        
        model.addAttribute("organizations", sortedOrganizations);
        
        

        List<Superpower> superpowers = hero.getSuperpower();
        
        List<Superpower> sortedSuperpowers = new ArrayList<Superpower>();
        Comparator<Superpower> nameComparator2 = Comparator.comparing(Superpower::getSuperpowerName, String.CASE_INSENSITIVE_ORDER);
        sortedSuperpowers = superpowers.stream().sorted(nameComparator2).collect(Collectors.toList());
        
        model.addAttribute("superpowers", sortedSuperpowers);
        
        return "super";
    }

    @GetMapping("/deleteSuper")
    public String deleteSuper(Integer id, Model model) {
        Hero hero = heroDao.getHeroById(id);
        model.addAttribute("hero", hero);

        return "deleteSuper";
    }
    
    @PostMapping("/deleteSuper")
    public String deleteSuper(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("heroId"));
        heroDao.deleteHeroById(id);
        return"redirect:/supers";
    }

    @GetMapping("/editSuper")
    public String editSuper(Integer id, Model model) {
        model.addAttribute("errors", violations);
        violations.clear();
        
        Hero hero = heroDao.getHeroById(id);
        List<Organization> organizations = organizationDao.getAllOrganizations();
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();

        model.addAttribute("hero", hero);
        model.addAttribute("organizations", organizations);
        model.addAttribute("superpowers", superpowers);

        return "editSuper";
    }

    @PostMapping("/editSuper")
    public String performEditSuper(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Hero hero = heroDao.getHeroById(id);

        String isVillain = request.getParameter("isVillain");

        hero.setHeroName(request.getParameter("heroName"));
        hero.setDescription(request.getParameter("heroDescription"));
        hero.setIsVillain(Boolean.valueOf(isVillain));

        String[] superpowerIds = request.getParameterValues("superpowerId");
        String[] organizationIds = request.getParameterValues("organizationId");

        List<Organization> organizations = new ArrayList<>();
        List<Superpower> superpowers = new ArrayList<>();

       
        for (String organizationId : organizationIds) {
            organizations.add(organizationDao.getOrganizationById(Integer.parseInt(organizationId)));
        }

        for (String superpowerId : superpowerIds) {
            superpowers.add(superpowerDao.getSuperpowerById(Integer.parseInt(superpowerId)));
        }

        //remove hero from organization
        List<Organization> allOrganizations = organizationDao.getAllOrganizations();
        for (Organization organization : allOrganizations) {
            List<Hero> heroes = organizationDao.getHeroesInOrganization(organization);
            List<Organization> thisOrganizations = organizationDao.getOrganizationsForHero(hero);

            if (heroes.contains(hero)) {
                heroes.remove(hero);
            }

            organization.setHeroes(heroes);
            organizationDao.updateOrganization(organization);

        }

        //update hero
        hero.setSuperpower(superpowers);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(hero);
        
        List<Organization> organizationsFromDao = organizationDao.getAllOrganizations();
        List<Superpower> superpowersFromDao = superpowerDao.getAllSuperpowers();

        Hero hero1 = heroDao.getHeroById(id);
        if (!violations.isEmpty()) {
            model.addAttribute("errors", violations);
            model.addAttribute("organizations", organizationsFromDao);
            model.addAttribute("superpowers", superpowersFromDao);
            model.addAttribute("hero", hero1);

            return "editSuper";
        }

        
        heroDao.updateHero(hero);

        for (Organization organization : organizations) {
            List<Hero> heroes = organizationDao.getHeroesInOrganization(organization);
            heroes.add(hero);

            organization.setHeroes(heroes);
            organizationDao.updateOrganization(organization);

        }

        return "redirect:/supers";
    }
    

}
