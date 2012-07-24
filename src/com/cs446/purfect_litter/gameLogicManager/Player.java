package com.cs446.purfect_litter.gameLogicManager;
import java.util.ArrayList;
import java.util.Random;

import com.cs446.purfect_litter.gameLogicManager.cardManager.CardDef;
import com.cs446.purfect_litter.gameLogicManager.cardManager.CardInstance;
import com.cs446.purfect_litter.gameLogicManager.cardManager.CatCard;
import com.cs446.purfect_litter.gameLogicManager.cardManager.LoveCard;


public class Player {

	String name;
	ArrayList<ArrayList<CardInstance>> piles;
	static public enum pile {
	    DECK(0), HAND(1), PLAYED(2), DISCARD(3), CHAMBER(4);
	    int index;
	    pile(int in){index = in;}
	}
	int victoryPoints;
	
	Player(String name)
	{
		this.name = name;
		piles = new ArrayList<ArrayList<CardInstance>>(5);
		for (int i=0; i<5; i++)
		{
			piles.set(i, new ArrayList<CardInstance>());
		}
	}
	
	public String getName()
	{
		return name;
	}
	
	public void take(CardInstance in, pile p)
	{
		piles.get(p.index).add(in);
	}
	
	public void move(CardInstance in, pile s, pile d)
	{
		piles.get(d.index).add(piles.get(s.index).remove(piles.get(s.index).indexOf(in)));
		//finds the index in s where in resides, removes it and plops it into d
	}
	
	public void move(pile s, pile d)
	{
		if (s == pile.DECK)//if its moving from the deck, move only one,
		{
			if (piles.get(pile.DECK.index).size() == 0)//if ever deck is empty
			{
				Random r= new Random();
				for (int i = piles.get(pile.DISCARD.index).size(); i<0; i--)
				{
					piles.get(pile.DECK.index).add(piles.get(pile.DISCARD.index).remove(r.nextInt(i)));
					//remove a card from discard at random, and plop it into deck
				}
			}
			piles.get(d.index).add(piles.get(s.index).remove(0));
		}
		else // if its from anywhere else, move the entire pile
		{
			piles.get(d.index).addAll(piles.get(s.index));
			piles.get(s.index).clear();
		}
	}
	
	public int getLoveInHand()
	{
		int rvlove= 0;
		for (int i=0;i<piles.get(pile.HAND.index).size(); i++)
		{
			CardDef look = piles.get(pile.HAND.index).get(i).getDef();
			if (look.getType() == CardDef.ctype.LOVE)
			{
				LoveCard lovelook = (LoveCard) look; 
				rvlove += lovelook.getValue();
			}
		}
		return rvlove;
	}
	
	public void calculateVP()
	{
		victoryPoints= 0;
		ArrayList<CardInstance> allCards = new ArrayList<CardInstance>();
		for (int i=0; i<piles.size(); i++)
		{
			allCards.addAll(piles.get(i));
		}
		for (int i=0; i<allCards.size(); i++)
		{
			CardDef look = allCards.get(i).getDef();
			if (look.getType() == CardDef.ctype.CAT)
			{
				CatCard catlook = (CatCard) look; 
				victoryPoints += catlook.getVP();
			}
		}
	}
}
