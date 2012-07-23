
public class actionPhase implements abstractPhase{

	@Override
	public void pickCard(cardInstance chosen, gameLogic wrapper) {

		// CHECK TO SEE IF ITS A BLUE MAID CARD
		
		if (wrapper.table.currentAction < 1)
		{
			//Throw error;
		}
		
		wrapper.me.move(chosen, player.pile.HAND, player.pile.PLAYED);
		wrapper.table.currentAction += -1 + chosen.getAction(); //HOW DO I ACCESS THIS?
		wrapper.table.currentLove += chosen.getLove();
		wrapper.table.currentPurchase += chosen.getPurchase();
		for (int i =0; i< chosen.getDraw(); i++)
		{
			wrapper.me.move(player.pile.DECK, player.pile.HAND);
		}
		wrapper.table.lastActions+= wrapper.me.getName() + " plays " + chosen.getName() + "\n";
		
	}

	@Override
	public void init(gameLogic wrapper) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextPhase(gameLogic wrapper) {
		
		wrapper.table.lastActions+= wrapper.me.getName() + " ends his action phase\n";
		wrapper.table.currentPhase = gameState.phase.CHAMBER;
		wrapper.setPhase(new chamberPhase());
		
	}

}
