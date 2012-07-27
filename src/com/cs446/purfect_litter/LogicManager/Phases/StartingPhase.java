package com.cs446.purfect_litter.LogicManager.Phases;

import com.cs446.purfect_litter.LogicManager.GameLogic;
import com.cs446.purfect_litter.LogicManager.GameState;
import com.cs446.purfect_litter.LogicManager.CardManager.CardInstance;

public class StartingPhase implements AbstractPhase {

	@Override
	public boolean pickCard(CardInstance chosen, GameLogic wrapper) {
		return false;
		// Do nothing or raise error
		
	}

	@Override
	public void init(GameLogic wrapper) {
		
		wrapper.getGs().currentAction = 1;
		wrapper.getGs().currentPurchase = 1;
		wrapper.getGs().currentLove = 10;
		wrapper.getGs().lastActions+= wrapper.getMe().getName() + "'s turn begins\n";
		wrapper.getGs().currentPhase = GameState.phase.ACTION;
		wrapper.setPhase(new ActionPhase());
		
	}

	@Override
	public void nextPhase(GameLogic wrapper) {
		//NOTHING
		
	}

	

}
