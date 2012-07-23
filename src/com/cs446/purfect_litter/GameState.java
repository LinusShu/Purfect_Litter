package com.cs446.purfect_litter;

import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable{
	public int id, currentLove, currentAction, currentPurchase;
	public List<String> lastActions;
	
	public int getID() {
		return this.id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
}
