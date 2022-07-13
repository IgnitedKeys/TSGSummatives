/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.guessthenumber.data;

import com.sg.guessthenumber.models.Round;
import java.util.List;

/**
 *
 * @author adrees
 */
public interface RoundDao {
    
    Round addRound(Round round);
    
    Round getRoundById(int id);
    
    List<Round> roundsForGame(int gameId);
    
    Round deleteRound(Round round);
}
