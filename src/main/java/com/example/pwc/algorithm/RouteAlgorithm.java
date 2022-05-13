package com.example.pwc.algorithm;

import com.example.pwc.exception.PathNotFoundException;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;

import java.util.List;
import java.util.Optional;

public class RouteAlgorithm {

    private final ShortestPathAlgorithm<String, DefaultEdge> shortestPathAlgorithm;

    public RouteAlgorithm(Graph<String, DefaultEdge> graph,
                          ShortestPathAlgorithms alg) {
        shortestPathAlgorithm = createShortestPathAlgorithm(graph, alg);
    }

    public List<String> getShortestPathCountriesCca3(String from, String to) {
        return Optional.ofNullable(shortestPathAlgorithm.getPath(from, to))
                .orElseThrow(PathNotFoundException::new)
                .getVertexList();
    }

    static ShortestPathAlgorithm<String, DefaultEdge> createShortestPathAlgorithm(Graph<String, DefaultEdge> graph,
                                                                                  ShortestPathAlgorithms alg) {
        switch (alg) {
            case DIJKSTRA:
                return new DijkstraShortestPath<>(graph);
            case BFS:
                return new BFSShortestPath<>(graph);
            case BELLMANFORD:
                return new BellmanFordShortestPath<>(graph);
            default:
                throw new Error("Algorithm not supported.");
        }
    }
}
