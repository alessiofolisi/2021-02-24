package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	PremierLeagueDAO dao;
	Map<Integer,Player> idMap;
	Graph<Player, DefaultWeightedEdge> grafo;
	Map<Integer,Action> idMapAction;
	BestPlayer bp;
	
	//simulatore
	private Simulatore simulatore;
	
	public Model() {
		dao = new PremierLeagueDAO();
		
	}
	
	public List<Match> getAllMatch(){
		
		List<Match> list = dao.listAllMatches();
		
		Collections.sort(list);
		
		return list;
	}
	
	public void setidMap() {
		this.idMap = new HashMap<Integer,Player>();
		
		List<Player> lista = dao.listAllPlayers();
		for(Player a : lista) {
			idMap.put(a.getPlayerID(), a);
		}
	
	}
	
	public void creaGrafo(Match m) {
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		this.idMapAction = new HashMap<Integer,Action>();
		List<Action> list = dao.listAMatchActions(m);
		for(Action a : list) {
			idMapAction.put(a.getPlayerID(), a);
		}
		
		List<Adiacenze> listaAdiacenze = dao.getAdiacenze(m, this.idMapAction);
		
		for(Adiacenze ad : listaAdiacenze) {
			double ef1 = (ad.getP1().getAssists()+ad.getP1().getTotalSuccessfulPassesAll());
			ef1 = ef1/ad.getP1().getTimePlayed();
			double ef2 = (ad.getP2().getAssists()+ad.getP2().getTotalSuccessfulPassesAll());
			ef2 = ef2/ad.getP2().getTimePlayed();
			double peso = ef1-ef2;
			if(peso>=0) {
				
				Graphs.addEdgeWithVertices(this.grafo, this.idMap.get(ad.getP1().getPlayerID()), this.idMap.get(ad.getP2().getPlayerID()), peso);
			
			}else if(peso<0) {
				
				Graphs.addEdgeWithVertices(this.grafo, this.idMap.get(ad.getP2().getPlayerID()), this.idMap.get(ad.getP1().getPlayerID()), Math.abs(peso));
			}
			
		}
	
	}
	
	public String getVertici() {
		return "#VERTICI: "+this.grafo.vertexSet().size() + "\n";
	}
	
	public String getArchi() {
		return "#ARCHI: "+this.grafo.edgeSet().size()+"\n";
	}
	
	public String getBestPlayer(Match m) {
		Player player = null;
		double delta = 0;
		
		for(Player p : this.grafo.vertexSet()) {
			double deltaV = 0;
			double pesoIn = 0;
			double pesoOut = 0;
			for(DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(p)) {
				pesoOut+=this.grafo.getEdgeWeight(e);
			}
			for(DefaultWeightedEdge e1 : this.grafo.incomingEdgesOf(p)) {
				pesoIn+=this.grafo.getEdgeWeight(e1);
			}
			deltaV = pesoOut - pesoIn;
			if(delta<deltaV) { 
				delta = deltaV; player = p;
				}
		}
		List<Action> azioni = new ArrayList<Action>(dao.listAMatchActions(m));
		for(Action a : azioni) {
			if(a.getPlayerID().equals(player.getPlayerID())) {
				this.bp = new BestPlayer(a,delta);
			}
		}
		
		return "Giocatore Migliore: \n"+player.getPlayerID()+"-"+player.getName()+", delta efficienza = " + delta;
	}


	public String simula(Match m, int N) {
		simulatore = new Simulatore(m,N);
		simulatore.init(bp);
		simulatore.run();
		return simulatore.getResult();
	}


}
