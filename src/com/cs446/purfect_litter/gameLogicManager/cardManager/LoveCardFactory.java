package com.cs446.purfect_litter.gameLogicManager.cardManager;

import com.cs446.purfect_litter.R;


public class LoveCardFactory extends AbstractCardFactory{

    public void CreateCards(){
		super.Cards().add(new LoveCard(1, "One Love", 1, 36, R.drawable.cat_love1));
		super.Cards().add(new LoveCard(4, "Two Love", 2, 12, R.drawable.cat_love2));
		super.Cards().add(new LoveCard(7, "Three Love", 3, 8, R.drawable.cat_love3));
	}

}
