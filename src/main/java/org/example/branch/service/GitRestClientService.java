package org.example.branch.service;

import org.example.branch.dto.GithubUserDto;
import org.example.branch.dto.GithubUserReposDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

// An implementation of GitClientServiceI to make REST client requests to the Github API endpoints for User and Repo info

@Service
public class GitRestClientService implements GitClientServiceI {

    private final RestTemplate restTemplate;

    public GitRestClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static final String GIT_USER_ENDPOINT = "https://api.github.com/users/{username}";
    private static final String GIT_USER_REPOS_ENDPOINT = "https://api.github.com/users/{username}/repos";

    @Cacheable("user")
    public GithubUserDto getUser(String username) {
        var user = restTemplate.getForObject(GIT_USER_ENDPOINT, GithubUserDto.class, username);
        var repos = getUserRepos(username);

        if (user != null) {
            return new GithubUserDto(
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

    public List<GithubUserReposDto> getUserRepos(String username) {
        var userRepos = restTemplate.getForObject(GIT_USER_REPOS_ENDPOINT, GithubUserReposDto[].class, username);

        if (userRepos == null) {
            return new ArrayList<>();
        } else {
            return List.of(userRepos);
        }
    }
}