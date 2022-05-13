package com.example.pwc.graph;

import com.example.pwc.config.AppProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.jgrapht.util.SupplierUtil.DEFAULT_EDGE_SUPPLIER;

@Component
@Slf4j
@RequiredArgsConstructor
public class GraphSupplier {

    private final AppProperties appProperties;
    private final RestTemplate restTemplate;
    @Getter
    private Graph<String, DefaultEdge> graph;

    @PostConstruct
    public void initGraph() {
        try {
            List<Country> countries = loadCountries();
            log.info("{} loaded {} countries from uri: {}", getClass().getCanonicalName(), countries.size(), appProperties.getCountriesUrl());
            graph = GraphTypeBuilder.<String, DefaultEdge>undirected()
                    .edgeSupplier(DEFAULT_EDGE_SUPPLIER)
                    .buildGraph();

            countries.forEach(country -> graph.addVertex(country.cca3));
            countries.forEach(country -> country.borders.forEach(b -> graph.addEdge(country.cca3, b)));

            log.info("Graph initialized with {} countries and {} edges", graph.vertexSet().size(), graph.edgeSet().size());
        } catch (Exception e) {
            throw new Error("Graph init error.", e);
        }
    }

    private List<Country> loadCountries() {
        Country[] body = restTemplate.getForEntity(appProperties.getCountriesUrl(), Country[].class).getBody();
        var responseEntity = Objects.requireNonNull(body, "Countries response does not contain body.");
        return Arrays.asList(responseEntity);

    }
}
