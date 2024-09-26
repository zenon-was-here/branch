package org.example.branch.service;

import org.example.branch.dto.GithubUserDto;
import org.example.branch.dto.GithubUserReposDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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
        try {
            var user = restTemplate.getForObject(GIT_USER_ENDPOINT, GithubUserDto.class, username);
            var repos = getUserRepos(username);

            System.out.println(user);
            System.out.println(user.getLogin());
            if (user != null) {
                return new GithubUserDto(
                        user.getLogin(),
                        user.getDisplayName(),
                        user.getAvatar(),
                        user.getGeoLocation(),
                        user.getEmail(),
                        user.getUrl(),
                        user.getCreatedAt(),
                        repos
                );
            } else {
                return null;
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            } else {
                throw e;
            }
        }
    }

    public List<GithubUserReposDto> getUserRepos(String username) {
        try {
            var userRepos = restTemplate.getForObject(GIT_USER_REPOS_ENDPOINT, GithubUserReposDto[].class, username);

            if (userRepos == null) {
                return new ArrayList<>();
            } else {
                return List.of(userRepos);
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new ArrayList<>();
            } else {
                throw e;
            }
        }
    }
}