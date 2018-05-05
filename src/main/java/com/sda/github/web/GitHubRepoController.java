package com.sda.github.web;

import com.sda.github.domain.CommitData;
import com.sda.github.domain.GitHubData;
import com.sda.github.service.GitHubRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class GitHubRepoController {

    private GitHubRepoService gitHubRepoService;

    @Autowired
    public GitHubRepoController(GitHubRepoService gitHubRepoService) {
        this.gitHubRepoService = gitHubRepoService;
    }

    @GetMapping("/getRepository/{user}/{repositoryName}")
    public ResponseEntity<Object> getRepositoryByUserAndRepo(@PathVariable("user") String user,
                                                                 @PathVariable("repositoryName") String repositoryName) {
        GitHubData response = gitHubRepoService.getRepoByUserAndRepoName(user, repositoryName);
        if (response.getError() != null) {
            return new ResponseEntity<>(response.getError(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getRepository/{user}/{repositoryName}/commits")
    public ResponseEntity<List<CommitData>> getCommitsForRepositoryByUserAndRepo(@PathVariable("user") String user,
                                                                                 @PathVariable("repositoryName") String repositoryName) {
        List<CommitData> response = gitHubRepoService.getCommitsByUserAndRepoName(user, repositoryName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
