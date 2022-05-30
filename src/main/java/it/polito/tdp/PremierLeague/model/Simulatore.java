package it.polito.tdp.PremierLeague.model;

import java.util.PriorityQueue;


import it.polito.tdp.PremierLeague.model.Event.EventType;

public class Simulatore {
	
	private Match m;
	private int nCasa;
	private int nOspite;
	private int goalCasa;
	private int goalTrasferta;
	private int N;
	
	private PriorityQueue<Event> queue;
	
	public Simulatore(Match m , int N) {
		this.m = m;
		this.nCasa = 11;
		this.nOspite = 11;
		this.goalCasa = 0;
		this.goalTrasferta = 0;
		this.N = N;
	}	
	
	public void creaEvento(BestPlayer bestPlayer) {
			Event e = null;	
			if(Math.random()<=0.5) {
				if(nCasa>nOspite) {
					e = new Event(EventType.GOAL_CASA);
					this.queue.add(e);
					return;
				}else if(nCasa<nOspite) {
					e = new Event(EventType.GOAL_OSPITE);
					this.queue.add(e);
					return;
				}else if(nCasa==nOspite) {
					if(bestPlayer.getPlayerAction().getTeamID()==m.getTeamHomeID()){
						e = new Event(EventType.GOAL_CASA);
						this.queue.add(e);
						return;
					}else if(bestPlayer.getPlayerAction().getTeamID()==m.getTeamAwayID()) {
						e = new Event(EventType.GOAL_OSPITE);
						this.queue.add(e);
						return;
					}
				}
			}else if(Math.random()<=0.3) {
				if(bestPlayer.getPlayerAction().getTeamID()==m.getTeamHomeID()) {
					if(Math.random()<=0.6) {
						e = new Event(EventType.ESPULSIONE_CASA);
						this.queue.add(e);
						return;
					}else if(Math.random()<=0.4) {
						e = new Event(EventType.ESPULSIONE_OSPITE);
						this.queue.add(e);
						return;
					}}else if(bestPlayer.getPlayerAction().getTeamID()==m.getTeamAwayID()) {
						if(Math.random()<=0.6) {
							e = new Event(EventType.ESPULSIONE_OSPITE);
							this.queue.add(e);
							return;
						}else if(Math.random()<=0.4) {
							e = new Event(EventType.ESPULSIONE_CASA);
							this.queue.add(e);
							return;
						}
					}
			}else if(Math.random()<=0.2) {
				e = new Event(EventType.INFORTUNIO);
				this.queue.add(e);
				return;
			}	
		}
	
	public void init(BestPlayer p) {
		this.queue = new PriorityQueue<Event>();
		this.queue.clear();
		for(int i=0;i<this.N;i++) {
			creaEvento(p);
		}
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = queue.poll();
			processEvent(e);
		}
	}
	
	public void processEvent(Event e) {
		
		switch(e.getType()) {
		
		case GOAL_CASA:
			this.goalCasa++;
			break;
		case GOAL_OSPITE:
			this.goalTrasferta++;
			break;
		case ESPULSIONE_CASA:
			this.nCasa--;
			break;
		case ESPULSIONE_OSPITE:
			this.nOspite--;
			break;
		case INFORTUNIO:
			if(Math.random()<=0.5) {
				this.N = this.N+2;
			}else{
				this.N = this.N+3;
			}break;
			
		}	
	}
	
	public String getResult() {
		int nEspulsiC = 11-this.nCasa;
		int nEspulsiO = 11-this.nOspite;
		int nEspulsi = nEspulsiC + nEspulsiO;
		return "RISULTATO "+this.m.toString() +"\n"+ this.goalCasa + " - " + this.goalTrasferta + "\nESPULSI: "+nEspulsi;
	}
		

}
