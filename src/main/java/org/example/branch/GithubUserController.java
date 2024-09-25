package org.example.branch;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import static org.springframework.http.HttpStatus.*;

@RestController
public class GithubUserController {

    final GitApiClientService gitApiService;

    public GithubUserController(GitApiClientService gitApiService) {
        this.gitApiService = gitApiService;
    }

    @GetMapping("/githubuser/{username}")
    public ResponseEntity<GithubUser> githubUser(@PathVariable String username) {
        gitApiService.getUserRepos(username);

        var resp = gitApiService.getUser(username);

        if (resp == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(resp);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleClientError(HttpClientErrorException e) {
        switch (e.getStatusCode()) {
            case BAD_REQUEST:
                return ResponseEntity.badRequest().body("Invalid request to the GitHub API");
            case NOT_FOUND:
                return ResponseEntity.status(404).body("Resource not found");
            default:
                return ResponseEntity.status(e.getStatusCode()).body("Client error: {}" + e.getMessage());
        }
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<String> handleServerError(HttpServerErrorException e) {
        switch (e.getStatusCode()) {
            case INTERNAL_SERVER_ERROR:
                return ResponseEntity.status(500).body("Internal server error at the GitHub API");
            default:
                return ResponseEntity.status(e.getStatusCode()).body("Server error: " + e.getMessage());
        }
    }
}
