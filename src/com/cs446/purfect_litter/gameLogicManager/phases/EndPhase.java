package com.cs446.purfect_litter.gameLogicManager.phases;

import com.cs446.purfect_litter.gameLogicManager.GameLogic;
import com.cs446.purfect_litter.gameLogicManager.GameState;
import com.cs446.purfect_litter.gameLogicManager.Player;
import com.cs446.purfect_litter.gameLogicManager.cardManager.CardInstance;

public class EndPhase implements AbstractPhase{

	@Override
	public boolean pickCard(CardInstance chosen, GameLogic wrapper) {
		return false;
		// do nothing
		
	}

	@Override
	public void init(GameLogic wrapper) {
		wrapper.me.move(Player.pile.HAND, Player.pile.PLAYED);
		wrapper.me.move(Player.pile.PLAYED, Player.pile.DISCARD);
		for (int i = 0; i<5; i++)
		{
			wrapper.me.move(Player.pile.DECK, Player.pile.HAND);
		}
		wrapper.table.currentAction = 0;
		wrapper.table.currentLove = 0;
		wrapper.table.currentPurchase = 0;
		
		wrapper.table.currentPhase = GameState.phase.ACTION;
		wrapper.table.lastActions+= wrapper.me.getName() + " ends his turn\n";
		wrapper.me = wrapper.table.nextPlayer();
		wrapper.comm.send(wrapper.table);
		
	}

	@Override
	public void nextPhase(GameLogic wrapper) {
		// do nothing
		
	}
	

}
