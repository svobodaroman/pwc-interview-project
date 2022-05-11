package com.example.pwc.exception;

public class PathNotFoundException extends RuntimeException {

    public PathNotFoundException() {
        super("Path not found.");
    }
}
