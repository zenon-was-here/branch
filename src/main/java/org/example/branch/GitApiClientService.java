package org.example.branch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitApiClientService {

    private final RestTemplate restTemplate;

    public GitApiClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static final String GIT_USER_ENDPOINT = "https://api.github.com/users/{username}";
    private static final String GIT_USER_REPOS_ENDPOINT = "https://api.github.com/users/{username}/repos";

    public GithubUser getUser(String username) {
        var user = restTemplate.getForObject(GIT_USER_ENDPOINT, GithubUser.class, username);
        var repos = getUserRepos(username);

        if (user != null) {
            return new GithubUser(
                    user.login(),
                    user.displayName(),
                    user.avatar(),
                    user.geoLocation(),
                    user.email(),
                    user.url(),
                    user.createdAt(),
                    repos
            );
        } else {
            return null;
        }
    }

    public List<GithubUserRepos> getUserRepos(String username) {
        var userRepos = restTemplate.getForObject(GIT_USER_REPOS_ENDPOINT, GithubUserRepos[].class, username);

        if (userRepos == null) {
            return new ArrayList<>();
        } else {
            return List.of(userRepos);
        }
    }
}