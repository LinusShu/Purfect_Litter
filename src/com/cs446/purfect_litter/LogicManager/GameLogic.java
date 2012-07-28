package com.cs446.purfect_litter.LogicManager;

import java.util.ArrayList;
import java.util.Random;

import com.cs446.purfect_litter.LogicManager.GameState.TownPile;
import com.cs446.purfect_litter.LogicManager.GameState.phase;
import com.cs446.purfect_litter.LogicManager.CardManager.CardDef;
import com.cs446.purfect_litter.LogicManager.CardManager.CardDefLib;
import com.cs446.purfect_litter.LogicManager.CardManager.CardInstance;
import com.cs446.purfect_litter.LogicManager.CardManager.CardDef.ctype;
import com.cs446.purfect_litter.LogicManager.CardManager.CatCard;
import com.cs446.purfect_litter.LogicManager.Phases.AbstractPhase;
import com.cs446.purfect_litter.LogicManager.Phases.OOTPhase;
import com.cs446.purfect_litter.LogicManager.Phases.StartingPhase;

/**
 * GameLogic class handles the control flow of the game mechanics.
 * Manages the players
 * Changes phases.
 * Manages and updates the game state.
 *
 */
public class GameLogic {
	
	private GameState table;
	private Player me;
	private AbstractPhase currentPhase;
	private Game comm;
	
	/**
	 * Client Constructor
	 * 
	 * @param newGS : the new game state that was created by the server
	 * @param playerName : the name the player should use to identify himself out of the players in the newGS
	 * @param comm: The game object the logic reports to
	 */
	public GameLogic(GameState newGS, String playerName, Game comm)
	{
		CardDefLib.populate();//fill out the card definition library
		this.comm = comm;//associate with the boss
		table = newGS;//set our current gamestate
		
		//find our name or be doomed to loop forever
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
	
	/**
	 * Server Constructor
	 * 
	 * @param playerNames: all the player names that are joining the game
	 * @param comm: the game object we report to
	 */
	public GameLogic(ArrayList<String> playerNames, Game comm)//ASSUMING the CURRENT PLAYER IS FIRST
	{
		CardDefLib.populate();//fill out card definitions
		this.comm = comm;//link up to the boss
		table = new GameState();//make an empty game state
		
		table.currentPhase = GameState.phase.OOT;// set the phase to out of turn
		
		Random r= new Random();//for randomly selecting a starting player
		
		//create players
		for (int i=0; i<playerNames.size(); i++)//for each name
		{
			Player newguy = new Player(playerNames.get(i));//create a player
			if (i == 0) {//if its the first player, its the server
				me = newguy;//set to me
			}
			table.players.add(newguy);//add to players list
		}
		
		// SERVER IS FIRST PLAYER!!!
		table.currentPlayer = table.players.get(0);//could be randomly chosen, currently guarantees that the server gets the first turn
		
		//create all card instances
		for (int i = 0; i < CardDefLib.lib.size(); i++)//for each card definition
		{
			CardDef def = CardDefLib.lib.get(i);//get the definition
			
			int left = def.getNumber();//get how many instances need to be created
			
			//this is the standard starting hand
			//we put the dealt cards in the discard because cards are shuffled when they are automatically transfered from discard to deck
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
			
			if (def.getName().equals("Ophelia Grail")) //if its a colette
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
			for (int j=0; j<left; j++)//for every instance left that needs to be created
			{
				newTownPile.add(new CardInstance(i));//fill up the pile
			}
			//find out where to put it in the town cards
			if (def.getType() == ctype.LOVE)
			{
				table.townCards.get(TownPile.LOVE.index).add(newTownPile);//belongs in the love pile
			}
			else if (def.getType() == ctype.CAT)
			{
				CatCard catdef = (CatCard) def;
				if (catdef.getColor() == 0)
				{
					table.townCards.get(TownPile.CHIEF.index).add(newTownPile);//belongs in the chief pile
				}
				else if (catdef.getColor() == 1)
				{
					table.townCards.get(TownPile.GENERAL.index).add(newTownPile);//belongs in the general pile
				}
				else
				{
					//FAIL HARD because definition does not exist in this game
				}
			}
			else
			{
				//FAIL HARD because the definition does not exist in this game
			}
		}
		
		//deal the cards
		for (int i = 0;i < table.players.size(); i++)
		{
			for (int j = 0;j < 5; j++)//deal 5
			{
				table.players.get(i).move(Player.Pile.DECK, Player.Pile.HAND);//from the deck to the hand
			}
		}
		table.lastActions = "The game has begun!\n";

	}
	
	/**
	 * for switching the phase object so that ui actions are properly handled
	 * also calls the initialization for each phase
	 * @param tothis: this is the phase we want it set to.
	 */
	public void setPhase(AbstractPhase tothis)
	{
		currentPhase = tothis;//set it
		currentPhase.init(this);//init it
//		comm.doSendGameState(table);
	}
	
	/**
	 * This function updates the gamestate and determines whether it needs to act based on it
	 * if the game is over, it needs to determine the winner
	 * if the gamestate is out of turn and me is the current player, start the turn
	 * @param newGS the newest information that we will accept and save
	 * @return true if any action was taken
	 */
	public boolean update(GameState newGS)
	{
		table = newGS;//update the gamestate
		
		//reassign me because it may have changed
		for (Player p : table.players) {
			if (p.name.equals(me.name)) {
				me = p;
				break;
			}
		}
		
		//check to see if the game is over
		if (table.gameOver())
		{
			table.findVictor();//find the winner
			table.currentPhase= phase.VICTORY;//set the state so the ui can react properly
			setPhase(new OOTPhase());//set the phase so that any further commands from the UI are ignored
			return true;
		}
		
		//check to see if its my turn to start a turn
		if (table.currentPlayer.name.equals(me.name) && table.currentPhase == GameState.phase.OOT)
		{
			setPhase((AbstractPhase) new StartingPhase());//this will init the starting phase too
			return true;
		}
		return false;//the update had information, but the gamelogic doesnt care about it
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
