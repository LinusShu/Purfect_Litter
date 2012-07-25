package com.cs446.purfect_litter.gameLogicManager.cardManager;

import com.cs446.purfect_litter.R;


public class CatCardFactory extends AbstractCardFactory{
    
	public void CreateCards(){
		// chief cats (green)
		super.Cards().add(new CatCard(3, "Colette Framboise", 0, 1, 0, 0, 0, 0, 2, true, 24, R.drawable.card_cat17/*, new Effect()*/));
		super.Cards().add(new CatCard(9, "Marianna Soleil", 0, 6, 0, 0, 0, 0, 0, true, 8, R.drawable.card_cat18/*, new Effect()*/));
		
		// general cats (blue)
		super.Cards().add(new CatCard(6, "Ophelia Grail", 1, 0, 1, 1, 1, 1, 0, true, 8, R.drawable.card_cat16 /*, new Effect()*/));
		super.Cards().add(new CatCard(7, "Anise Greenaway", 1, 3, 3, 0, 0, 1, 0, true, 8, R.drawable.card_cat5 /*, new Effect()*/));
		super.Cards().add(new CatCard(5, "Sainsbury Lockwood", 1, 0, 0, 0, 0, 0, 0, true, 10, R.drawable.card_cat6 /*, new Effect()*/));
		super.Cards().add(new CatCard(5, "Tenalys Trent", 1, 0, 0, 3, 0, 1, 0, false, 10, R.drawable.card_cat12 /*, new Effect()*/));
		super.Cards().add(new CatCard(5, "Nena Wilder", 1, 0, 0, 1, 0, 0, 0, false, 10, R.drawable.card_cat14 /*, new Effect()*/));
		super.Cards().add(new CatCard(5, "Natsumi Fujikawa", 1, 0, 1, 0, 2, 0, 0, false, 10, R.drawable.card_cat13 /*, new Effect()*/));
		super.Cards().add(new CatCard(4, "Esquine Foret", 1, 0, 2, 0, 0, 0, 0, false, 10, R.drawable.card_cat1 /*, new Effect()*/));
		super.Cards().add(new CatCard(4, "Genevieve Daubigny", 1, 0, 1, 1, 1, 0, 0, false, 10, R.drawable.card_cat10 /*, new Effect()*/));
		super.Cards().add(new CatCard(4, "Moine de LeFevre", 1, 0, 2, 0, 0, 2, 0, false, 10, R.drawable.card_cat15 /*, new Effect()*/));
		super.Cards().add(new CatCard(3, "Eliza Rosewater", 1, 0, 0, 2, 0, 0, 0, false, 10, R.drawable.card_cat7 /*, new Effect()*/));
		super.Cards().add(new CatCard(3, "Kagari Ichinomiya", 1, 0, 0, 0, 2, 0, 0, false, 10, R.drawable.card_cat3 /*, new Effect()*/));
		super.Cards().add(new CatCard(3, "Claire St.Juste", 1, 0, 0, 0, 1, 0, 0, false, 10, R.drawable.card_cat4 /*, new Effect()*/));
		super.Cards().add(new CatCard(3, "Safran Virginie", 1, 0, 0, 2, 0, 0, 1, true, 10, R.drawable.card_cat11 /*, new Effect()*/));
		super.Cards().add(new CatCard(2, "Azure Crescent", 1, 1, 0, 0, 0, 1, 1, true, 10, R.drawable.card_cat8 /*, new Effect()*/));
		super.Cards().add(new CatCard(2, "Viola Crescent", 1, 1, 1, 0, 0, 0, 1, true, 10, R.drawable.card_cat2 /*, new Effect()*/));
		super.Cards().add(new CatCard(2, "Rouge Crescent", 1, 1, 0, 1, 0, 0, 1, true, 10, R.drawable.card_cat9 /*, new Effect()*/));
		
		//TODO add more general cats here
		
		// private cats (black)
		//super.Cards().add(new CatCard(5, "Amber Twilight", 2, -3, 0, 0, 0, 0, 0, true /*, new Effect()*/));
		//TODO add more private cats here
	}

}
