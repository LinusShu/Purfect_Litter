package com.cs446.purfect_litter.LogicManager.CardManager;

import java.util.ArrayList;
import java.util.Random;

public class CardDefLib {
	public static ArrayList<CardDef> lib = new ArrayList<CardDef>();

	// create card definitions
	public static void populate() 
	{
		Random r= new Random();
		
		lib.clear();
		LoveCardFactory lcf = new LoveCardFactory();
		//lcf.CreateCards();
		lib.addAll(lcf.Cards());
		CatCardFactory ccf = new CatCardFactory();
		//ccf.CreateCards();
		lib.addAll(ccf.Cards());
	}
}
