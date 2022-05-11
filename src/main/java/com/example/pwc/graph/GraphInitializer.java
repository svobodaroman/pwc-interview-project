package com.example.pwc.graph;

import com.example.pwc.config.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.jgrapht.util.SupplierUtil.DEFAULT_EDGE_SUPPLIER;

@Slf4j
@RequiredArgsConstructor
@Component
public class GraphInitializer {

    private final AppProperties appProperties;
    private final RestTemplate restTemplate;

    public Graph<String, DefaultEdge> initGraph() {
        try {
            List<Country> countries = loadCountries();
            log.info("{} loaded {} countries from uri: {}", getClass().getCanonicalName(), countries.size(), appProperties.getCountriesUrl());
            var graph = GraphTypeBuilder.<String, DefaultEdge>undirected()
                    .edgeSupplier(DEFAULT_EDGE_SUPPLIER)
                    .buildGraph();

            countries.forEach(country -> graph.addVertex(country.cca3));
            countries.forEach(country -> country.borders.forEach(b -> graph.addEdge(country.cca3, b)));

            log.info("Graph initialized with {} countries and {} edges", graph.vertexSet().size(), graph.edgeSet().size());
            return graph;
        } catch (Exception e) {
            throw new Error("Graph init error.", e);
        }
    }

    private List<Country> loadCountries() {
        var responseEntity = Objects.requireNonNull(restTemplate.getForEntity(appProperties.getCountriesUrl(), Country[].class).getBody(),
                "Countries response does not contain body.");
        return Arrays.asList(responseEntity);

    }
}
