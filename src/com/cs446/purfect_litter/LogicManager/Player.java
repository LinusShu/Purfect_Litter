package com.cs446.purfect_litter.LogicManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import com.cs446.purfect_litter.LogicManager.CardManager.CardDef;
import com.cs446.purfect_litter.LogicManager.CardManager.CardInstance;
import com.cs446.purfect_litter.LogicManager.CardManager.CatCard;
import com.cs446.purfect_litter.LogicManager.CardManager.LoveCard;


public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2489578786043327902L;
	String name;
	public ArrayList<ArrayList<CardInstance>> piles;
	static public enum Pile {
	    DECK(0), HAND(1), PLAYED(2), DISCARD(3), CHAMBER(4);
	    public int index;
	    Pile(int in){index = in;}
	}
	int victoryPoints;
	
	Player(String name)
	{
		this.name = name;
		piles = new ArrayList<ArrayList<CardInstance>>();
		for (int i=0; i<5; i++)
		{
			piles.add(new ArrayList<CardInstance>());
		}
	}
	
	public String getName()
	{
		return name;
	}
	
	public void take(CardInstance in, Pile p)
	{
		piles.get(p.index).add(in);
	}
	
	public void move(CardInstance in, Pile s, Pile d)
	{
		piles.get(d.index).add(piles.get(s.index).remove(piles.get(s.index).indexOf(in)));
		//finds the index in s where in resides, removes it and plops it into d
	}
	
	public void move(Pile s, Pile d)
	{
		if (s == Pile.DECK)//if its moving from the deck, move only one,
		{
			if (piles.get(Pile.DECK.index).size() == 0)//if ever deck is empty
			{
//				Log.d("SIZE OF DISCARD ", piles.get(pile.DISCARD.index).size() + "");
				Random r= new Random();
				while (piles.get(Pile.DISCARD.index).size()> 0)
				{
					int removeMe = r.nextInt(piles.get(Pile.DISCARD.index).size());
					CardInstance cardInstance = piles.get(Pile.DISCARD.index).remove(removeMe);
					piles.get(Pile.DECK.index).add(cardInstance);
					//remove a card from discard at random, and plop it into deck
				}
			}
//			Log.d("SIZE OF DECK", piles.get(pile.DECK.index).size() + "");
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
		for (int i=0;i<piles.get(Pile.HAND.index).size(); i++)
		{
			CardDef look = piles.get(Pile.HAND.index).get(i).getDef();
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
	
	public Integer[] getImageArray(Pile p)
	{
		Integer[] rv= new Integer[piles.get(p.index).size()];
		try {

			for (int i=0;i<piles.get(p.index).size();i++)
			{
				rv[i]=piles.get(p.index).get(i).getDef().getImage();
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rv;
	}
	
	@Override
	public boolean equals(Object other) {
		return name.equals(((Player) other).name);
	}
}
