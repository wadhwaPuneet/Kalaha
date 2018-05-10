package com.puneet.test.game.service;

import org.springframework.stereotype.Service;

import com.puneet.test.game.exception.GameException;
import com.puneet.test.game.exception.InvalidMoveException;
import com.puneet.test.game.model.Game;
import com.puneet.test.game.model.GameStatus;
import com.puneet.test.game.model.Kalaha;
import com.puneet.test.game.model.Move;
import com.puneet.test.game.model.Pit;
import com.puneet.test.game.model.Player;

@Service
public class GameService {
	private static final int PITS_PER_PLAYER = 6;
	private Game game = new Game();

	public Game getCurrentStateOfGame() {
		return this.game;
	}

	public Game move(int index) {
		Move move = makeTheMove(index);
		switch(move) {
		case NORMAL:
		case CAPTURE:
			game = game.toBuilder().status(changePlayer()).build();
			break;
		case ONE_MORE_TURN:
			break;
		case INVALID:
			throw new InvalidMoveException("Invalid Move!!");
		}
		if(gameFinished()) {
			game = game.toBuilder().status(GameStatus.FINISHED).build();
		}
		return game;
	}
	
	private GameStatus changePlayer() {
    	GameStatus status = game.getStatus();
		if(!(status == GameStatus.PLAYER_1 || status == GameStatus.PLAYER_2)) {
    		throw new GameException("Game not active");
    	}

		return status == GameStatus.PLAYER_1 ? GameStatus.PLAYER_2 : GameStatus.PLAYER_1;
	}

	private Move makeTheMove(int index) {
		if(invalidMove(index)) {
			return Move.INVALID;
		}
		
		Move move = Move.NORMAL;
		boolean isOponentArea = false;
		Player activePlayer = game.getActivePlayer();
		int numberOfStonesRemoved = activePlayer.getSelectedPit(index).takeStones();
		for (int i = 0; i < numberOfStonesRemoved; i++) {
			index++;
			move = Move.NORMAL;
			Pit nextPit = activePlayer.getSelectedPit(index);
			if (nextPit != null) {
				if (!isOponentArea && nextPit.isEmpty()) {
					move = Move.CAPTURE;
				}
				nextPit.addStone();
			} else {
				// First time you move out of your pits, you will get your Kalaha. After that it would be opponent's pits.
				if (!isOponentArea) {
					Kalaha kalaha = activePlayer.getKalaha();
					kalaha.addStone();
					move = Move.ONE_MORE_TURN;
				} else {
					// add the stone back to play at opponent's area in next iteration
					i = i - 1;
				}
				activePlayer = game.getOpponent();
				isOponentArea = !isOponentArea;
				index = -1;
			}

		}

		if (move == Move.CAPTURE) {
		    game = capture(index);
		}
		return move;
	}
	
	private boolean invalidMove(int index) {
		if (index < 0 || index > 5) {
			return true;
		}
		return game.getActivePlayer().getSelectedPit(index).isEmpty();
	}

	public Player getWinner() {
		if (game.getStatus() != GameStatus.FINISHED) {
			throw new GameException("Game not finished yet, still in progress");
		}
		if (game.getPlayer1().getKalaha().size() > game.getPlayer2().getKalaha().size()) {
			return game.getPlayer1();
		}
		else if (game.getPlayer2().getKalaha().size() > game.getPlayer1().getKalaha().size()) {
			return game.getPlayer2();
		}
		// it's a tie!
		return null;
	}
	
	public Game capture(int index) {
		Player activePlayer = game.getActivePlayer();
		Player opponent = game.getOpponent();
		int activePlayersStones = activePlayer.getSelectedPit(index).takeStones();
		
		int opponentIndex = (PITS_PER_PLAYER - 1) - index;
		int opponentStones = opponent.getSelectedPit(opponentIndex).takeStones();
		
		Kalaha kalaha =  activePlayer.getKalaha();
		Kalaha updatedKalaha = kalaha.toBuilder().numberOfStones(kalaha.size() + activePlayersStones + opponentStones).build();

		Player updatedPlayer = activePlayer.toBuilder().kalaha(updatedKalaha).build();
		return game.toBuilder()
				.player1(updatedPlayer.getName().equals(Game.NAME1) ? updatedPlayer : opponent)
				.player2(updatedPlayer.getName().equals(Game.NAME2) ? updatedPlayer : opponent)
				.build();
	}

	private boolean gameFinished() {
		return !(game.getPlayer1().hasStonesLeft() && game.getPlayer2().hasStonesLeft());
	}

}
