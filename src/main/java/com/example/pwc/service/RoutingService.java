package com.example.pwc.service;

import java.util.List;

public interface RoutingService {
    List<String> getPath(String from, String to);
}
