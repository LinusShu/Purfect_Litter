package com.cs446.purfect_litter.gameLogicManager.phases;

import com.cs446.purfect_litter.gameLogicManager.GameLogic;
import com.cs446.purfect_litter.gameLogicManager.GameState;
import com.cs446.purfect_litter.gameLogicManager.Player;
import com.cs446.purfect_litter.gameLogicManager.cardManager.CardDef;
import com.cs446.purfect_litter.gameLogicManager.cardManager.CardInstance;
import com.cs446.purfect_litter.gameLogicManager.cardManager.CatCard;

public class ChamberPhase implements AbstractPhase{

	@Override
	public boolean pickCard(CardInstance chosen, GameLogic wrapper) {

		CardDef look = chosen.getDef();
		if (look.getType() != CardDef.ctype.CAT)
		{
			return false;
		}
		CatCard catlook = (CatCard) look;
		
		if (catlook.getChamberCost() == 0) {
			return false;
		}
		
		if (wrapper.table.currentAction < catlook.getChamberCost()) {
			return false;
		}

		wrapper.me.move(chosen, Player.pile.HAND, Player.pile.CHAMBER);
		wrapper.table.currentAction -= catlook.getChamberCost();
		wrapper.table.lastActions+= wrapper.me.getName() + " sends " + chosen.getDef().getName() + " to his chamber.\n";
		return true;

	}

	@Override
	public void init(GameLogic wrapper) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextPhase(GameLogic wrapper) {
		
		wrapper.table.lastActions+= wrapper.me.getName() + " ends his chamber phase\n";
		wrapper.table.currentPhase = GameState.phase.PURCHASE;
		wrapper.setPhase((AbstractPhase) new PurchasePhase());
		
	}

}
