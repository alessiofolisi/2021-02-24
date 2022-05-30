package it.polito.tdp.PremierLeague.model;

public class Event implements Comparable<Event>{
	
	public enum EventType{
		
		GOAL_CASA,
		GOAL_OSPITE,
		ESPULSIONE_CASA,
		ESPULSIONE_OSPITE,
		INFORTUNIO
		
	}

	private EventType type;
	
	public Event(EventType type) {
		this.type = type;
	}

	public EventType getType() {
		return type;
	}

	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return 0;
	}
	

	
}
