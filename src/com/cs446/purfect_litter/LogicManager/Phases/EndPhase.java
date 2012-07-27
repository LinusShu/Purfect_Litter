package com.cs446.purfect_litter.LogicManager.Phases;

import com.cs446.purfect_litter.LogicManager.GameLogic;
import com.cs446.purfect_litter.LogicManager.GameState;
import com.cs446.purfect_litter.LogicManager.Player;
import com.cs446.purfect_litter.LogicManager.CardManager.CardInstance;

public class EndPhase implements AbstractPhase{

	@Override
	public boolean pickCard(CardInstance chosen, GameLogic wrapper) {
		return false;
		// do nothing
		
	}

	@Override
	public void init(GameLogic wrapper) {
		wrapper.getMe().move(Player.Pile.HAND, Player.Pile.PLAYED);
		wrapper.getMe().move(Player.Pile.PLAYED, Player.Pile.DISCARD);
		for (int i = 0; i<5; i++)
		{
			wrapper.getMe().move(Player.Pile.DECK, Player.Pile.HAND);
		}
		wrapper.getGs().currentAction = 0;
		wrapper.getGs().currentLove = 0;
		wrapper.getGs().currentPurchase = 0;
		
		wrapper.getGs().currentPhase = GameState.phase.OOT;
		wrapper.getGs().lastActions += wrapper.getMe().getName() + " ends his turn\n";
		wrapper.getGs().currentPlayer = wrapper.getGs().nextPlayer();
		wrapper.setPhase(new OOTPhase());
		
	}

	@Override
	public void nextPhase(GameLogic wrapper) {
		// do nothing
		
	}
	

}
