package com.sda.github.service;

import com.sda.github.domain.CommitData;
import com.sda.github.domain.GitHubData;
import com.sda.github.errorHandling.SDAException;
import com.sda.github.repository.CommitDataRepository;
import com.sda.github.repository.GitHubDataRepository;
import com.sda.github.repository.OwnerDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class GitHubRepoService {

    private final static String URL = "https://api.github.com/repos/{owner}/{repo}";

    private RestTemplate restTemplate;
    private GitHubDataRepository gitHubDataRepository;
    private CommitDataRepository commitDataRepository;
    private OwnerDataRepository ownerDataRepository;

    @Autowired
    public GitHubRepoService(RestTemplate restTemplate,
                             GitHubDataRepository gitHubDataRepository,
                             CommitDataRepository commitDataRepository,
                             OwnerDataRepository ownerDataRepository) {
        this.restTemplate = restTemplate;
        this.gitHubDataRepository = gitHubDataRepository;
        this.commitDataRepository = commitDataRepository;
        this.ownerDataRepository = ownerDataRepository;
    }

    @Transactional
    public GitHubData getRepoByUserAndRepoName(String username, String repositoryName) {
        try {
            GitHubData response;
            String fullName = getFullName(username, repositoryName);
            if (gitHubDataRepository.existsByFullName(fullName)) {
                response = gitHubDataRepository.getByFullName(fullName);
            } else {
                response = restTemplate.getForObject(URL,
                        GitHubData.class, username, repositoryName);
                if (ownerDataRepository.existsByLogin(response.getOwner().getLogin())) {
                    response.setOwner(ownerDataRepository.getByLogin(response.getOwner().getLogin()));
                }
                gitHubDataRepository.save(response);
            }
            return response;
        } catch (HttpClientErrorException ex) {
            GitHubData errorResponse = new GitHubData();
            errorResponse.setError(ex.getMessage());
            return errorResponse;
        }
    }

    public List<CommitData> getCommitsByUserAndRepoName(String username, String repositoryName) {
        try {
            List<CommitData> commitDataList;
            String fullName = getFullName(username, repositoryName);
            if (commitDataRepository.existsByUrlContaining(fullName)) {
                commitDataList = commitDataRepository.getAllByUrlContaining(fullName);
            } else {
                CommitData[] response = restTemplate.getForObject(URL + "/commits",
                        CommitData[].class, username, repositoryName);
                commitDataList = Arrays.asList(response);
                commitDataList = commitDataList.size() > 3 ? commitDataList.subList(0, 3) : commitDataList;
                commitDataRepository.saveAll(commitDataList);
            }
            return commitDataList;
        } catch (HttpClientErrorException ex) {
            throw new SDAException(ex.getMessage());
        }
    }

    private static String getFullName(String username, String repositoryName) {
        return String.format("%s/%s", username, repositoryName);
    }
}
