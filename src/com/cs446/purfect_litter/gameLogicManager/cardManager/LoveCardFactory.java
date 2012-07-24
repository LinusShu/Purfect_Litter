package com.cs446.purfect_litter.gameLogicManager.cardManager;


public class LoveCardFactory extends AbstractCardFactory{

    public void CreateCards(){
		super.Cards().add(new LoveCard(1, "One Love", 1, 36));
		super.Cards().add(new LoveCard(4, "Two Love", 2, 12));
		super.Cards().add(new LoveCard(7, "Three Love", 3, 8));
	}

}
