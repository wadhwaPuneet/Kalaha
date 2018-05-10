package com.puneet.test.game.model;

public enum Move {
	NORMAL,			// A normal move, players will change their turn
	ONE_MORE_TURN,	// Current player gets one more chance
	CAPTURE,		// Current player will capture his opponent's stones
	INVALID			// Invalid move
}
