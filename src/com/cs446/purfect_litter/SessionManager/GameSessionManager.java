package com.cs446.purfect_litter.SessionManager;

import com.cs446.purfect_litter.LogicManager.GameState;



public abstract class GameSessionManager {
	
	//called to send game state updates to default targets
	//See comments in ClientManager and ServerManager for detailed behaviour
	public abstract boolean send(GameState g);
	
	
	//should not be called manually other than the CommsTask class
	public abstract void receive(GameState fromClient);
	
	//used to tear down connection and kill application processes when network error occurs
	public abstract void shutDown();
	
}
