package org.example.branch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

// NOTE: I would typically not mock RestTemplate like this, as it effectively is forcing us to test the implementation of an API we don't control

@SpringBootTest
public class GitApiClientServiceTest {

    @Autowired
    private GitApiClientService gitApiClientService;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        String username = "fakeoctocatusername123";
        GithubUserRepos[] mockRepos = new GithubUserRepos[] {
                new GithubUserRepos("repo0", "url0"),
                new GithubUserRepos("repo1", "url1")
        };
        GithubUser mockUser = new GithubUser("fakeoctocatusername123",
                "The Octocat",
                "avatar_url",
                "San Francisco",
                "email",
                "html_url",
                "created_at",
                List.of(mockRepos));

        when(restTemplate.getForObject(eq("https://api.github.com/users/{username}"), eq(GithubUser.class), eq(username)))
                .thenReturn(mockUser);
        when(restTemplate.getForObject(eq("https://api.github.com/users/{username}/repos"), eq(GithubUserRepos[].class), eq(username)))
                .thenReturn(mockRepos);
    }

    @Test
    public void testGetUser() {
        String username = "fakeoctocatusername123";

        GithubUser user = gitApiClientService.getUser(username);

        assertNotNull(user);
        assertEquals(username, user.login());
        assertEquals(2, user.GithubUserRepos().size());
    }

    @Test
    public void testGetUserRepos() {
        String username = "fakeoctocatusername123";

        List<GithubUserRepos> repos = gitApiClientService.getUserRepos(username);

        assertNotNull(repos);
        assertEquals(2, repos.size());
    }
}