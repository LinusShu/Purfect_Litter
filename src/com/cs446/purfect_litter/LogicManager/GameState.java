package com.cs446.purfect_litter.LogicManager;


import java.io.Serializable;
import java.util.ArrayList;

import com.cs446.purfect_litter.R;
import com.cs446.purfect_litter.LogicManager.CardManager.CardDef;
import com.cs446.purfect_litter.LogicManager.CardManager.CardInstance;


public class GameState implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1172399847755703701L;

	public static enum phase {
	    START("Start Phase"), 
	    ACTION("Action Phase"), 
	    CHAMBER("Chamber Phase"), 
	    PURCHASE("Purchase Phase"), 
	    END("End Phase"), 
	    OOT("Not My Turn"), 
	    VICTORY("Victory Phase");
	    
	    private String name;
	    
	    private phase(String s) {
	    	name = s;
	    }
	    
	    public String getName() {
	    	return name;
	    }
	}
	
	public ArrayList<ArrayList<CardInstance>> townCards;
	public ArrayList<Player> players;
	public Player currentPlayer;
	public phase currentPhase;
	public int currentLove;
	public int currentAction;
	public int currentPurchase;
	public String lastActions;
	public int id;
	
	public GameState()
	{
		townCards = new ArrayList<ArrayList<CardInstance>>();
		players = new ArrayList<Player>();
		lastActions = "";
	}
	
	public Player nextPlayer()
	{
		if (players.indexOf(currentPlayer)+1 == players.size())
		{
			currentPlayer = players.get(0);
		}
		else
		{
			currentPlayer = players.get(players.indexOf(currentPlayer)+1);
		}
		return currentPlayer;
	}
	
	boolean gameOver()
	{
		int x = 0;
		for (int i=0; i<townCards.size();i++)
		{
			if (townCards.get(i).size() == 0)
			{
				x++;
			}
		}
		if (x>=2) { return true;}
		return false;
	}
	
	void findVictor()
	{
		for (int i=0; i<players.size(); i++)
		{
			players.get(i).calculateVP();
		}
	}
	
	public CardInstance townRemove(CardInstance chosen)
	{
		CardDef compare = chosen.getDef();
		for (int i=0;i<townCards.size(); i++)
		{
			if (townCards.get(i).size()>0 && townCards.get(i).get(0).getDef() == compare)
			{
				return townCards.get(i).remove(0);
			}
		}
		//THROW ERROR
		return null;
	}

	public int getID() {
		return this.id;
	}

	public void setID(int id) {
		this.id = id;
	}
	
	public String pullLastActions() {
		String rv = lastActions;
		lastActions = "";
		return rv;
	}
	
	public int[] getImageArray() {
		int[] rv = new int[townCards.size()];
		for (int i=0;i<townCards.size();i++)
		{
			if (townCards.get(i).size() == 0)
			{
				rv[i]= R.drawable.card_blank;
			}
			else
			{
				rv[i]=townCards.get(i).get(0).getDef().getImage();	
			}
		}
		return rv;
	}
}
