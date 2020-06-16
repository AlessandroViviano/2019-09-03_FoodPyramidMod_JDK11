package it.polito.tdp.food.model;

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
}
