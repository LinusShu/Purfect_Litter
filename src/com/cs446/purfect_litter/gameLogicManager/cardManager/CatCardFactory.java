package com.cs446.purfect_litter.gameLogicManager.cardManager;


public class CatCardFactory extends AbstractCardFactory{
    
	public void CreateCards(){
		// chief cats (green)
		super.Cards().add(new CatCard(3, "Colette Framboise", 0, 1, 0, 0, 0, 0, 2, true, 24/*, new Effect()*/));
		super.Cards().add(new CatCard(9, "Marianna Soleil", 0, 6, 0, 0, 0, 0, 0, true, 8/*, new Effect()*/));
		
		// general cats (blue)
		super.Cards().add(new CatCard(6, "Opheia Grail", 1, 0, 1, 1, 1, 1, 0, true, 8 /*, new Effect()*/));
		super.Cards().add(new CatCard(7, "Anise Greenaway", 1, 3, 3, 0, 0, 1, 0, true, 8 /*, new Effect()*/));
		super.Cards().add(new CatCard(5, "Sainsbury Lockwood", 1, 0, 0, 0, 0, 0, 0, true, 10 /*, new Effect()*/));
		super.Cards().add(new CatCard(5, "Tenalys Trent", 1, 0, 0, 3, 0, 1, 0, false, 10 /*, new Effect()*/));
		super.Cards().add(new CatCard(5, "Nena Wilder", 1, 0, 0, 1, 0, 0, 0, false, 10 /*, new Effect()*/));
		super.Cards().add(new CatCard(5, "Natsumi Fujikawa", 1, 0, 1, 0, 2, 0, 0, false, 10 /*, new Effect()*/));
		super.Cards().add(new CatCard(4, "Esquine Foret", 1, 0, 2, 0, 0, 0, 0, false, 10 /*, new Effect()*/));
		super.Cards().add(new CatCard(4, "Genevieve Daubigny", 1, 0, 1, 1, 1, 0, 0, false, 10 /*, new Effect()*/));
		super.Cards().add(new CatCard(4, "Moine de LeFevre", 1, 0, 2, 0, 0, 2, 0, false, 10 /*, new Effect()*/));
		super.Cards().add(new CatCard(3, "Eliza Rosewater", 1, 0, 0, 2, 0, 0, 0, false, 10 /*, new Effect()*/));
		super.Cards().add(new CatCard(3, "Kagari Ichinomiya", 1, 0, 0, 0, 2, 0, 0, false, 10 /*, new Effect()*/));
		super.Cards().add(new CatCard(3, "Claire St.Juste", 1, 0, 0, 0, 1, 0, 0, false, 10 /*, new Effect()*/));
		super.Cards().add(new CatCard(3, "Safran Virginie", 1, 0, 0, 2, 0, 0, 1, true, 10 /*, new Effect()*/));
		super.Cards().add(new CatCard(2, "Azure Crescent", 1, 1, 0, 0, 0, 1, 1, true, 10 /*, new Effect()*/));
		super.Cards().add(new CatCard(2, "Viola Crescent", 1, 1, 1, 0, 0, 0, 1, true, 10 /*, new Effect()*/));
		super.Cards().add(new CatCard(2, "Rouge Crescent", 1, 1, 0, 1, 0, 0, 1, true, 10 /*, new Effect()*/));
		
		//TODO add more general cats here
		
		// private cats (black)
		//super.Cards().add(new CatCard(5, "Amber Twilight", 2, -3, 0, 0, 0, 0, 0, true /*, new Effect()*/));
		//TODO add more private cats here
	}

}
