package com.puneet.test.game.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Player {
	private static final int PITS_PER_PLAYER = 6;
	private final String name;
	private final Kalaha kalaha;
	private final List<Pit> pits;

	public static Player initialize(String name) {
		return Player.builder()
				.name(name)
				.kalaha(Kalaha.builder().build())
				.pits(initializePits())
				.build();
	}
	
	public static List<Pit> initializePits() {
		return IntStream.range(0, PITS_PER_PLAYER)
		.mapToObj(Pit::initialize)
		.collect(Collectors.toList());
	}

	public Pit getSelectedPit(int index) {
		if (index < 0 || index >= pits.size()) {
		    return null;
		}
		return pits.get(index);
	}
	
	public boolean hasStonesLeft() {
		return this.pits.stream()
			.filter(Pit::isNotEmpty)
			.findAny()
			.isPresent();
	}
}
