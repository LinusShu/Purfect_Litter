package com.cs446.purfect_litter.gameLogicManager;

import java.util.ArrayList;
import java.util.Random;

import android.util.Log;

import com.cs446.purfect_litter.gameLogicManager.cardManager.CardDef;
import com.cs446.purfect_litter.gameLogicManager.cardManager.CardDefLib;
import com.cs446.purfect_litter.gameLogicManager.cardManager.CardInstance;
import com.cs446.purfect_litter.gameLogicManager.phases.AbstractPhase;
import com.cs446.purfect_litter.gameLogicManager.phases.StartingPhase;
import com.cs446.purfect_litter.gameSessionManager.GameSessionManager;



public class GameLogic {
	
	public GameState table;
	public Player me;
	public AbstractPhase currentPhase;
	public GameSessionManager comm;
	
	/**
	 * Client Constructor
	 * 
	 * @param newGS
	 * @param playerName
	 * @param comm
	 */
	public GameLogic(GameState newGS, String playerName, GameSessionManager comm)
	{
		CardDefLib.populate();
		this.comm = comm;
		
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
		update(newGS);
	}
	
	public GameLogic(ArrayList<String> playerNames, GameSessionManager comm)//ASSUMING the CURRENT PLAYER IS FIRST
	{
		CardDefLib.populate();
		this.comm = comm;
		table = new GameState();
		
		table.currentPhase = GameState.phase.END;
		
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
						table.players.get(j).take(new CardInstance(i), Player.pile.DISCARD);
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
						table.players.get(j).take(new CardInstance(i), Player.pile.DISCARD);
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
		
		for (int i = 0;i < table.players.size(); i++)
		{
			for (int j = 0;j < 5; j++)
			{
				table.players.get(i).move(Player.pile.DECK, Player.pile.HAND);
			}
		}
		table.lastActions = "The game has begun!\n";
		comm.send(table);
		update(table);
	}
	
	public void setPhase(AbstractPhase tothis)
	{
		currentPhase = tothis;
		currentPhase.init(this);
		comm.send(table);
	}
	
	void update(GameState newGS)
	{
		table = newGS;
		
		//TALK TO VIEW, POST UPDATES
		//view.updatelog(table.lastActions);
		//table.lastActions = "";
		
		if (table.gameOver())
		{
			table.findVictor();
		}
		
		if (table.currentPlayer == me && table.currentPhase == GameState.phase.END)
		{
			setPhase((AbstractPhase) new StartingPhase());
		}
	}
}
