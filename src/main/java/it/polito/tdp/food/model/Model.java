package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDAO;

public class Model {
	private Graph<String, DefaultWeightedEdge> grafo;
	private FoodDAO dao;
	private List<String> porzioni;
	
	//Ricorsione
	private double pesoMax;
	private List<String> camminoMax;
	
	
	public Model() {
		dao = new FoodDAO();
	}
	
	public String creaGrafo(int C) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.porzioni = dao.getPortionDisplayNames(C);
		
		//Aggiungo i vertici
		Graphs.addAllVertices(this.grafo, porzioni);
		
		//Aggiungo gli archi
		List<InfoArco> archi = dao.getTuttiArchi();
		
		for(InfoArco a: archi) {
			if(this.grafo.vertexSet().contains(a.getVertice1()) &&
					this.grafo.vertexSet().contains(a.getVertice2())) {
				Graphs.addEdge(this.grafo, a.getVertice1(), a.getVertice2(), a.getPeso());
			}
		}
		
		return String.format("Grafo creato con %d vertici e %d archi.", this.grafo.vertexSet().size(), this.grafo.edgeSet().size());
	}
	
	public List<String> getVertici(){
		return this.porzioni;
	}
	
	public List<PorzioneAdiacente> getAdiacenti(String porzione){
		List<String> vicini = Graphs.neighborListOf(this.grafo, porzione);
		
		List<PorzioneAdiacente> result = new ArrayList<>();
		for(String v: vicini) {
			double peso = this.grafo.getEdgeWeight(this.grafo.getEdge(porzione, v));
			result.add(new PorzioneAdiacente(v, peso));
		}
		return result;
	}
	
	public void trovaCammino(int N, String partenza) {
		this.camminoMax = null;
		this.pesoMax = 0;
		
		List<String> parziale = new ArrayList<>();
		parziale.add(partenza);
		
		search(parziale, 1, N);
	}
	
	private void search(List<String> parziale, int livello, int N) {
		if(livello == N+1) {
			double peso = pesoCammino(parziale);
			if(peso > pesoMax) {
				this.pesoMax = peso;
				this.camminoMax  = new ArrayList<>(parziale);
			}
			return ;
		}
		
		List<String> vicini = Graphs.neighborListOf(this.grafo, parziale.get(livello-1));
		for(String vicino: vicini) {
			if(!parziale.contains(vicino)) {
				parziale.add(vicino);
				search(parziale, livello+1, N);
				parziale.remove(parziale.size()-1);
			}
		}
	}

	private double pesoCammino(List<String> parziale) {
		double peso = 0.0;
		for(int i=1; i<parziale.size(); i++) {
			double p = this.grafo.getEdgeWeight(this.grafo.getEdge(parziale.get(i-1), parziale.get(i)));
			peso += p;
		}
		return peso;
	}
	
	public double getPesoMax() {
		return this.pesoMax;
	}
	
	public List<String> getCamminoMax(){
		return this.camminoMax;
	}
}














