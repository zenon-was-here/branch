package org.example.branch.service;

import org.example.branch.dto.GithubUserDto;
import org.example.branch.dto.GithubUserReposDto;

import java.util.List;

// This is admittedly overkill, but I'd be remiss to demonstrate knowledge of interfaces
public interface GitClientServiceI {

    GithubUserDto getUser(String username);
    List<GithubUserReposDto> getUserRepos(String username);
}
