/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.sg.guessthenumber.service;

import com.sg.guessthenumber.TestApplicationConfiguration;
import com.sg.guessthenumber.data.GameDao;
import com.sg.guessthenumber.data.RoundDao;
import com.sg.guessthenumber.models.Game;
import com.sg.guessthenumber.models.Round;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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
public class GuessTheNumberServiceImplIT {

    @Autowired
    GuessTheNumberService service;

    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    public GuessTheNumberServiceImplIT() {

    }

    @Before
    public void setUp() {

        List<Game> games = service.getAllGames();
        for (Game game : games) {
            service.deleteGame(game.getGameId());
        }

    }

    /**
     * Test of caluculateResults method, of class GuessTheNumberServiceImpl.
     */
    @Test
    public void testCaluculateResults() throws GameAlreadyCompleteException, InvalidGuessException, GameNullException {
        Game game = new Game();
        game.setGameId(1);
        game.setFinished(false);
        game.setAnswer("1234");
        //service.addGame(game);
        gameDao.add(game);

        Round roundFromDao = service.makeGuess(game.getGameId(), "5234");
        assertEquals(roundFromDao.getResult(), "e:3:p:0");

        Round roundFromDao2 = service.makeGuess(game.getGameId(), "4321");
        assertEquals(roundFromDao2.getResult(), "e:0:p:4");

    }

    @Test
    public void testGameFinished() throws GameAlreadyCompleteException, InvalidGuessException, GameNullException {
        Game game = service.createGame();
        assertEquals(game.isFinished(), false);
        //create a won game
        Round roundFromDao = service.makeGuess(game.getGameId(), game.getAnswer());
        Game gameFromDao = service.getGameById(game.getGameId());

        //check if game's status is changed
        assertEquals(gameFromDao.isFinished(), true);
        assertEquals(roundFromDao.getResult(), "e:4:p:0");
    }

    @Test
    public void testGameAlreadyCompleteException() throws GameAlreadyCompleteException, GameNullException, InvalidGuessException {
        Game game = service.createGame();
        assertEquals(game.isFinished(), false);
        //create a won game
        service.makeGuess(game.getGameId(), game.getAnswer());

        try {
            service.makeGuess(game.getGameId(), "1234");
            fail("Exception was not thrown");
        } catch (GameNullException | InvalidGuessException ex) {
            fail("Incorrect Exception thrown");
        } catch (GameAlreadyCompleteException ex) {
            return;
        }
    }

    /**
     * Test of getAllGames method, of class GuessTheNumberServiceImpl.
     */
    @Test
    public void testGetAllGames() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(true);

        Game testGame = gameDao.add(game);

        assertEquals(1, service.getAllGames().size());
        assertTrue(service.getAllGames().contains(testGame));
    }

    /**
     * Test of getGameById method, of class GuessTheNumberServiceImpl.
     */
    @Test
    public void testGetGameById() throws GameNullException {
        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(true);

        Game testGame = gameDao.add(game);

        Game getGame = service.getGameById(testGame.getGameId());
        assertEquals(getGame, testGame);

        try {
            Game nullGame = service.getGameById(testGame.getGameId() + 1);
            assertNull(nullGame);
            fail("GameNullException not thrown");
        } catch (GameNullException ex) {

        }

    }

    /**
     * Test of createAnswer method, of class GuessTheNumberServiceImpl.
     */
    @Test
    public void testCreateAnswer() {
        //CHECK FOR DUPLICATES??
        Game game = service.createGame();
        //check if created game's status is false
        assertEquals(game.isFinished(), false);
    }

    @Test
    public void testGetAllRounds() throws GameNullException {
        Game game = service.createGame();
        Round round = new Round();
        round.setGameId(game.getGameId());
        round.setGuess("");
        round.setTimeStamp(LocalDateTime.now().withNano(0));
        round.setResult("");

        Round round2 = new Round();
        round2.setGameId(game.getGameId());
        round2.setGuess("5432");
        round2.setTimeStamp(LocalDateTime.now().withNano(0));
        round2.setResult("");

        Round fromDao = roundDao.addRound(round);
        Round fromDao2 = roundDao.addRound(round2);

        Game game2 = service.createGame();
        Round round3 = new Round();
        round3.setGameId(game2.getGameId());
        round3.setGuess("");
        round3.setTimeStamp(LocalDateTime.now().withNano(0));
        round3.setResult("");

        Round fromDao3 = roundDao.addRound(round3);

        assertEquals(2, service.getRoundsForGame(game.getGameId()).size());
        assertTrue(service.getRoundsForGame(game.getGameId()).contains(fromDao));
        assertTrue(service.getRoundsForGame(game.getGameId()).contains(fromDao2));

        assertFalse(service.getRoundsForGame(game.getGameId()).contains(fromDao3));
        assertNotEquals(service.getRoundsForGame(game.getGameId()), service.getRoundsForGame(game2.getGameId()));
    }

    @Test
    public void testEmptyAndNullGetAllRounds() throws GameNullException {
        Game game = service.createGame();
        List<Round> results = service.getRoundsForGame(game.getGameId());
        assertTrue(results.isEmpty());

        try {
            service.getRoundsForGame(5);
            fail("Should throw exception");
        } catch (GameNullException ex) {

        }

    }

    @Test
    public void testFourUniqueDigits() {
        Game game = service.createGame();
        String answer = game.getAnswer();
        Set<Integer> digits = new HashSet<>();

        for (int i = 0; i < answer.length(); i++) {
            digits.add(Integer.parseInt(answer.substring(i, i + 1)));
        }

        assertEquals(digits.size(), 4);

    }

    @Test
    public void testInvalidGuess() throws GameAlreadyCompleteException, InvalidGuessException, GameNullException {
        Game game = service.createGame();

        //test length
        try {
            service.makeGuess(game.getGameId(), "312");
        } catch (GameNullException | GameAlreadyCompleteException ex) {
            fail("Wrong exception thrown");
        } catch (InvalidGuessException ex) {
            
        }
        
        //test duplicate
        try {
            service.makeGuess(game.getGameId(), "5555");
        } catch (GameNullException | GameAlreadyCompleteException ex) {
            fail("Wrong exception thrown");
        } catch (InvalidGuessException ex) {
            
        }

    }
}
