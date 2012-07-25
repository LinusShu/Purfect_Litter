package com.cs446.purfect_litter.gameLogicManager.phases;

import com.cs446.purfect_litter.gameLogicManager.GameLogic;
import com.cs446.purfect_litter.gameLogicManager.GameState;
import com.cs446.purfect_litter.gameLogicManager.Player;
import com.cs446.purfect_litter.gameLogicManager.cardManager.CardDef;
import com.cs446.purfect_litter.gameLogicManager.cardManager.CardInstance;
import com.cs446.purfect_litter.gameLogicManager.cardManager.CatCard;

public class ActionPhase implements AbstractPhase{

	@Override
	public boolean pickCard(CardInstance chosen, GameLogic wrapper) {

		// CHECK TO SEE IF ITS A BLUE MAID CARD
		CardDef look = chosen.getDef();
		if (look.getType() != CardDef.ctype.CAT)
		{
			return false;
		}
		CatCard catlook = (CatCard) look;
		
		if (wrapper.table.currentAction < 1)
		{
			return false;
		}
		
		if (catlook.getColor() == 0)//if its a green maid, we dont want it.
		{
			return false;
		}
		
		wrapper.me.move(chosen, Player.pile.HAND, Player.pile.PLAYED);
		wrapper.table.currentAction += -1 + catlook.getAction(); //HOW DO I ACCESS THIS?
		wrapper.table.currentLove += catlook.getLove();
		wrapper.table.currentPurchase += catlook.getPurchase();
		for (int i =0; i< catlook.getDraw(); i++)
		{
			wrapper.me.move(Player.pile.DECK, Player.pile.HAND);
		}
		wrapper.table.lastActions+= wrapper.me.getName() + " plays " + catlook.getName() + "\n";
		return true;
	}

	@Override
	public void init(GameLogic wrapper) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextPhase(GameLogic wrapper) {
		
		wrapper.table.lastActions+= wrapper.me.getName() + " ends his action phase\n";
		wrapper.table.currentPhase = GameState.phase.CHAMBER;
		wrapper.setPhase((AbstractPhase) new ChamberPhase());
		
	}

}
