/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.sg.guessthenumber.data;

import com.sg.guessthenumber.TestApplicationConfiguration;
import com.sg.guessthenumber.models.Game;
import com.sg.guessthenumber.models.Round;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
public class RoundDBDaoIT {

    @Autowired
    RoundDao roundDao;

    @Autowired
    GameDao gameDao;

    public RoundDBDaoIT() {
    }

    @Before
    public void setUp() {
        List<Game> games = gameDao.getAllGames();
        for (Game game : games) {
            gameDao.deleteGameById(game.getGameId());
        }
    }

    /**
     * Test of addRound method and getRoundById method, of class RoundDBDao.
     */
    @Test
    public void testAddRound() {
        Game game = new Game();
        game.setAnswer("5748");
        game.setFinished(false);
        game = gameDao.add(game);

        Game gameFromDao = gameDao.getGameById(game.getGameId());
        Round round = new Round();
        round.setGameId(game.getGameId());
        round.setGuess("5832");
        round.setTimeStamp(LocalDateTime.now().withNano(0));
        round.setResult("e:1:p:1");
        round = roundDao.addRound(round);

        Round fromDao = roundDao.getRoundById(round.getRoundId());
        assertEquals(round, fromDao);

    }

    /**
     * Test of roundForGame method, of class RoundDBDao.
     */
    @Test
    public void testRoundForGame() {
        Game game = new Game();
        game.setAnswer("5748");
        game.setFinished(false);
        game = gameDao.add(game);

        Game gameFromDao = gameDao.getGameById(game.getGameId());
        Round round = new Round();
        round.setGameId(game.getGameId());
        round.setGuess("5832");
        round.setTimeStamp(LocalDateTime.now().withNano(0));
        round.setResult("e:1:p:1");
        round = roundDao.addRound(round);

        Round round2 = new Round();
        round2.setGameId(game.getGameId());
        round2.setGuess("7892");
        round2.setTimeStamp(LocalDateTime.now().withNano(0));
        round2.setResult("e:0:p:2");
        round2 = roundDao.addRound(round2);

        List<Round> rounds = roundDao.roundsForGame(game.getGameId());

        assertEquals(2, rounds.size());
        assertTrue(rounds.contains(round));
        assertTrue(rounds.contains(round2));

    }

    /**
     * Test of deleteRound method, of class RoundDBDao.
     */
    @Test
    public void testDeleteRound() {
        Game game = new Game();
        game.setAnswer("5748");
        game.setFinished(false);
        game = gameDao.add(game);

        Game gameFromDao = gameDao.getGameById(game.getGameId());
        Round round = new Round();
        round.setGameId(game.getGameId());
        round.setGuess("5832");
        round.setTimeStamp(LocalDateTime.now().withNano(0));
        round.setResult("e:1:p:1");
        round = roundDao.addRound(round);
        
        roundDao.deleteRound(round);
        Round fromDao = roundDao.getRoundById(round.getRoundId());
        assertNull(fromDao);
    }

}
