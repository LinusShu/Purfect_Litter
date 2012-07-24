package com.cs446.purfect_litter.gameLogicManager.cardManager;

public class CardDef {
	public enum ctype {
		CAT, LOVE, EVENT
	}
	private int cost;
	private String name;
	protected ctype t;
	private int number;
	
	
	
	public CardDef(int cost, String name, ctype t) {
		this.cost = cost;
		this.name = name;
		this.t = t;
	}

	public int getCost() {
		return cost;
	}
	
	public String getName(){
		return name;
	}
	
	public ctype getType(){
		return t;
	}
	
	public int getNumber(){
		return number;
	}
}



