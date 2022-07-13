/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.guessthenumber.service;

import com.sg.guessthenumber.models.Game;
import com.sg.guessthenumber.models.Round;
import java.util.List;

/**
 *
 * @author adrees
 */
public interface GuessTheNumberService {
    
    public List<Game> getAllGames();
    
    public Game getGameById(int id) throws GameNullException;
    
    public Game createGame();
    
    public Round makeGuess(int gameId, String guess) throws GameAlreadyCompleteException, InvalidGuessException, GameNullException;
    
    public List<Round> getRoundsForGame(int id) throws GameNullException;
    
    public void deleteGame(int id);

    public boolean checkIfGameIsFinished(int gameId);
}
