package it.polito.tdp.PremierLeague.model;

public class BestPlayer {
	
	private Action playerAction;
	private double delta;
	
	public BestPlayer(Action playerAction, double delta) {

		this.playerAction = playerAction;
		this.delta = delta;
	}

	public Action getPlayerAction() {
		return playerAction;
	}

	public void setPlayerAction(Action playerAction) {
		this.playerAction = playerAction;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}
	

}
