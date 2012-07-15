package com.cs446.purfect_litter.gameLogicManager.cardManager;

public class LoveCard extends Card {
	private int value;
	
	public LoveCard(int cost, String name, int n_value) {
		super(cost, name);
		value = n_value;
	}
	
	public int getValue() {
		return value;
	}

}
