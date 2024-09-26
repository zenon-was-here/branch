package org.example.branch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubUserReposDto(
        @JsonProperty("name") String name,
        @JsonProperty("url") String htmlUrl
) {}