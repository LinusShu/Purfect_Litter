package com.cs446.purfect_litter.LogicManager.CardManager;

public class LoveCard extends CardDef {
	private int value;
	
	public LoveCard(int cost, String name, int n_value, int number, int image) {
		super(cost, name, number, image);
		value = n_value;
		t = ctype.LOVE;
		
	}
	
	public int getValue() {
		return value;
	}

}
