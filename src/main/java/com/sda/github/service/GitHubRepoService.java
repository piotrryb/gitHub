package com.sda.github.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GitHubRepoService {
    public String getRepoByUserAndRepoName(String username, String repositoryName) {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject("https://api.github.com/repos/{owner}/{repo}",
                String.class, username, repositoryName);
        return response;
    }
}
