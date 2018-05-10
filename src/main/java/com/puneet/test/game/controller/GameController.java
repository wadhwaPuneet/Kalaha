package com.puneet.test.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.puneet.test.game.model.Game;
import com.puneet.test.game.model.GameRequest;
import com.puneet.test.game.model.Player;
import com.puneet.test.game.service.GameService;

@RestController
@RequestMapping(value = "/game", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameController {
	private final GameService service;
	
	@Autowired
	public GameController(GameService service) {
		this.service = service;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Game getGame() {
		return service.getCurrentStateOfGame();
	}

	@GetMapping("/winner")
	@ResponseStatus(HttpStatus.OK)
	public Player getWinner() {
		return service.getWinner();
	}

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Game makeAMove(@RequestBody GameRequest request) {
		return service.move(request.getIndex());
	}
}
