package com.cs446.purfect_litter.gameLogicManager.cardManager;

import java.util.ArrayList;
import java.util.List;

/* 
 * abstract card factory used to generate different types of card definitions 
 * 
 */

public abstract class AbstractCardFactory {
	private  List<Card> cards = new ArrayList<Card>();
	
	public AbstractCardFactory(){
		this.CreateCards();
	}
	
	public List<Card> Cards(){
		return this.cards;
	}
	
	public abstract void CreateCards();
	
}
