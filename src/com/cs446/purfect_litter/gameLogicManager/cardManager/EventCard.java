package com.cs446.purfect_litter.gameLogicManager.cardManager;

public class EventCard extends Card {
	private int vp;
	//private Effect effect;
	
	public EventCard(int cost, String name, int n_vp /*, Effect n_effect*/){
		super(cost,name);
		vp = n_vp;
		//effect = n_effect;
	}
	
	public int getVP() {
		return vp;
	}

	/*public Effect getEffect() {
	 * 	return effect;
	 * }
	 */
}
