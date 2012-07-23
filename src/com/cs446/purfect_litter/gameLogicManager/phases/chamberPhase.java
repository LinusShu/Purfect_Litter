
public class chamberPhase implements abstractPhase{

	@Override
	public void pickCard(cardInstance chosen, gameLogic wrapper) {
		// TODO Auto-generated method stub

		if (chosen.chamberCost = 0) {
			// Throw error;
		}
		
		if (wrapper.table.currentAction < chosen.chamberCost) {
			// Throw error;
		}

		wrapper.me.move(chosen, player.pile.HAND, player.pile.CHAMBER);
		wrapper.table.currentAction -= chosen.chamberCost;
		wrapper.table.lastActions+= wrapper.me.getName() + " sends " + chosen.getName() + " to his chamber.\n";

	}

	@Override
	public void init(gameLogic wrapper) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextPhase(gameLogic wrapper) {
		
		wrapper.table.lastActions+= wrapper.me.getName() + " ends his chamber phase\n";
		wrapper.table.currentPhase = gameState.phase.PURCHASE;
		wrapper.setPhase(new purchasePhase());
		
	}

}
