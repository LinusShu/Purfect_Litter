package com.cs446.purfect_litter.LogicManager.CardManager;

public class CardDef {
	public enum ctype {
		CAT, LOVE, EVENT
	}
	private int cost;
	private String name;
	protected ctype t;
	private int number;
	private int image;
	
	
	
	public CardDef(int cost, String name, int number, int image) {
		this.cost = cost;
		this.name = name;
		this.number = number;
		this.image = image;
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
	
	public int getImage(){
		return image;
	}
}



