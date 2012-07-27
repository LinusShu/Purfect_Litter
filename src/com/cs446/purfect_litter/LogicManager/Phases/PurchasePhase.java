package com.cs446.purfect_litter.LogicManager.Phases;

import com.cs446.purfect_litter.LogicManager.GameLogic;
import com.cs446.purfect_litter.LogicManager.GameState;
import com.cs446.purfect_litter.LogicManager.Player;
import com.cs446.purfect_litter.LogicManager.CardManager.CardInstance;

public class PurchasePhase implements AbstractPhase{

	@Override
	public boolean pickCard(CardInstance chosen, GameLogic wrapper) {
		
		//FIND IT IN TOWN
		
		//MAKE SURE YOU CAN AFFORD
		if (chosen.getDef().getCost() > wrapper.getGs().currentLove ||
				wrapper.getGs().currentPurchase == 0)
		{
			return false;
		}
		
		wrapper.getGs().currentLove -= chosen.getDef().getCost();
		wrapper.getGs().currentPurchase--;
		wrapper.getMe().take(wrapper.getGs().townRemove(chosen), Player.Pile.PLAYED);
		wrapper.getGs().lastActions= wrapper.getMe().getName() + " bought a " + chosen.getDef().getName() + "\n"+ wrapper.getGs().lastActions;
		return true;
		
	}

	@Override
	public void init(GameLogic wrapper) {
		
		wrapper.getGs().currentLove += wrapper.getMe().getLoveInHand();
		
	}

	@Override
	public void nextPhase(GameLogic wrapper) {
		
		wrapper.getGs().lastActions= wrapper.getMe().getName() + " ends his purchase phase\n"+ wrapper.getGs().lastActions;
		wrapper.getGs().currentPhase = GameState.phase.END;
		wrapper.setPhase(new EndPhase());
	}

}
