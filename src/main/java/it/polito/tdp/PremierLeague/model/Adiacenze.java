package it.polito.tdp.PremierLeague.model;

public class Adiacenze {

	Action p1;
	Action p2;
	
	public Adiacenze(Action p1, Action p2) {

		this.p1 = p1;
		this.p2 = p2;

	}

	public Action getP1() {
		return p1;
	}

	public void setP1(Action p1) {
		this.p1 = p1;
	}

	public Action getP2() {
		return p2;
	}

	public void setP2(Action p2) {
		this.p2 = p2;
	}

}
