package com.example.pwc.controller;

import com.example.pwc.service.RoutingService;
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
class RoutingControllerTest {

    @Mock
    RoutingService routingServiceMock;

    @InjectMocks
    RoutingController routingController;

    @Test
    void path() {
        var pathCzeEsp = List.of("CZE", "DEU", "ESP");
        doReturn(pathCzeEsp)
                .when(routingServiceMock)
                .getPath(any(), any());

        List<String> result = routingController.path("CZE", "ESP");

        assertThat(result).isEqualTo(pathCzeEsp);
    }
}