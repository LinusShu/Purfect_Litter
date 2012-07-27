package com.cs446.purfect_litter.LogicManager.Phases;

import com.cs446.purfect_litter.LogicManager.GameLogic;
import com.cs446.purfect_litter.LogicManager.GameState;
import com.cs446.purfect_litter.LogicManager.Player;
import com.cs446.purfect_litter.LogicManager.CardManager.CardDef;
import com.cs446.purfect_litter.LogicManager.CardManager.CardInstance;
import com.cs446.purfect_litter.LogicManager.CardManager.CatCard;

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
		
		if (wrapper.getGs().currentAction < catlook.getChamberCost()) {
			return false;
		}

		wrapper.getMe().move(chosen, Player.Pile.HAND, Player.Pile.CHAMBER);
		wrapper.getGs().currentAction -= catlook.getChamberCost();
		wrapper.getGs().lastActions+= wrapper.getMe().getName() + " sends " + chosen.getDef().getName() + " to his chamber.\n";
		return true;

	}

	@Override
	public void init(GameLogic wrapper) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextPhase(GameLogic wrapper) {
		
		wrapper.getGs().lastActions+= wrapper.getMe().getName() + " ends his chamber phase\n";
		wrapper.getGs().currentPhase = GameState.phase.PURCHASE;
		wrapper.setPhase((AbstractPhase) new PurchasePhase());
		
	}

}
