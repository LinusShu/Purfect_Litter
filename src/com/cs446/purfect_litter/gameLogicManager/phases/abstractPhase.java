
public interface abstractPhase {

	void pickCard(cardInstance chosen, gameLogic wrapper);
	void init(gameLogic wrapper);
	void nextPhase(gameLogic wrapper);
}
