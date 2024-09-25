package org.example.branch;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubUserRepos(
        @JsonProperty("name") String name,
        @JsonProperty("url") String htmlUrl
) {}