package com.cs446.purfect_litter.LogicManager;

import java.util.ArrayList;

import android.R.integer;

import com.cs446.purfect_litter.MainActivity;
import com.cs446.purfect_litter.MainActivity.SessionType;
import com.cs446.purfect_litter.LogicManager.Player.Pile;
import com.cs446.purfect_litter.LogicManager.CardManager.CardInstance;
import com.cs446.purfect_litter.LogicManager.GameState.TownPile;
import com.cs446.purfect_litter.SessionManager.ClientManager;
import com.cs446.purfect_litter.SessionManager.GameSessionManager;
import com.cs446.purfect_litter.SessionManager.ServerManager;

/**
 * Facade interface.
 *
 */
public class Game {
	
	private MainActivity gameUI;
	private GameSessionManager gameSessionManager;
	private GameLogic gameLogic;
	
	public Game(MainActivity mainActivity, SessionType sessionType) {
		gameUI = mainActivity;
		
		switch (sessionType) {
		case HOST:
			gameSessionManager = new ServerManager(this);
			break;
		case CLIENT:
			gameSessionManager = new ClientManager(this);
		default:
			break;
		}
	}
	

	// SETTERS ======================================================
	
	public void createLogicForServer(ArrayList<String> playerNames)
	{
		gameLogic = new GameLogic(playerNames, this);
		doSendGameState(gameLogic.getGs());
		if (gameLogic.update(gameLogic.getGs())) {
			doSendGameState(gameLogic.getGs());
		}
	}
	
	public void createLogicForClient(GameState newGs, String playerName)
	{
		gameLogic = new GameLogic(newGs, playerName, this);
		if (gameLogic.update(gameLogic.getGs())) {
			doSendGameState(gameLogic.getGs());
		}
	}
	
	public void doSendGameState(GameState curState) {
		gameUI.update();
		gameSessionManager.send(new GameState(curState));
	}
	
	public void doReceiveGameState(GameState newGs) {
		if (gameLogic.update(newGs)) {
			doSendGameState(gameLogic.getGs());
		}
		else {
			gameUI.update();
		}
	}
	
	public void doNextPhase() {
		gameLogic.getPhase().nextPhase(gameLogic);
		doSendGameState(gameLogic.getGs());
	}
	
	public boolean doPickCard(CardInstance whichCard) {
		boolean result = gameLogic.getPhase().pickCard(whichCard, gameLogic);
    	gameUI.update();
    	return result;
	}

	// GETTERS ======================================================
	
	public boolean isItMyTurn() {
		return gameLogic.getGs().currentPlayer.equals(gameLogic.getMe());
	}
	
	public String getMyName() {
		return gameLogic.getMe().getName();
	}
	
	public Integer[] getMyCardPile(Pile whichPile) {
		return gameLogic.getMe().getImageArray(whichPile);
	}
	
	public CardInstance getMyCard(Pile whichPile, int index) {
		return gameLogic.getMe().piles.get(whichPile.index).get(index);
	}
	
	public Integer[] getTownCardPile(TownPile whichPile) {
		return gameLogic.getGs().getImageArray(whichPile);
	}
	
	public CardInstance getTownCard(TownPile whichPile, int index) {
		if (gameLogic.getGs().townCards.get(whichPile.index).get(index).size() > 0) {
			return gameLogic.getGs().townCards.get(whichPile.index).get(index).get(0);
		}
		else {
			return null;
		}
	}

	public String getCurrentPhase() {
		return gameLogic.getGs().currentPhase.getName();
	}
	
	public GameState getGameState() {
		return gameLogic.getGs();
	}
}
