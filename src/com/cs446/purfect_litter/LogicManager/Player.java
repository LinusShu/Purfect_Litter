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
		this.name = name;//set the name of this player
		piles = new ArrayList<ArrayList<CardInstance>>();//create a container for piles
		for (int i=0; i<Pile.values().length; i++)//for every pile we want
		{
			piles.add(new ArrayList<CardInstance>());//make that pile
		}
	}
	
	public String getName()
	{
		return name;
	}
	
	/**
	 * for introducing a card into the player's piles
	 * @param in: the card instance you want introduced
	 * @param p: the pile where you want it to go
	 */
	public void take(CardInstance in, Pile p)
	{
		piles.get(p.index).add(in);
	}
	
	/**
	 * for moving a single card instance from one pile to another inside a player
	 * @param in: the card you want moved
	 * @param s: where this card can be found, the Source
	 * @param d: where you want the card to go, the Destination
	 */
	public void move(CardInstance in, Pile s, Pile d)
	{
		piles.get(d.index).add(piles.get(s.index).remove(piles.get(s.index).indexOf(in)));
		//finds the index in s where in resides, removes it and plops it into d
	}
	
	/**
	 * for moving entire piles in a player into another pile
	 * EXCEPTION: if the source is the Deck, only move the first card
	 * @param s: the source
	 * @param d: the destination
	 */
	public void move(Pile s, Pile d)
	{
		if (s == Pile.DECK)//if its moving from the deck, move only one,
		{
			if (piles.get(Pile.DECK.index).size() == 0)//if ever deck is empty
			{
//				Log.d("SIZE OF DISCARD ", piles.get(pile.DISCARD.index).size() + "");
				//this is reloading the discard pile, this effectively is like shuffling the deck 
				Random r= new Random();
				while (piles.get(Pile.DISCARD.index).size()> 0)
				{
					int removeMe = r.nextInt(piles.get(Pile.DISCARD.index).size());
					CardInstance cardInstance = piles.get(Pile.DISCARD.index).remove(removeMe);
					piles.get(Pile.DECK.index).add(cardInstance);
					//remove a card from discard at random, and plop it into deck
					//used to be on a single line, got broken off
				}
			}
//			Log.d("SIZE OF DECK", piles.get(pile.DECK.index).size() + "");
			if (piles.get(Pile.DECK.index).size() > 0) {//if the deck is STILL empty forget it, just dont draw
				piles.get(d.index).add(piles.get(s.index).remove(0));
			}
		}
		else // if its from anywhere else, move the entire pile
		{
			piles.get(d.index).addAll(piles.get(s.index));//add source to destination
			piles.get(s.index).clear();//clear source
		}
	}
	
	/**
	 * for use when entering purchasing phase, 
	 * counts the total love points available in the hand
	 * @return returns total love points in hand
	 */
	public int getLoveInHand()
	{
		int rvlove= 0;
		for (int i=0;i<piles.get(Pile.HAND.index).size(); i++)//for every card in hand
		{
			CardDef look = piles.get(Pile.HAND.index).get(i).getDef();
			if (look.getType() == CardDef.ctype.LOVE)//if its a love card
			{
				LoveCard lovelook = (LoveCard) look; 
				rvlove += lovelook.getValue();//add its value to the total
			}
		}
		return rvlove;
	}
	
	/**
	 * for use when the game is over
	 * finds out how many points the player has racked up by counting all the victory points on all his cards in all piles
	 */
	public void calculateVP()
	{
		victoryPoints= 0;
		ArrayList<CardInstance> allCards = new ArrayList<CardInstance>();
		for (int i=0; i<piles.size(); i++)//for all piles
		{
			allCards.addAll(piles.get(i));//put their contents in allCards
		}
		for (int i=0; i<allCards.size(); i++)//for every card this player has
		{
			CardDef look = allCards.get(i).getDef();
			if (look.getType() == CardDef.ctype.CAT)//is this card a cat?
			{
				CatCard catlook = (CatCard) look; 
				victoryPoints += catlook.getVP();//add the victory points it offers to the total
			}
		}
	}
	
	/**
	 * for ui convinience when displaying the contents of a player pile
	 * @param p: the pile they want shown
	 * @return: indices of all the images used in that pile
	 */
	public Integer[] getImageArray(Pile p)
	{
		Integer[] rv= new Integer[piles.get(p.index).size()];
		try {

			for (int i=0;i<piles.get(p.index).size();i++)//for each card in the pile
			{
				rv[i]=piles.get(p.index).get(i).getDef().getImage();//return the index it uses
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
