package com.cs446.purfect_litter.gameLogicManager.cardManager;


public class EventCardFactory extends AbstractCardFactory{
	
	public void CreateCards(){
		super.Cards().add(new LoveCard(1, "Illness", 0/*, new Effect()*/));
		super.Cards().add(new LoveCard(4, "Bad Habit", -1 /*, new Effect()*/));
	}

}
