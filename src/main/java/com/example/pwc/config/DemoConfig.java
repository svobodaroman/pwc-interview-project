package com.example.pwc.config;

import com.example.pwc.algorithm.RouteAlgorithm;
import com.example.pwc.graph.GraphSupplier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
@EnableConfigurationProperties(value = {AppProperties.class, AlgorithmProperties.class})
public class DemoConfig {

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter() {{
            setSupportedMediaTypes(List.of(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        }};
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(List.of(mappingJackson2HttpMessageConverter()));
    }

    @Bean
    public RouteAlgorithm shortestPathAlgorithm(GraphSupplier graphSupplier,
                                                AlgorithmProperties algorithmProperties) {
        return new RouteAlgorithm(graphSupplier.getGraph(), algorithmProperties.getShortestPath());
    }

}
