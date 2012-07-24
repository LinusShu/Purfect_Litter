package com.cs446.purfect_litter.gameLogicManager.cardManager;

import java.util.Random;

public static class CardDefLib {
	public static ArrayList<CardDef> lib = new ArrayList<CardDef>();

	// create card definitions
	public static void populate() 
	{
		Random r= new Random();
		
		lib.clear();
		LoveCardFactory lcf = new LoveCardFactory();
		lcf.CreateCards();
		lib.addAll(lcf.Cards());
		CatCardFactory ccf = new CatCardFactory();
		ccf.CreateCards();
		ArrayList<CardDef> tooManyCats = ccf.Cards();
		for(int i=0;i<6;i++)
		{
			tooManyCats.remove(r.nextInt(tooManyCats.size()));//remove 6 cats at random
		}
		lib.addAll(tooManyCats);
	}
}
