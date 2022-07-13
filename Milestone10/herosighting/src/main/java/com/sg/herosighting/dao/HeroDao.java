/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sg.herosighting.dao;

import com.sg.herosighting.dto.Hero;
import java.util.List;

/**
 *
 * @author adrees
 */
public interface HeroDao {

    Hero getHeroById(int id);

    List<Hero> getAllHeroes();

    Hero addHero(Hero hero);

    void updateHero(Hero hero);

    void deleteHeroById(int id);
}
