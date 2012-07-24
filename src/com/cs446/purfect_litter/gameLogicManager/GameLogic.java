package com.cs446.purfect_litter.gameLogicManager;

import java.util.ArrayList;
import java.util.Random;

import com.cs446.purfect_litter.gameLogicManager.cardManager.CardDef;
import com.cs446.purfect_litter.gameLogicManager.cardManager.CardDefLib;
import com.cs446.purfect_litter.gameLogicManager.cardManager.CardInstance;
import com.cs446.purfect_litter.gameLogicManager.cardManager.CatCardFactory;
import com.cs446.purfect_litter.gameLogicManager.cardManager.LoveCardFactory;
import com.cs446.purfect_litter.gameLogicManager.phases.AbstractPhase;
import com.cs446.purfect_litter.gameLogicManager.phases.StartingPhase;
import com.cs446.purfect_litter.gameSessionManager.GameSessionManager;



public class GameLogic {
	
	public GameState table;
	public Player me;
	AbstractPhase currentPhase;
	GameSessionManager comm;
	
	GameLogic(GameState newGS, String playerName)
	{
		CardDefLib.populate();
		
		int i=0;
		while(true)
		{
			if(newGS.players.get(i).getName() == playerName)
			{
				me = newGS.players.get(i);
				break;
			}
		}
		update(newGS);
	}
	
	GameLogic(ArrayList<String> playerNames)//ASSUMING the CURRENT PLAYER IS FIRST
	{
		CardDefLib.populate();
		table = new GameState();
		
		table.currentPhase= GameState.phase.END;
		
		Random r= new Random();
		
		//create players
		for (int i=0; i<playerNames.size(); i++)
		{
			Player newguy = new Player(playerNames.get(i));
			if (i == 0) {me= newguy;}
			table.players.add(newguy);
		}
		
		//choose a first player randomly
		table.currentPlayer= table.players.get(r.nextInt(playerNames.size()));
		
		//create all card instances
		for (int i=0; i<CardDefLib.lib.size(); i++)//for each card definition
		{
			CardDef def = CardDefLib.lib.get(i);
			int left = def.getNumber();
			
			if (def.getName() == "One Love")//if its a one love card
			{
				for (int j=0; j<table.players.size();j++)//for each player
				{
					for (int k=0; k<7;k++)//deal 7 of them
					{
						table.players[j].take(new CardInstance(i), Player.pile.DISCARD);
						left--;
					}
				}
			}
			
			if (def.getName() == "Colette Framboise")//if its a colette
			{
				for (int j=0; j<table.players.size();j++)//for each player
				{
					for (int k=0; k<3;k++)//deal 3 of them
					{
						table.players[j].take(new CardInstance(i), Player.pile.DISCARD);
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
		table.lastActions = "The game has begun!\n";
		comm.send(table);
		update(table);
	}
	
	public void setPhase(AbstractPhase tothis)
	{
		currentPhase = tothis;
		comm.send(table);
		currentPhase.init(this);
	}
	
	void update(GameState newGS)
	{
		table = newGS;
		
		//TALK TO VIEW, POST UPDATES
		view.updatelog(table.lastActions);
		table.lastActions = "";
		
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
