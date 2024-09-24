package org.example.branch;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitUserController {

    @GetMapping("/gituser/{username}")
    public String gitUser(@PathVariable String username) {
        return username;
    }
}
