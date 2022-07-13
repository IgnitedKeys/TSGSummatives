/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.sg.guessthenumber.data;

import com.sg.guessthenumber.TestApplicationConfiguration;
import com.sg.guessthenumber.TestApplicationConfiguration;
import com.sg.guessthenumber.models.Game;
import com.sg.guessthenumber.models.Round;
import java.time.LocalDateTime;
import java.util.List;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author adrees
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GameDBDaoIT {
    
    @Autowired
    GameDao gameDao;
    
    @Autowired
    RoundDao roundDao;
    
    public GameDBDaoIT() {
    }
    
    @Before
    public void setUp() {
        //delete all the rounds to a game before deleting the game
        List<Game> games = gameDao.getAllGames();
        for(Game game : games) {
            gameDao.deleteGameById(game.getGameId());
        }
    }
    
    /**
     * Test of add method and findById method, of class GameDBDao.
     */
    @Test
    public void testAddAndGetById() {
        Game game = new Game();
       // game.setGameId(1);
        game.setAnswer("6743");
        game.setFinished(false);
        gameDao.add(game);
        
        Game fromDao = gameDao.getGameById(game.getGameId());
        
        assertEquals(game, fromDao);
    }

    /**
     * Test of getAllGames method, of class GameDBDao.
     */
    @Test
    public void testGetAllGames() {
        Game game = new Game();
        game.setAnswer("3579");
        game.setFinished(false);
        gameDao.add(game);
        
        Game game2 = new Game();
        game2.setAnswer("5187");
        game2.setFinished(false);
        gameDao.add(game2);
        
        List<Game> games = gameDao.getAllGames();
        
        assertEquals(2, games.size());
        assertTrue(games.contains(game));
        assertTrue(games.contains(game2));
    }

    /**
     * Test of updateGame method, of class GameDBDao.
     */
    @Test
    public void testUpdateGame() {
        Game game = new Game();
        game.setAnswer("5387");
        game.setFinished(false);
        gameDao.add(game);
        
        Game fromDao = gameDao.getGameById(game.getGameId());
        assertEquals(game, fromDao);
        
        //update game
        game.setFinished(true);
        gameDao.updateGame(game);
        
        assertNotEquals(game, fromDao);
        
        fromDao = gameDao.getGameById(game.getGameId());
        assertEquals(game, fromDao);
//        //update answer
//        game.setAnswer("1234");
//        
//        gameDao.updateGame(game);
//        assertNotEquals(game, fromDao);
//        
//        fromDao = gameDao.getGameById(game.getGameId());
//        assertEquals(game, fromDao);
        
        
    }
    
    @Test
    public void testDeleteGame(){
        Game game = new Game();
        game.setAnswer("3579");
        game.setFinished(false);
        gameDao.add(game);
        
        Round round = new Round();
        //round.setGameId(game.getGameId());
        round.setGameId(game.getGameId());
        round.setTimeStamp(LocalDateTime.now());
        round.setGuess("4765");
        round.setResult("e:0:p:2");
        roundDao.addRound(round);
        
        gameDao.deleteGameById(game.getGameId());
        
        Game fromDao = gameDao.getGameById(game.getGameId());
        assertNull(fromDao);
    }
}
