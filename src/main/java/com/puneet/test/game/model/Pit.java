package com.puneet.test.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Pit {
	
	private static final int STONES_PER_PIT = 6;
	private final int pitNumber;
	private int numberOfStones;
	
	public static Pit initialize(int pitNumber) {
		return Pit.builder().numberOfStones(STONES_PER_PIT).pitNumber(pitNumber).build();
	}

	public void addStone() {
		numberOfStones = numberOfStones + 1;
	}
	
	public int takeStones() {
		int count = numberOfStones;
		numberOfStones = 0;
		return count;
	}
	
	@JsonIgnore
	public boolean isEmpty() {
		return numberOfStones == 0;
	}
	
	@JsonIgnore
	public boolean isNotEmpty() {
		return numberOfStones >= 1;
	}
}
