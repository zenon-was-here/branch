package org.example.branch;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GitApiService {
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String GIT_USER_ENDPOINT = "https://api.github.com/users/{username}";

    public Object getUserData(String username) {
        var userResponse = restTemplate.getForObject(GIT_USER_ENDPOINT, Object.class, username);

        System.out.println(userResponse);
        return userResponse;
    }

}
