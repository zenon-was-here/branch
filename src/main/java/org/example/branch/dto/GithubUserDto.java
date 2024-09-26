package org.example.branch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubUserDto {

        private String login;

        private String displayName;

        private String avatar;

        private String geoLocation;

        @JsonProperty("email")
        private String email;

        private String url;

        @JsonProperty("created_at")
        private String createdAt;

        @JsonProperty("repos")
        private List<GithubUserReposDto> githubUserRepos;

        @JsonProperty(value = "user_name", access = JsonProperty.Access.READ_ONLY)
        public String getLogin() {
                return login;
        };

        @JsonProperty(value = "login", access = JsonProperty.Access.WRITE_ONLY)
        public void setLogin(String login) {
                this.login = login;
        }

        @JsonProperty(value = "display_name", access = JsonProperty.Access.READ_ONLY)
        public String getDisplayName() {
                return displayName;
        };

        @JsonProperty(value = "name", access = JsonProperty.Access.WRITE_ONLY)
        public void setDisplayName(String displayName) {
                this.displayName = displayName;
        }

        @JsonProperty(value = "avatar", access = JsonProperty.Access.READ_ONLY)
        public String getAvatar() {
                return avatar;
        };

        @JsonProperty(value = "avatar_url", access = JsonProperty.Access.WRITE_ONLY)
        public void setAvatar(String avatar) {
                this.avatar = avatar;
        }

        @JsonProperty(value = "geo_location", access = JsonProperty.Access.READ_ONLY)
        public String getGeoLocation() {
                return geoLocation;
        };

        @JsonProperty(value = "location", access = JsonProperty.Access.WRITE_ONLY)
        public void setGeoLocation(String geoLocation) {
                this.geoLocation = geoLocation;
        }

        @JsonProperty(value = "url", access = JsonProperty.Access.READ_ONLY)
        public String getUrl() {
                return url;
        };

        @JsonProperty(value = "html_url", access = JsonProperty.Access.WRITE_ONLY)
        public void setUrl(String url) {
                this.url = url;
        }
}