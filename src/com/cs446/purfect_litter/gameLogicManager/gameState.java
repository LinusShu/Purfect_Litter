

import java.util.ArrayList;


public class gameState {

	public enum phase {
	    START, ACTION, CHAMBER, PURCHASE, END, VICTORY
	}
	
	public ArrayList<ArrayList<cardInstance>> townCards;
	public ArrayList<player> players;
	public player currentPlayer;
	public phase currentPhase;
	public int currentLove;
	public int currentAction;
	public int currentPurchase;
	public String lastActions;
	
	player nextPlayer()
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
		for (int i=0; i<townCards.size(),i++)
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
	
	cardInstance townRemove(cardInstance chosen)
	{
		
	}
}
