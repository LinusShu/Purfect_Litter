
public class purchasePhase implements abstractPhase{

	@Override
	public void pickCard(cardInstance chosen, gameLogic wrapper) {
		
		//FIND IT IN TOWN
		
		//MAKE SURE YOU CAN AFFORD
		if (chosen.cost > wrapper.table.currentLove)
		{
			//throw error
		}
		
		wrapper.table.currentLove-= chosen.cost;
		wrapper.me.take(wrapper.table.townRemove(chosen), player.pile.PLAYED);
		wrapper.table.lastActions+= wrapper.me.getName() + " bought a " + chosen.getName() + "\n";
		
	}

	@Override
	public void init(gameLogic wrapper) {
		
		wrapper.table.currentLove += wrapper.me.getLoveInHand();
		
	}

	@Override
	public void nextPhase(gameLogic wrapper) {
		
		wrapper.table.lastActions+= wrapper.me.getName() + " ends his purchase phase\n";
		wrapper.table.currentPhase = gameState.phase.END;
		wrapper.setPhase(new endPhase());
	}

}
