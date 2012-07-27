package com.cs446.purfect_litter.LogicManager.Phases;

import com.cs446.purfect_litter.LogicManager.GameLogic;
import com.cs446.purfect_litter.LogicManager.CardManager.CardInstance;

public interface AbstractPhase {

	boolean pickCard(CardInstance chosen, GameLogic wrapper);
	void init(GameLogic wrapper);
	void nextPhase(GameLogic wrapper);
}
