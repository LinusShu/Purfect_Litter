package com.cs446.purfect_litter.LogicManager.Phases;

import com.cs446.purfect_litter.LogicManager.GameLogic;
import com.cs446.purfect_litter.LogicManager.GameState;
import com.cs446.purfect_litter.LogicManager.Player;
import com.cs446.purfect_litter.LogicManager.CardManager.CardDef;
import com.cs446.purfect_litter.LogicManager.CardManager.CardInstance;
import com.cs446.purfect_litter.LogicManager.CardManager.CatCard;

public class ActionPhase implements AbstractPhase{

	@Override
	public boolean pickCard(CardInstance chosen, GameLogic wrapper) {

		// CHECK TO SEE IF ITS A BLUE MAID CARD
		CardDef look = chosen.getDef();
		if (look.getType() != CardDef.ctype.CAT)
		{
			return false;
		}
		CatCard catlook = (CatCard) look;
		
		if (wrapper.getGs().currentAction < 1)
		{
			return false;
		}
		
		if (catlook.getColor() == 0)//if its a green maid, we dont want it.
		{
			return false;
		}
		
		wrapper.getMe().move(chosen, Player.Pile.HAND, Player.Pile.PLAYED);
		wrapper.getGs().currentAction += -1 + catlook.getAction(); //HOW DO I ACCESS THIS?
		wrapper.getGs().currentLove += catlook.getLove();
		wrapper.getGs().currentPurchase += catlook.getPurchase();
		for (int i =0; i< catlook.getDraw(); i++)
		{
			wrapper.getMe().move(Player.Pile.DECK, Player.Pile.HAND);
		}
		wrapper.getGs().lastActions+= wrapper.getMe().getName() + " plays " + catlook.getName() + "\n";
		return true;
	}

	@Override
	public void init(GameLogic wrapper) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextPhase(GameLogic wrapper) {
		
		wrapper.getGs().lastActions+= wrapper.getMe().getName() + " ends his action phase\n";
		wrapper.getGs().currentPhase = GameState.phase.CHAMBER;
		wrapper.setPhase((AbstractPhase) new ChamberPhase());
		
	}

}
