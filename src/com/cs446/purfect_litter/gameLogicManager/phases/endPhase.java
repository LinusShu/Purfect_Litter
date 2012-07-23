
public class endPhase implements abstractPhase{

	@Override
	public void pickCard(cardInstance chosen, gameLogic wrapper) {
		// do nothing
		
	}

	@Override
	public void init(gameLogic wrapper) {
		wrapper.me.move(player.pile.HAND, player.pile.PLAYED);
		wrapper.me.move(player.pile.PLAYED, player.pile.DISCARD);
		for (int i = 0; i<5; i++)
		{
			wrapper.me.move(player.pile.DECK, player.pile.HAND);
		}
		wrapper.table.currentAction = 0;
		wrapper.table.currentLove = 0;
		wrapper.table.currentPurchase = 0;
		
		wrapper.table.currentPhase = gameState.phase.ACTION;
		wrapper.table.lastActions+= wrapper.me.getName() + " ends his turn\n";
		wrapper.me = wrapper.table.nextplayer();
		wrapper.comm.send(wrapper.table);
		
	}

	@Override
	public void nextPhase(gameLogic wrapper) {
		// do nothing
		
	}
	

}
