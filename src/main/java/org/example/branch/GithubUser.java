package org.example.branch;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubUser(
        @JsonProperty("login") String login,
        @JsonProperty("name") String displayName,
        @JsonProperty("avatar_url") String avatar,
        @JsonProperty("location") String geoLocation,
        @JsonProperty("email") String email,
        @JsonProperty("html_url") String url,
        @JsonProperty("created_at") String createdAt
) {}