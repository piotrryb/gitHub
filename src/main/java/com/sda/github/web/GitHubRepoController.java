package com.sda.github.web;

import com.sda.github.service.GitHubRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GitHubRepoController {

    private GitHubRepoService gitHubRepoService;

    @Autowired
    public GitHubRepoController(GitHubRepoService gitHubRepoService) {
        this.gitHubRepoService = gitHubRepoService;
    }

    @GetMapping("/getRepository/{user}/{repositoryName}")
    public ResponseEntity<String> getRepositoryByUserAndRepo(@PathVariable("user") String user,
                                                            @PathVariable("repositoryName") String repositoryName) {
        String response = gitHubRepoService.getRepoByUserAndRepoName(user, repositoryName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
