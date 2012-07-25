package com.cs446.purfect_litter.gameLogicManager.cardManager;

public class CardInstance {
	int defRef;

	public CardInstance(int dr) 
	{
		defRef = dr;
	}
	
	public CardDef getDef()
	{
		return CardDefLib.lib.get(defRef);
	}

}
