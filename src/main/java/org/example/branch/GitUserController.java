package org.example.branch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitUserController {

    final GitApiService gitApiService;

    public GitUserController(GitApiService gitApiService) {
        this.gitApiService = gitApiService;
    }

    @GetMapping("/gituser/{username}")
    public String gitUser(@PathVariable String username) {
        gitApiService.getUserData(username);

        return username;
    }
}
