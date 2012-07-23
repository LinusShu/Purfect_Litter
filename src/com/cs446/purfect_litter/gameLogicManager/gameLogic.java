
import java.util.ArrayList;



public class gameLogic {
	
	gameState table;
	player me;
	abstractPhase currentPhase;
	gameSessionManager comm;
	
	void setPhase(abstractPhase tothis)
	{
		currentPhase = tothis;
		comm.send(table);
		currentPhase.init(this);
	}
	
	void update(gameState newGS)
	{
		table = newGS;
		
		//TALK TO VIEW, POST UPDATES
		view.updatelog(table.lastActions);
		table.lastActions = "";
		
		if (table.gameOver())
		{
			
		}
		
		if (table.currentPlayer == me && table.currentPhase == gameState.phase.END)
		{
			setPhase(new startingPhase());
		}
	}
}
