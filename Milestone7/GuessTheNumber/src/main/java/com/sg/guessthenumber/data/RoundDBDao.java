/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.guessthenumber.data;

import com.sg.guessthenumber.data.GameDBDao.GameMapper;
import com.sg.guessthenumber.models.Game;
import com.sg.guessthenumber.models.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author adrees
 */
@Repository
public class RoundDBDao implements RoundDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoundDBDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Round addRound(Round round) {
        final String sql = "INSERT INTO round(gameId, guess, timeStamp, result) VALUES(?, ?, ?, ?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, round.getGameId());
            statement.setString(2, round.getGuess());
            statement.setTimestamp(3, Timestamp.valueOf(round.getTimeStamp()));
            statement.setString(4, round.getResult());
            return statement;
        }, keyHolder);
        //int newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setRoundId(keyHolder.getKey().intValue());
        return round;
    }

//    @Override
//    @Transactional
//    public Round addRound(Round round) {
//        final String sql = "INSERT INTO round(gameId, guess, timeStamp, result) VALUES(?, ?, ?, ?);";
//
//        jdbcTemplate.update(sql, round.getGameId(), round.getGuess(), Timestamp.valueOf(round.getTimeStamp()),round.getResult());
//        int newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
//        round.setRoundId(newId);
//        return round;
//    }

    @Override
    public List<Round> roundsForGame(int gameId) {
        final String sql = "SELECT * FROM round WHERE gameId =?";
        List<Round> rounds = jdbcTemplate.query(sql, new RoundMapper(), gameId);
        return rounds;
    }

    //check the Round round vs roundId
    //void vs Round
    @Override
    public Round deleteRound(Round round) {
        final String DELETE_ROUND = "DELETE FROM round WHERE roundId = ?";
        jdbcTemplate.update(DELETE_ROUND, round.getRoundId());
        return round;
    }

    @Override
    public Round getRoundById(int id) {
        try {
            final String sql = "SELECT roundId, gameId, guess, timeStamp, result FROM round WHERE roundId=?;";
            Round round = jdbcTemplate.queryForObject(sql, new RoundMapper(), id);
            //round.setGame(gameForRound(round));
            return round;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    public static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setRoundId(rs.getInt("roundId"));
            //round.setGame(rs.);
            round.setGameId(rs.getInt("gameId"));
            round.setGuess(rs.getString("guess"));
            round.setTimeStamp(rs.getTimestamp("timeStamp").toLocalDateTime());
            round.setResult(rs.getString("result"));

            return round;
        }
    }

}
