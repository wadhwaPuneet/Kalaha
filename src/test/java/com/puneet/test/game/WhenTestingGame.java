package com.puneet.test.game;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.puneet.test.game.exception.GameException;
import com.puneet.test.game.exception.InvalidMoveException;
import com.puneet.test.game.model.Game;
import com.puneet.test.game.model.GameStatus;
import com.puneet.test.game.service.GameService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WhenTestingGame {
	
	@Autowired
	private GameService service;
	
	@Test(expected = GameException.class)
	public void should_throw_exception_if_game_not_been_started_and_we_check_winner() {
		service.getWinner();
	}
	
	@Test
	public void should_set_player_1_as_status_initially() {
		Game game = service.getCurrentStateOfGame();
		assertThat(game.getStatus()).isEqualByComparingTo(GameStatus.PLAYER_1);
	}

	@Test(expected = InvalidMoveException.class)
	public void should_throw_exception_illegal_move_negative() {
		service.move(-1);
	}
	
	@Test(expected = InvalidMoveException.class)
	public void should_throw_exception_illegal_move_higher() {
		service.move(10);
	}

	// This test will emulate an empty pit. As in first turn last stone will end up
	// in kalaha and turn will remain with same player. And if player again uses
	// the same pit it would be empty
	@Test(expected = InvalidMoveException.class)
	public void should_throw_exception_illegal_move_empty_pit() {
		service.move(0);
		service.move(0);
	}
}
