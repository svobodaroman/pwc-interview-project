package com.example.pwc.graph;

import com.example.pwc.config.AppProperties;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.jgrapht.util.SupplierUtil.DEFAULT_EDGE_SUPPLIER;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GraphSupplierTest {

    @Test
    void initGraph() {
        //arrange
        var appPropertiesMock = mock(AppProperties.class);
        var restTemplateMock = mock(RestTemplate.class);
        var graphSupplier = new GraphSupplier(appPropertiesMock, restTemplateMock);

        Graph<String, DefaultEdge> graphMock = GraphTypeBuilder.<String, DefaultEdge>undirected()
                .edgeSupplier(DEFAULT_EDGE_SUPPLIER)
                .buildGraph();
        List.of("CZE", "DEU", "FRA", "ESP", "PRT", "POL").forEach(graphMock::addVertex);
        graphMock.addEdge("CZE", "DEU");
        graphMock.addEdge("CZE", "POL");
        graphMock.addEdge("DEU", "FRA");
        graphMock.addEdge("DEU", "POL");
        graphMock.addEdge("FRA", "ESP");
        graphMock.addEdge("ESP", "PRT");

        var countries = new Country[]{
                new Country("CZE", List.of("DEU", "POL")),
                new Country("DEU", List.of("CZE", "FRA", "POL")),
                new Country("FRA", List.of("DEU", "ESP")),
                new Country("ESP", List.of("PRT", "FRA")),
                new Country("PRT", List.of("ESP")),
                new Country("POL", List.of("CZE", "DEU"))
        };

        doReturn("www.testurl.com")
                .when(appPropertiesMock)
                .getCountriesUrl();
        doReturn(ResponseEntity.ok(countries))
                .when(restTemplateMock)
                .getForEntity("www.testurl.com", Country[].class);

        // act
        graphSupplier.initGraph();
        Graph<String, DefaultEdge> result = graphSupplier.getGraph();

        //assert
        assertThat(result.toString(), equalTo(graphMock.toString()));
    }

    @Test
    void whenDivideByZero_thenAssertException() {
        //arrange
        var appPropertiesMock = mock(AppProperties.class);
        var restTemplateMock = mock(RestTemplate.class);
        var graphSupplier = new GraphSupplier(appPropertiesMock, restTemplateMock);
        doThrow(RestClientException.class)
                .when(restTemplateMock)
                .getForEntity(any(), any());

        //assert
        assertThrows(Error.class, graphSupplier::initGraph);
    }
}