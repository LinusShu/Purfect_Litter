package com.cs446.purfect_litter.LogicManager.CardManager;

public class Card {
	private int cost;
	private String name;
	
	public Card(int cost, String name) {
		this.cost = cost;
		this.name = name;
	}

	public int getCost() {
		return cost;
	}
	
	public String getName(){
		return name;
	}
	
}



