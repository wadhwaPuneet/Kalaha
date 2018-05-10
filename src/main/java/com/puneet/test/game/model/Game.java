package com.puneet.test.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.puneet.test.game.exception.GameException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Game {
	private final Player player1;
	private final Player player2;
	private GameStatus status;
	public static final String NAME1 = "Player 1";
    public static final String NAME2 = "Player 2";	
    
    public Game() {
    	player1 = Player.initialize(NAME1);
    	player2 = Player.initialize(NAME2);
    	status = GameStatus.PLAYER_1;
    }

    @JsonIgnore
    public Player getActivePlayer() {
    	if(!(status == GameStatus.PLAYER_1 || status == GameStatus.PLAYER_2)) {
    		throw new GameException("Game not active");
    	}
    	return status==GameStatus.PLAYER_1 ? player1 : player2;
    }

    @JsonIgnore
    public Player getOpponent() {
    	if(!(status == GameStatus.PLAYER_1 || status == GameStatus.PLAYER_2)) {
    		throw new GameException("Game not active");
    	}
    	return status==GameStatus.PLAYER_1 ? player2 : player1;
    }
}
