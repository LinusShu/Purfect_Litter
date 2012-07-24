package com.cs446.purfect_litter.gameLogicManager.cardManager;

public class EventCard extends CardDef {
	private int vp;
	//private Effect effect;
	
	public EventCard(int cost, String name, int n_vp, int number /*, Effect n_effect*/){
		super(cost,name,number);
		vp = n_vp;
		t = ctype.EVENT;
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
