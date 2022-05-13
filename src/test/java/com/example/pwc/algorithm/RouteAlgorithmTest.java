package com.example.pwc.algorithm;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.junit.jupiter.api.Test;

import static com.example.pwc.algorithm.RouteAlgorithm.createShortestPathAlgorithm;
import static com.example.pwc.algorithm.ShortestPathAlgorithms.BFS;
import static com.example.pwc.algorithm.ShortestPathAlgorithms.DIJKSTRA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class RouteAlgorithmTest {

    @Test
    void createShortestPathAlgorithm_dijkstra() {
        Graph<String, DefaultEdge> mock = mock(Graph.class);

        //act
        var routeAlgorithm = createShortestPathAlgorithm(mock, DIJKSTRA);

        //assert
        assertEquals(routeAlgorithm.getClass(), DijkstraShortestPath.class);
    }


    @Test
    void createShortestPathAlgorithm_bfs() {
        Graph<String, DefaultEdge> mock = mock(Graph.class);

        //act
        var routeAlgorithm = createShortestPathAlgorithm(mock, BFS);

        //assert
        assertEquals(routeAlgorithm.getClass(), BFSShortestPath.class);
    }
}