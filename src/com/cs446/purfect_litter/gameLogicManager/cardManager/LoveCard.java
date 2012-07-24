package com.cs446.purfect_litter.gameLogicManager.cardManager;

public class LoveCard extends CardDef {
	private int value;
	
	public LoveCard(int cost, String name, int n_value, int number) {
		super(cost, name, number);
		value = n_value;
		t = ctype.LOVE;
		
	}
	
	public int getValue() {
		return value;
	}

}
