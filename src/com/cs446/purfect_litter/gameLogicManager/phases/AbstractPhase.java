package com.cs446.purfect_litter.gameLogicManager.phases;

import com.cs446.purfect_litter.gameLogicManager.GameLogic;
import com.cs446.purfect_litter.gameLogicManager.cardManager.CardInstance;

public interface AbstractPhase {

	boolean pickCard(CardInstance chosen, GameLogic wrapper);
	void init(GameLogic wrapper);
	void nextPhase(GameLogic wrapper);
}
