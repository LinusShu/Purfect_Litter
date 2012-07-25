package com.cs446.purfect_litter.gameLogicManager.cardManager;


public class CatCard extends CardDef{
	private int colour, vp, draw, love, action, purchase, chamberCost;
	boolean hasVP;
	//TODO private Effect effect;

	// colour is assign as: Chief/green=0; General/blue=1; Private/black=2.
	public CatCard(int cost, String name, int nColour, int nVP, int nDraw,
			int nLove, int nAction, int nPurchase, int nChamberCost, boolean nHasVP, 
			int number, int image/*Effect effect*/){
		super(cost,name,number,image);
		colour = nColour;
		vp = nVP;
		draw = nDraw;
		love = nLove;
		action = nAction;
		purchase = nPurchase;
		chamberCost = nChamberCost;
		hasVP = nHasVP;
		t = ctype.CAT;
	}
	
	public int getVP() {
		return vp;
	}
	
	public int getColor(){
		return colour;
	}
		
	public int getDraw() {
		return draw;
	}
	
	public int getLove() {
		return love;
	}
	
	public int getAction() {
		return action;
	}
	
	public int getPurchase() {
		return purchase;
	}
	
	public int getChamberCost(){
		return chamberCost;
	}
	
	public boolean getHasVP(){
		return hasVP;
	}

	/*public Effect getEffect() {
	 * 	return effect;
	 * }
	 */
	
}
