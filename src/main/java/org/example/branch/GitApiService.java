package org.example.branch;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GitApiService {
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String GIT_USER_ENDPOINT = "https://api.github.com/users/{username}";
    private static final String GIT_USER_REPOS_ENDPOINT = "https://api.github.com/users/{username}/repos";

    public GithubUser getUser(String username) {

        var userResponse = restTemplate.getForObject(GIT_USER_ENDPOINT, GithubUser.class, username);


//        System.out.println(userResponse.login() + " " + userResponse.name()  + " " +  userResponse.email());

        return new GithubUser(
                userResponse.login(),
                userResponse.displayName(),
                userResponse.avatar(),
                userResponse.geoLocation(),
                userResponse.email(),
                userResponse.url(),
                userResponse.createdAt()
        );
    }

    public Object getUserRepos(String username) {
        var userResponse = restTemplate.getForObject(GIT_USER_REPOS_ENDPOINT, Object.class, username);

        return userResponse;
    }

}
