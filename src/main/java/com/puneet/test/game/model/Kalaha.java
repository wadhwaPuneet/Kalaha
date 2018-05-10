package com.puneet.test.game.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Kalaha {
	private int numberOfStones;
	
	public int size() {
		return numberOfStones;
	}
	
	public void addStone() {
		numberOfStones = numberOfStones + 1;
	}
}
