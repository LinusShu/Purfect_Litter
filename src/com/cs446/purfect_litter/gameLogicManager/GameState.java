package com.cs446.purfect_litter.gameLogicManager;


import java.util.ArrayList;

import com.cs446.purfect_litter.gameLogicManager.cardManager.CardDef;
import com.cs446.purfect_litter.gameLogicManager.cardManager.CardInstance;


public class GameState {

	public static enum phase {
	    START, ACTION, CHAMBER, PURCHASE, END, VICTORY
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
}
