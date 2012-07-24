package com.cs446.purfect_litter.gameSessionManager;

import com.cs446.purfect_litter.gameLogicManager.GameState;



public abstract class GameSessionManager {
	
	//called to send game state updates to default targets
	//See comments in ClientManager and ServerManager for detailed behaviour
	public abstract void send(GameState g);
	
	
	//should not be called manually other than the CommsTask class
	public void receive(GameState fromClient){
		//TODO notify GameFlowManager when received a game state update
	}
	
	//used to tear down connection and kill application processes when network error occurs
	public void shutDown(){
		try {
			int pid = android.os.Process.myPid();
			android.os.Process.killProcess(pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
