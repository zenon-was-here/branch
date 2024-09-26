package org.example.branch;

import org.example.branch.dto.GithubUserDto;
import org.example.branch.dto.GithubUserReposDto;
import org.example.branch.service.GitRestClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GithubUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GitRestClientService gitApiClientService;

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

        when(gitApiClientService.getUser(username)).thenReturn(mockUser);
    }

    @Test
    public void testGithubUser() throws Exception {
        String username = "fakeoctocatusername123";

        mockMvc.perform(get("/githubuser/{username}", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("octocat"))
                .andExpect(jsonPath("$.repos[0].name").value("repo0"))
                .andExpect(jsonPath("$.repos[1].name").value("repo1"));
    }

    @Test
    public void testGithubUserNotFound() throws Exception {
        String username = "unknown";

        when(gitApiClientService.getUser(username)).thenReturn(null);

        mockMvc.perform(get("/githubuser/{username}", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testHandleClientErrorBadRequest() throws Exception {
        String username = "badrequest";

        when(gitApiClientService.getUser(username)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad request"));

        mockMvc.perform(get("/githubuser/{username}", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid request to the GitHub API"));
    }

    @Test
    public void testHandleClientErrorNotFound() throws Exception {
        String username = "notfound";

        when(gitApiClientService.getUser(username)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "Not found"));

        mockMvc.perform(get("/githubuser/{username}", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Resource not found"));
    }

    @Test
    public void testHandleServerErrorInternalServerError() throws Exception {
        String username = "internalerror";

        when(gitApiClientService.getUser(username)).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));

        mockMvc.perform(get("/githubuser/{username}", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Internal server error at the GitHub API"));
    }

    @Test
    public void testHandleServerErrorOther() throws Exception {
        String username = "otherservererror";

        when(gitApiClientService.getUser(username)).thenThrow(new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE, "Service unavailable"));

        mockMvc.perform(get("/githubuser/{username}", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isServiceUnavailable())
                .andExpect(content().string("Server error: Service unavailable"));
    }
}