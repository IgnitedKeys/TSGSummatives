/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.guessthenumber.service;

import com.sg.guessthenumber.data.GameDao;
import com.sg.guessthenumber.data.RoundDao;
import com.sg.guessthenumber.models.Game;
import com.sg.guessthenumber.models.Round;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 *
 * @author adrees
 */
@Component
public class GuessTheNumberServiceImpl implements GuessTheNumberService {

    GameDao gameDao;
    RoundDao roundDao;

    public GuessTheNumberServiceImpl(GameDao gameDao, RoundDao roundDao) {
        this.gameDao = gameDao;
        this.roundDao = roundDao;
    }

    @Override
    public Game createGame() {
        Game newGame = new Game();
        //get finished and answer for Game
        newGame.setFinished(false);
        String answer = randomizer();
        newGame.setAnswer(answer);
        gameDao.add(newGame);
        return newGame;
    }

    @Override
    public Round makeGuess(int gameId, String guess) throws GameAlreadyCompleteException, InvalidGuessException, GameNullException {

        Game game = gameDao.getGameById(gameId);
        if (game == null) {
            throw new GameNullException("Game doesn't exists");
        }

        if (game.isFinished()) {
            throw new GameAlreadyCompleteException("Game is already finished!");
        }

        int e = 0;
        int p = 0;
        boolean found;
        String answer = game.getAnswer();
        try {
            for (int i = 0; i < guess.length(); i++) {
                if (answer.charAt(i) == guess.charAt(i)) {
                    e++;
                } else {
                    found = false;
                    for (int j = 0; j < guess.length() && !found; j++) {
                        if (answer.charAt(i) == guess.charAt(j)) {
                            found = true;
                            p++;
                        }
                    }
                }
            }
        } catch (StringIndexOutOfBoundsException ex) {

        }

        if (e == 4) {
            //mark game finished if correct
            setGameToFinished(gameId);
        }

        String result = "e:" + e + ":p:" + p;

        Set<Integer> digits = new HashSet<>();
        for (int i = 0; i < guess.length(); i++) {
            digits.add(Integer.parseInt(guess.substring(i, i + 1)));
        }

        if (guess.length() != 4) {
            throw new InvalidGuessException("Guess must be 4 unique digits");
        }
        
        if (digits.size() != 4) {
            throw new InvalidGuessException("Guess must be 4 unique digits");
        }
        Round round = new Round();
        round.setGuess(guess);
        round.setResult(result);
        round.setTimeStamp(LocalDateTime.now());
        round.setGameId(gameId);
        roundDao.addRound(round);
        return round;

    }

    //check if answer is blank!
    @Override
    public List<Game> getAllGames() {
        List<Game> games = gameDao.getAllGames();
        for (Game game : games) {
            if (game.isFinished() == false) {
                game.setAnswer("");
            }
        }
        return games;
    }

    //check if answer is blank
    @Override
    public Game getGameById(int id) throws GameNullException {

        Game game = gameDao.getGameById(id);
        if (game == null) {
            throw new GameNullException("Please enter an existing gameId");
        }
        if (game.isFinished() == false) {
            game.setAnswer("");
        }

        return game;

    }

    @Override
    public List<Round> getRoundsForGame(int gameId) throws GameNullException {
        List<Round> results = roundDao.roundsForGame(gameId);
        Game game = gameDao.getGameById(gameId);
         if(game == null) {
             throw new GameNullException("That game doesn't exist");
         }
        if (results.isEmpty()) {
            results = new ArrayList<>();
        }

        return results;
    }

    @Override
    public void deleteGame(int id) {

        List<Round> rounds = roundDao.roundsForGame(id);
        for (Round round : rounds) {
            roundDao.deleteRound(round);
        }
        gameDao.deleteGameById(id);
    }

    private String randomizer() {
        //add zero
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        String result = "";
        for (int i = 0; i < 4; i++) {
            result += numbers.get(i).toString();
        }
        return result;
    }

    private void setGameToFinished(int gameId) {
        Game game = gameDao.getGameById(gameId);
        //update GameDao
        gameDao.updateGame(game);
    }

    @Override
    public boolean checkIfGameIsFinished(int gameId) {
        try {
            Game game = gameDao.getGameById(gameId);
            return game.isFinished();
        } catch (NullPointerException ex) {
            return false;
        }

    }

}
