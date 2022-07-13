/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.guessthenumber.controllers;

import com.sg.guessthenumber.models.Game;
import com.sg.guessthenumber.models.Round;
import com.sg.guessthenumber.service.GameAlreadyCompleteException;
import com.sg.guessthenumber.service.GuessTheNumberService;
import com.sg.guessthenumber.service.InvalidGuessException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author adrees
 */
@RestController
@RequestMapping("/api/guessthenumber")
public class GuessTheNumberController {

    private final GuessTheNumberService service;

    public GuessTheNumberController(GuessTheNumberService service) {
        this.service = service;
    }

    @PostMapping("/guess")
    @ResponseBody
    //public Round makeGuess(@RequestBody String guess, @RequestBody String gameId) {
    public ResponseEntity<Round> makeGuess(@RequestBody Round json) throws GameAlreadyCompleteException, InvalidGuessException {

            String guess = json.getGuess();
            int gameId = json.getGameId();
         
            Round round = service.makeGuess(gameId, guess);

            return ResponseEntity.ok(round);


    }

    @GetMapping("/game")
    public List<Game> all() {
        List<Game> games = service.getAllGames();

        return games;
    }

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int createGame() {
        Game newGame = service.createGame();

        return newGame.getGameId();
    }

    @GetMapping("game/{gameId}")
    public ResponseEntity<Game> findById(@PathVariable int gameId) {
        Game result = service.getGameById(gameId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("rounds/{gameId}")
    public ResponseEntity<List<Round>> findRoundById(@PathVariable int gameId) {
        List<Round> results = service.getRoundsForGame(gameId);

        return ResponseEntity.ok(results);
    }


}
