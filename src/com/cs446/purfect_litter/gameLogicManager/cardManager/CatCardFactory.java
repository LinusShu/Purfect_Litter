package com.cs446.purfect_litter.gameLogicManager.cardManager;


public class CatCardFactory extends AbstractCardFactory{
    
	public void CreateCards(){
		// chief cats (green)
		super.Cards().add(new CatCard(3, "Colette Framboise", 0, 1, 0, 0, 0, 0, 2, true/*, new Effect()*/));
		//TODO add more chief cats here
		
		// general cats (blue)
		super.Cards().add(new CatCard(6, "Opheia Grail", 1, 0, 1, 1, 1, 1, 0, true /*, new Effect()*/));
		//TODO add more general cats here
		
		// private cats (black)
		super.Cards().add(new CatCard(5, "Amber Twilight", 2, -3, 0, 0, 0, 0, 0, true /*, new Effect()*/));
		//TODO add more private cats here
	}

}
