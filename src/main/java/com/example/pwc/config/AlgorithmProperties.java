package com.example.pwc.config;

import com.example.pwc.algorithm.ShortestPathAlgorithms;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("app.alg")
public class AlgorithmProperties {
    ShortestPathAlgorithms shortestPath;
}
