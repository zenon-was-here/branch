package org.example.branch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GithubUserDto(
        @JsonProperty("login") String login,
        @JsonProperty("name") String displayName,
        @JsonProperty("avatar_url") String avatar,
        @JsonProperty("location") String geoLocation,
        @JsonProperty("email") String email,
        @JsonProperty("html_url") String url,
        @JsonProperty("created_at") String createdAt,
        @JsonProperty("repos") List<GithubUserReposDto> GithubUserRepos
) {}