package org.example.branch.controller;

import org.example.branch.service.GitRestClientService;
import org.example.branch.dto.GithubUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import static org.springframework.http.HttpStatus.*;

// Provides a RESTful GET endpoint that takes a username and retrieves a combined Git User + their Repos

@RestController
public class GithubUserController {

    final GitRestClientService gitApiService;

    public GithubUserController(GitRestClientService gitApiService) {
        this.gitApiService = gitApiService;
    }

    @GetMapping("/githubuser/{username}")
    public ResponseEntity<GithubUserDto> githubUser(@PathVariable String username) {
        var resp = gitApiService.getUser(username);

        if (resp == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(resp);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleClientError(HttpClientErrorException e) {
        return switch (e.getStatusCode()) {
            case BAD_REQUEST -> ResponseEntity.badRequest().body("Invalid request to the GitHub API");
            case NOT_FOUND -> ResponseEntity.status(404).body("Resource not found");
            default -> ResponseEntity.status(e.getStatusCode()).body("Client error: {}" + e.getMessage());
        };
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<String> handleServerError(HttpServerErrorException e) {
        if (e.getStatusCode().equals(INTERNAL_SERVER_ERROR)) {
            return ResponseEntity.status(500).body("Internal server error at the GitHub API");
        }
        return ResponseEntity.status(e.getStatusCode()).body("Server error: " + e.getMessage());
    }
}
