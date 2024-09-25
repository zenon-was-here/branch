package org.example.branch;

import java.util.List;

// This is admittedly overkill, but I'd be remiss to demonstrate knowledge of interfaces
public interface GitClientServiceI {

    GithubUser getUser(String username);
    List<GithubUserRepos> getUserRepos(String username);
}
