/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.guessthenumber.data;

import com.sg.guessthenumber.models.Game;
import java.util.List;

/**
 *
 * @author adrees
 */

public interface GameDao {
    
    //add game to db
    Game add(Game game);
    
    //return list of all games
    List<Game> getAllGames();
    
    //return game by id
    Game getGameById(int id);
    
    Game updateGame(Game game);
    
    void deleteGameById(int id);
    //ADD MORE?
    
}
