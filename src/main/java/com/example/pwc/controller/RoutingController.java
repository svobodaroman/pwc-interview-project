package com.example.pwc.controller;

import com.example.pwc.service.RoutingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/routing", produces = APPLICATION_JSON_VALUE)
public class RoutingController {

    private final RoutingService routingService;

    @GetMapping("/{from}/{to}")
    public List<String> path(@PathVariable String from, @PathVariable String to) {
        return routingService.getPath(from, to);
    }
}
