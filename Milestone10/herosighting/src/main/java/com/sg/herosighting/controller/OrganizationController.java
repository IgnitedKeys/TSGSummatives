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
import com.sg.herosighting.dto.Organization;
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
public class OrganizationController {

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

    Set<ConstraintViolation<Organization>> violations = new HashSet<>();

    @GetMapping("/organizations")
    public String displayOrganizations(Model model) {
        model.addAttribute("errors", violations);

        violations.clear();
        List<Organization> organizations = organizationDao.getAllOrganizations();

        List<Organization> sortedOrganizations = new ArrayList<Organization>();
        Comparator<Organization> nameComparator = Comparator.comparing(Organization::getOrganizationName, String.CASE_INSENSITIVE_ORDER);
        sortedOrganizations = organizations.stream().sorted(nameComparator).collect(Collectors.toList());

        model.addAttribute("organizations", sortedOrganizations);

        return "organizations";
    }

    @GetMapping("/addOrganization")
    public String addOrganization(Model model) {
        model.addAttribute("errors", violations);
        return "addOrganization";
    }

    @PostMapping("/addOrganization")
    public String addOrganization(HttpServletRequest request, Model model) {

        String organizationName = request.getParameter("organizationName");
        String organizationDescription = request.getParameter("organizationDescription");
        String contact = request.getParameter("contact");
        String address = request.getParameter("address");

        Organization organization = new Organization();
        organization.setOrganizationName(organizationName);
        organization.setOrganizationDescription(organizationDescription);
        organization.setContact(contact);
        organization.setAddress(address);

        List<Hero> heroes = new ArrayList<>();
        organization.setHeroes(heroes);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(organization);

        if (!violations.isEmpty()) {
            model.addAttribute("errors", violations);
            model.addAttribute("organization", organization);
            return "addOrganization";
        }
        organizationDao.addOrganization(organization);

        return "redirect:/organizations";
    }

    @GetMapping("/organization")
    public String displayOrganization(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        List<Hero> heroes = organizationDao.getHeroesInOrganization(organization);
        model.addAttribute("organization", organization);

        List<Hero> sortedHeroes = new ArrayList<Hero>();
        Comparator<Hero> nameComparator = Comparator.comparing(Hero::getHeroName, String.CASE_INSENSITIVE_ORDER);
        sortedHeroes = heroes.stream().sorted(nameComparator).collect(Collectors.toList());

        model.addAttribute("heroes", sortedHeroes);
        return "organization";
    }

    @GetMapping("/editOrganization")
    public String editOrganization(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        //check on HERO
        model.addAttribute("organization", organization);

        return "editOrganization";
    }

    @PostMapping("/editOrganization")
    public String performEditOrganization(@Valid Organization organization, BindingResult result, HttpServletRequest request, Model model) {

        List<Hero> heroes = organizationDao.getHeroesInOrganization(organization);
        if (heroes.isEmpty()) {
            heroes = new ArrayList<>();
        }
        organization.setHeroes(heroes);

        if (result.hasErrors()) {
            model.addAttribute("organization", organization);
            return "editOrganization";
        }
        organizationDao.updateOrganization(organization);
        return "redirect:/organizations";
    }

    @GetMapping("/deleteOrganization")
    public String deleteOrganization(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", organization);

        return "deleteOrganization";
    }

    @PostMapping("/deleteOrganization")
    public String performDeleteOrganization(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("organizationId"));

        organizationDao.deleteOrganizationById(id);
        return "redirect:/organizations";
    }

}
