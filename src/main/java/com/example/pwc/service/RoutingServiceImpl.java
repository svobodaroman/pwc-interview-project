package com.example.pwc.service;

import com.example.pwc.algorithm.RouteAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class RoutingServiceImpl implements RoutingService {

    private final RouteAlgorithm routeAlgorithm;

    @Override
    public List<String> getPath(String from, String to) {
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        return routeAlgorithm.getShortestPathCountriesCca3(from, to);
    }

}
