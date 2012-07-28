package com.cs446.purfect_litter.LogicManager;


import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;

import android.R.integer;
import android.graphics.AvoidXfermode.Mode;

import com.cs446.purfect_litter.R;
import com.cs446.purfect_litter.LogicManager.CardManager.CardDef;
import com.cs446.purfect_litter.LogicManager.CardManager.CardInstance;

/**
 * GameState class represents the current game state.
 * SessionManager passes GameState objects from server to clients
 * to keep the game in sync between players.
 */
public class GameState extends Object implements Serializable {

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
	
	static public enum TownPile {
	    LOVE(0), CHIEF(1), GENERAL(2);
	    public int index;
	    TownPile(int in){index = in;}
	}
	
	public ArrayList<ArrayList<ArrayList<CardInstance>>> townCards;
	public ArrayList<Player> players;
	public Player currentPlayer;
	public phase currentPhase;
	public int currentLove;
	public int currentAction;
	public int currentPurchase;
	public String lastActions;
	public int id;
	
	/**
	 * Constructor
	 */
	public GameState()
	{
		townCards = new ArrayList<ArrayList<ArrayList<CardInstance>>>();//create super container
		for (int i=0;i<TownPile.values().length;i++)
		{
			townCards.add(new ArrayList<ArrayList<CardInstance>>());//create card type container
		}
		players = new ArrayList<Player>();
		lastActions = "";
	}
	
	/**
	 * Copy constructor.
	 */
	public GameState(GameState other) {
		this.id = other.id;
		this.lastActions = new String(other.lastActions);
		this.currentPurchase = other.currentPurchase;
		this.currentAction = other.currentAction;
		this.currentLove = other.currentLove;
		this.currentPhase = other.currentPhase;
		
		this.players = new ArrayList<Player>();
		for (Player otherPlayer : other.players) {
			this.players.add(new Player(otherPlayer));
		}
		for (Player p : this.players) {
			if (p.name.equals(other.currentPlayer.name)) {
				this.currentPlayer = p;
				break;
			}
		}
		
		this.townCards = new ArrayList<ArrayList<ArrayList<CardInstance>>>();
		for (ArrayList<ArrayList<CardInstance>> othersTownType : other.townCards) {
			this.townCards.add(new ArrayList<ArrayList<CardInstance>>());
			
			for (ArrayList<CardInstance> othersTownPile : othersTownType) {
				int i = this.townCards.size() - 1;
				this.townCards.get(i).add(new ArrayList<CardInstance>());
				
				for (CardInstance othersTownCard : othersTownPile) {
					int j = this.townCards.get(i).size() - 1;
					this.townCards.get(i).get(j).add(new CardInstance(othersTownCard.getDefRef()));
				}
			}
		}
	}
	
	/**
	 * For use when changing turns
	 * chooses the next player in sequence of the players array
	 */
	public void setNextPlayer()
	{
		int index = players.indexOf(currentPlayer);//find current player's index in players
		currentPlayer = players.get((index + 1) % players.size());//set the current player to the next player index
	}
	
	/**
	 * For use when checking if the game met ending conditions
	 * counts how many card definition piles are empty
	 * @return true if the game is over, false otherwise
	 */
	boolean gameOver()
	{
		int x = 0;
		for (int i=0; i<townCards.size();i++)//for each card type
		{
			for (int j=0; j<townCards.get(i).size();j++)//for each card definition
			{
				if (townCards.get(i).get(j).size() == 0)//are we out of card instances to sell
				{
					x++;
				}
			}
		}
		if (x>=2) { return true;}// were there 2 or more empty piles? game is over
		return false;//otherwise the game is not over
	}
	
	/**
	 * for use when trying to find a victor
	 * sets the victory points field in each player
	 */
	void findVictor()
	{
		for (int i=0; i<players.size(); i++)//for each player
		{
			players.get(i).calculateVP();//count his points
		}
	}
	
	/**
	 * For use when purchasing a card from the town
	 * looks through every single card in the town until it find one that matches the definition of chosen
	 * removes it from the townCards
	 * returns the instance it removed
	 * @param chosen: the card whose definition wants one instance removed from town
	 * @return the instance that was removed
	 */
	public CardInstance townRemove(CardInstance chosen)
	{
		CardDef compare = chosen.getDef();
		for (int i=0;i<townCards.size(); i++)//for every card type
		{
			for (int j=0;j<townCards.get(i).size();j++)//for every definition of that type
			{
				if (townCards.get(i).get(j).size()>0 && townCards.get(i).get(j).get(0).getDef() == compare) //if theres at least one in the pile, and the def matches
				{
					return townCards.get(i).get(j).remove(0);//get rid of it and return it
				}
			}
		}
		//THROW ERROR, because the card wasnt found
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
		return rv;
	}
	
	/**
	 * for ui convenience to get the correct image arrays
	 * @param p the type of card they want definitions of
	 * @return an array of image indices for R
	 */
	public Integer[] getImageArray(TownPile p) {
		Integer[] rv = new Integer[townCards.get(p.index).size()];
		for (int i=0;i<townCards.get(p.index).size();i++)////for every definition of card type p
		{
			if (townCards.get(p.index).get(i).size() == 0)//is this pile empty?
			{
				rv[i]= R.drawable.card_blank;//this pile is empty, so we dont know what used to be in it
			}
			else
			{
				rv[i]=townCards.get(p.index).get(i).get(0).getDef().getImage();//return the image index of the first card in this pile (all definitions are the same in this pile)	
			}
		}
		return rv;
	}
}
