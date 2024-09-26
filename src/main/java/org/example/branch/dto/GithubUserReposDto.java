package org.example.branch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// Leverage a record because this is simple enough to warrant
public record GithubUserReposDto(
        @JsonProperty("name") String name,
        @JsonProperty("url") String htmlUrl
) {}