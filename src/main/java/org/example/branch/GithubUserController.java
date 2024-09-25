package org.example.branch;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GithubUserController {

    final GitApiService gitApiService;

    public GithubUserController(GitApiService gitApiService) {
        this.gitApiService = gitApiService;
    }

    @GetMapping("/githubuser/{username}")
    public GithubUser githubUser(@PathVariable String username) {
        gitApiService.getUserRepos(username);

        return gitApiService.getUser(username);
    }
}
