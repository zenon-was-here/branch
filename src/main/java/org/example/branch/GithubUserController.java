package org.example.branch;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
