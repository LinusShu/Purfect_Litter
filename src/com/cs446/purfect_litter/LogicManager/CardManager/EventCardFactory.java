package com.cs446.purfect_litter.LogicManager.CardManager;

import com.cs446.purfect_litter.R;


public class EventCardFactory extends AbstractCardFactory{
	
	public void CreateCards(){
		super.Cards().add(new LoveCard(1, "Illness", 0, 10, R.drawable.card_illness/*, new Effect()*/));
		super.Cards().add(new LoveCard(4, "Bad Habit", -1, 16, R.drawable.card_badhabit /*, new Effect()*/));
	}

}
