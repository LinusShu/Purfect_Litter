
public class startingPhase implements abstractPhase {

	@Override
	public void pickCard(cardInstance chosen, gameLogic wrapper) {
		// Do nothing or raise error
		
	}

	@Override
	public void init(gameLogic wrapper) {
		
		wrapper.table.currentAction = 1;
		wrapper.table.currentPurchase = 1;
		wrapper.table.lastActions+= wrapper.me.getName() + "'s turn begins\n";
		wrapper.table.currentPhase = gameState.phase.ACTION;
		wrapper.setPhase(new actionPhase());
		
	}

	@Override
	public void nextPhase(gameLogic wrapper) {
		//NOTHING
		
	}

	

}
