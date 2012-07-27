package com.cs446.purfect_litter.LogicManager;

import java.util.ArrayList;
import java.util.Random;

import com.cs446.purfect_litter.LogicManager.CardManager.CardDef;
import com.cs446.purfect_litter.LogicManager.CardManager.CardDefLib;
import com.cs446.purfect_litter.LogicManager.CardManager.CardInstance;
import com.cs446.purfect_litter.LogicManager.Phases.AbstractPhase;
import com.cs446.purfect_litter.LogicManager.Phases.StartingPhase;



public class GameLogic {
	
	private GameState table;
	private Player me;
	private AbstractPhase currentPhase;
	private Game comm;
	
	/**
	 * Client Constructor
	 * 
	 * @param newGS
	 * @param playerName
	 * @param comm
	 */
	public GameLogic(GameState newGS, String playerName, Game comm)
	{
		CardDefLib.populate();
		this.comm = comm;
		table = newGS;
		
		int i = 0;
		while (true)
		{
			if (newGS.players.get(i).getName().equals(playerName))
			{
				me = newGS.players.get(i);
				break;
			}
			i++;
		}
	}
	
	public GameLogic(ArrayList<String> playerNames, Game comm)//ASSUMING the CURRENT PLAYER IS FIRST
	{
		CardDefLib.populate();
		this.comm = comm;
		table = new GameState();
		
		table.currentPhase = GameState.phase.OOT;
		
		Random r= new Random();
		
		//create players
		for (int i=0; i<playerNames.size(); i++)
		{
			Player newguy = new Player(playerNames.get(i));
			if (i == 0) {
				me = newguy;
			}
			table.players.add(newguy);
		}
		
		// SERVER IS FIRST PLAYER!!!
		table.currentPlayer = table.players.get(0);
		
		//create all card instances
		for (int i = 0; i < CardDefLib.lib.size(); i++)//for each card definition
		{
			CardDef def = CardDefLib.lib.get(i);
			
			int left = def.getNumber();
			
			if (def.getName().equals("One Love"))//if its a one love card
			{
				for (int j=0; j<table.players.size();j++)//for each player
				{
					for (int k=0; k<7;k++)//deal 7 of them
					{
						table.players.get(j).take(new CardInstance(i), Player.Pile.DISCARD);
//						Log.d("GameLogic - DISCARD size: ", table.players.get(j).piles.get(Player.pile.DISCARD.index).size() + "");
						left--;
					}
				}
			}
			
			if (def.getName().equals("Colette Framboise"))//if its a colette
			{
				for (int j = 0; j < table.players.size(); j++)//for each player
				{
					for (int k = 0; k < 3; k++)//deal 3 of them
					{
						table.players.get(j).take(new CardInstance(i), Player.Pile.DISCARD);
						left--;
					}
				}
			}
			
			
			//for the rest put them in the town.
			ArrayList<CardInstance> newTownPile = new ArrayList<CardInstance>();
			for (int j=0; j<left; j++)
			{
				newTownPile.add(new CardInstance(i));
			}
			table.townCards.add(newTownPile);
		}
		
		//deal the cards
		for (int i = 0;i < table.players.size(); i++)
		{
			for (int j = 0;j < 5; j++)
			{
				table.players.get(i).move(Player.Pile.DECK, Player.Pile.HAND);
			}
		}
		table.lastActions = "The game has begun!\n";

	}
	
	public void setPhase(AbstractPhase tothis)
	{
		currentPhase = tothis;
		currentPhase.init(this);
			comm.doSendGameState(table);
	}
	
	public void update(GameState newGS)
	{
		table = newGS;
		
		//TALK TO VIEW, POST UPDATES
		//view.updatelog(table.lastActions);
		//table.lastActions = "";
		
		if (table.gameOver())
		{
			table.findVictor();
		}
		
		if (table.currentPlayer == me && table.currentPhase == GameState.phase.OOT)
		{
			setPhase((AbstractPhase) new StartingPhase());
		}
	}
	
	public GameState getGs()
	{
		return table;
	}
	
	public Player getMe()
	{
		return me;
	}
	
	public AbstractPhase getPhase()
	{
		return currentPhase;
	}
	
	public void sendGs()
	{
		comm.doSendGameState(table);
	}
}
