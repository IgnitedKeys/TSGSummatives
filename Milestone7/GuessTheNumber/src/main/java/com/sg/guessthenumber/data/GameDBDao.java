/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.guessthenumber.data;

import com.sg.guessthenumber.data.RoundDBDao.RoundMapper;
import com.sg.guessthenumber.models.Game;
import com.sg.guessthenumber.models.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author adrees
 */
@Repository
//@Profile("test")
//@Component("GameDao")
public class GameDBDao implements GameDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GameDBDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Game add(Game game) {
        final String sql = "INSERT INTO game(answer, finished) VALUES(?, ?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, game.getAnswer());
            statement.setBoolean(2, game.isFinished());
            return statement;
        }, keyHolder);

        game.setGameId(keyHolder.getKey().intValue());
        
        return game;

    }
    

    @Override
    public List<Game> getAllGames() {
        final String sql = "SELECT gameId, answer, finished FROM game;";
        return jdbcTemplate.query(sql, new GameMapper());
    }

    @Override
    public Game getGameById(int id) {
        try {
            final String sql = "SELECT gameId, answer, finished FROM game WHERE gameId=?;";
            Game game = jdbcTemplate.queryForObject(sql, new GameMapper(), id);
            //game.setRounds(getRoundsForGame(game));
            return game;
        } catch (DataAccessException ex) {
            return null;
        }

    }
    private List<Round> getRoundsForGame(Game game) {
        final String SELECT_ROUNDS_FOR_GAME = "SELECT r.* FROM Round r "
                + "JOIN Game g ON r.gameId = r.gameId WHERE g.gameId =?";
        return jdbcTemplate.query(SELECT_ROUNDS_FOR_GAME, new RoundMapper(), game.getGameId());
    }
    
//double check return-should it be void??
    @Override
    public Game updateGame(Game game) {

        game.setFinished(true);
        final String sql = "UPDATE game SET finished = ? WHERE gameId = ?;";

        jdbcTemplate.update(sql, game.isFinished(), game.getGameId());
        return game;
    }
//void or Game
    @Override
    public void deleteGameById(int id) {

        final String DELETE_ROUND = "DELETE FROM round WHERE gameId = ?";
        jdbcTemplate.update(DELETE_ROUND, id);

        final String DELETE_GAME = "DELETE FROM game WHERE gameId = ?";
        jdbcTemplate.update(DELETE_GAME, id);
        
        
        
        
    }

    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setGameId(rs.getInt("gameId"));
            game.setAnswer(rs.getString("answer"));
            game.setFinished(rs.getBoolean("finished"));
            return game;
        }
    }

}
