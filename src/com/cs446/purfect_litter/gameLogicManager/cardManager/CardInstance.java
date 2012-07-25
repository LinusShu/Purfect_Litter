package com.cs446.purfect_litter.gameLogicManager.cardManager;

import java.io.Serializable;

public class CardInstance implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1235588209513825456L;
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
