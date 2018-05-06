package com.sda.github.service;

import com.sda.github.domain.CommitData;
import com.sda.github.domain.GitHubData;
import com.sda.github.errorHandling.SDAException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class GitHubRepoService {

    private final static String URL = "https://api.github.com/repos/{owner}/{repo}";

    private RestTemplate restTemplate;

    @Autowired
    public GitHubRepoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GitHubData getRepoByUserAndRepoName(String username, String repositoryName) {
        try {
            return restTemplate.getForObject(URL,
                    GitHubData.class, username, repositoryName);
        } catch (HttpClientErrorException ex) {
            GitHubData errorResponse = new GitHubData();
            errorResponse.setError(ex.getMessage());
            return errorResponse;
        }
    }

    public List<CommitData> getCommitsByUserAndRepoName(String username, String repositoryName) {
        try {
            CommitData[] response = restTemplate.getForObject(URL + "/commits",
                    CommitData[].class, username, repositoryName);
            List<CommitData> commitDataList = Arrays.asList(response);
            return commitDataList.size() > 3 ? commitDataList.subList(0, 3) : commitDataList;
        } catch (HttpClientErrorException ex) {
            throw new SDAException(ex.getMessage());
        }
    }
}
