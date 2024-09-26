package org.example.branch.service;

import org.example.branch.dto.GithubUserDto;
import org.example.branch.dto.GithubUserReposDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

// NOTE: I would typically not mock RestTemplate like this, as it effectively is forcing us to test the implementation of an API we don't control
@SpringBootTest
public class GitApiClientServiceTest {

    private static final String GIT_USER_ENDPOINT = "https://api.github.com/users/{username}";
    private static final String GIT_USER_REPOS_ENDPOINT = "https://api.github.com/users/{username}/repos";

    @Autowired
    private GitRestClientService gitApiClientService;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        String username = "fakeoctocatusername123";
        GithubUserReposDto[] mockRepos = new GithubUserReposDto[] {
                new GithubUserReposDto("repo0", "url0"),
                new GithubUserReposDto("repo1", "url1")
        };
        GithubUserDto mockUser = new GithubUserDto("fakeoctocatusername123",
                "The Octocat",
                "avatar_url",
                "San Francisco",
                "email",
                "html_url",
                "created_at",
                List.of(mockRepos));

        when(restTemplate.getForObject(eq(GIT_USER_ENDPOINT), eq(GithubUserDto.class), eq(username)))
                .thenReturn(mockUser);
        when(restTemplate.getForObject(eq(GIT_USER_REPOS_ENDPOINT), eq(GithubUserReposDto[].class), eq(username)))
                .thenReturn(mockRepos);
    }

    @Test
    public void testGetUser() {
        String username = "fakeoctocatusername123";
        int expectedSize = 2;

        GithubUserDto user = gitApiClientService.getUser(username);

        assertNotNull(user);
        assertEquals(username, user.getLogin());
        assertEquals(expectedSize, user.getGithubUserRepos().size());
    }

    @Test
    public void testGetUserRepos() {
        String username = "fakeoctocatusername123";
        int expectedSize = 2;

        List<GithubUserReposDto> repos = gitApiClientService.getUserRepos(username);

        assertNotNull(repos);
        assertEquals(expectedSize, repos.size());
    }

    @Test
    public void testGetUserNotFound() {
        String username = "unknown";

        when(restTemplate.getForObject(eq(GIT_USER_ENDPOINT), eq(GithubUserDto.class), eq(username)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "Not Found"));

        GithubUserDto user = gitApiClientService.getUser(username);

        assertNull(user);
    }

    @Test
    public void testGetUserReposNotFound() {
        String username = "unknown";

        when(restTemplate.getForObject(eq(GIT_USER_REPOS_ENDPOINT), eq(GithubUserReposDto[].class), eq(username)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "Not Found"));

        List<GithubUserReposDto> repos = gitApiClientService.getUserRepos(username);

        assertNotNull(repos);
        assertTrue(repos.isEmpty());
    }

    @Test
    public void testGetUserInternalServerError() {
        String username = "fakeoctocatusername123";

        when(restTemplate.getForObject(eq(GIT_USER_ENDPOINT), eq(GithubUserDto.class), eq(username)))
                .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"));

        assertThrows(RuntimeException.class, () -> gitApiClientService.getUser(username));
    }
}