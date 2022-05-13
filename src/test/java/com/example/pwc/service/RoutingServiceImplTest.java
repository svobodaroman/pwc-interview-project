package com.example.pwc.service;

import com.example.pwc.algorithm.RouteAlgorithm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class RoutingServiceImplTest {

    @Mock
    RouteAlgorithm routeAlgorithmMock;

    @InjectMocks
    RoutingServiceImpl routingService;

    @Test
    void getPath_happy() {

        var pathCzeEsp = List.of("CZE", "DEU", "ESP");
        doReturn(pathCzeEsp)
                .when(routeAlgorithmMock)
                .getShortestPathCountriesCca3(any(), any());

        // act
        List<String> result = routingService.getPath("CZE", "ESP");

        //assert
        assertThat(result).isEqualTo(pathCzeEsp);
    }

    @Test
    void getPath_expectExceptionWhenNull() {
        // act
        try {
            routingService.getPath("CZE", null);
        } catch (NullPointerException e) {
            //assert
            assertThat(e.getClass()).isEqualTo(NullPointerException.class);
        }
    }
}