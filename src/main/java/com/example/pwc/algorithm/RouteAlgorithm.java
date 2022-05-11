package com.example.pwc.algorithm;


import com.example.pwc.config.AlgorithmProperties;
import com.example.pwc.exception.PathNotFoundException;
import com.example.pwc.graph.GraphInitializer;
import lombok.RequiredArgsConstructor;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RouteAlgorithm {

    private final GraphInitializer graphInitializer;
    private final AlgorithmProperties algorithmProperties;
    private ShortestPathAlgorithm<String, DefaultEdge> shortestPathAlgorithm;

    @PostConstruct
    public void init() {
        var graph = graphInitializer.initGraph();
        shortestPathAlgorithm = getAlgorithm(graph, algorithmProperties.getShortestPath());
    }

    private ShortestPathAlgorithm<String, DefaultEdge> getAlgorithm(Graph<String, DefaultEdge> graph, ShortestPathAlgorithms alg) {
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

    public List<String> getPath(String from, String to) {
        return Optional.ofNullable(shortestPathAlgorithm.getPath(from, to))
                .orElseThrow(PathNotFoundException::new)
                .getVertexList();
    }
}
