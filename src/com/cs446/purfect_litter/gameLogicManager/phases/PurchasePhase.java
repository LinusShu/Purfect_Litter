package com.cs446.purfect_litter.gameLogicManager.phases;

import com.cs446.purfect_litter.gameLogicManager.GameLogic;
import com.cs446.purfect_litter.gameLogicManager.GameState;
import com.cs446.purfect_litter.gameLogicManager.Player;
import com.cs446.purfect_litter.gameLogicManager.cardManager.CardInstance;

public class PurchasePhase implements AbstractPhase{

	@Override
	public boolean pickCard(CardInstance chosen, GameLogic wrapper) {
		
		//FIND IT IN TOWN
		
		//MAKE SURE YOU CAN AFFORD
		if (chosen.getDef().getCost() > wrapper.table.currentLove)
		{
			return false;
		}
		
		wrapper.table.currentLove-= chosen.getDef().getCost();
		wrapper.me.take(wrapper.table.townRemove(chosen), Player.pile.PLAYED);
		wrapper.table.lastActions+= wrapper.me.getName() + " bought a " + chosen.getDef().getName() + "\n";
		return true;
		
	}

	@Override
	public void init(GameLogic wrapper) {
		
		wrapper.table.currentLove += wrapper.me.getLoveInHand();
		
	}

	@Override
	public void nextPhase(GameLogic wrapper) {
		
		wrapper.table.lastActions+= wrapper.me.getName() + " ends his purchase phase\n";
		wrapper.table.currentPhase = GameState.phase.END;
		wrapper.setPhase(new EndPhase());
	}

}
