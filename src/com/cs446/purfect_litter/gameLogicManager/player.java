import java.util.Random;


public class player {

	ArrayList<ArrayList<cardInstance>> piles;
	static public enum pile {
	    DECK(0), HAND(1), PLAYED(2), DISCARD(3), CHAMBER(4);
	    int index;
	    pile(int in){index = in;}
	}
	int victoryPoints;
	
	void take(cardInstance in, pile p)
	{
		piles[p.index].add(in);
	}
	
	void move(cardInstance in, pile s, pile d)
	{
		piles[d.index].add(piles[s.index].remove(piles[s.index].indexOf(in)));
		//finds the index in s where in resides, removes it and plops it into d
	}
	
	void move(pile s, pile d)
	{
		if (s == pile.DECK)//if its moving from the deck, move only one,
		{
			if (piles[pile.DECK.index].size() == 0)//if ever deck is empty
			{
				Random r= new Random();
				for (int i = piles[pile.DISCARD.index].size(); i<0; i--)
				{
					piles[pile.DECK.index].add(piles[pile.DISCARD.index].remove(r.nextInt(i)));
					//remove a card from discard at random, and plop it into deck
				}
			}
			piles[d.index].add(piles[s.index].remove(0));
		}
		else // if its from anywhere else, move the entire pile
		{
			piles[d.index].addAll(piles[s.index]);
			piles[s.index].clear();
		}
	}
	
	int getLoveInHand()
	{
		
	}
	
	void calculateVP()
	{
		
	}
}
