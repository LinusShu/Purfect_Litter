package com.cs446.purfect_litter.gameLogicManager.phases;

import com.cs446.purfect_litter.gameLogicManager.GameLogic;
import com.cs446.purfect_litter.gameLogicManager.GameState;
import com.cs446.purfect_litter.gameLogicManager.cardManager.CardInstance;

public class StartingPhase implements AbstractPhase {

	@Override
	public boolean pickCard(CardInstance chosen, GameLogic wrapper) {
		return false;
		// Do nothing or raise error
		
	}

	@Override
	public void init(GameLogic wrapper) {
		
		wrapper.table.currentAction = 1;
		wrapper.table.currentPurchase = 1;
		wrapper.table.currentLove = 0;
		wrapper.table.lastActions+= wrapper.me.getName() + "'s turn begins\n";
		wrapper.table.currentPhase = GameState.phase.ACTION;
		wrapper.setPhase(new ActionPhase());
		
	}

	@Override
	public void nextPhase(GameLogic wrapper) {
		//NOTHING
		
	}

	

}
