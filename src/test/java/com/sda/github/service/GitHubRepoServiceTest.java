package com.sda.github.service;

import com.sda.github.domain.CommitData;
import com.sda.github.domain.GitHubData;
import com.sda.github.domain.OwnerData;
import com.sda.github.errorHandling.SDAException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GitHubRepoServiceTest {

    private static final String URL = "https://api.github.com/repos/{owner}/{repo}";
    private static final String URL_COMMITS = "https://api.github.com/repos/{owner}/{repo}/commits";
    private static final String TEST_USER = "test_user";
    private static final String TEST_REPO = "test_repo";
    private static final String ERROR_MESSAGE = "test_error";
    private static final String URL_1 = "url_1";
    private static final String URL_2 = "url_2";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GitHubRepoService gitHubRepoService;

    @Test
    public void shouldReturnValidResponseForQuery() {
        // given
        OwnerData ownerData = new OwnerData();
        ownerData.setLogin("test_login");
        ownerData.setSiteAdmin(false);

        GitHubData gitHubData = new GitHubData();
        gitHubData.setFullName("test_name");
        gitHubData.setOwner(ownerData);
        gitHubData.setDescription("test_description");

        when(restTemplate.getForObject(any(String.class), eq(GitHubData.class),
                eq(TEST_USER), eq(TEST_REPO))).thenReturn(gitHubData);
        // when
        GitHubData underTest = gitHubRepoService.getRepoByUserAndRepoName(TEST_USER, TEST_REPO);
        // then
        assertThat(underTest.getFullName()).isEqualTo(gitHubData.getFullName());
    }

    @Test
    public void shouldGetErrorWhen4xxFromGitHub() {
        // given
        when(restTemplate.getForObject(URL, GitHubData.class, TEST_USER, TEST_REPO))
                .thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN, ERROR_MESSAGE));
        // when
        GitHubData underTest = gitHubRepoService.getRepoByUserAndRepoName(TEST_USER, TEST_REPO);
        // then
        assertThat(underTest.getError()).isEqualTo(HttpStatus.FORBIDDEN.value() + " " + ERROR_MESSAGE);
    }

    @Test
    public void shouldReturnListOfCommitsWithSpecificAmount() {
        // given
        CommitData[] commitData = new CommitData[3];
        commitData[0] = new CommitData();
        commitData[1] = new CommitData();
        commitData[1].setUrl(URL_1);
        commitData[2] = new CommitData();
        commitData[2].setUrl(URL_2);

        when(restTemplate.getForObject(URL_COMMITS, CommitData[].class,
                TEST_USER, TEST_REPO)).thenReturn(commitData);
        // when
        List<CommitData> underTest = gitHubRepoService.getCommitsByUserAndRepoName(TEST_USER, TEST_REPO);
        // then
        assertThat(underTest.size()).isEqualTo(commitData.length);
        assertThat(underTest.stream()
                .map(CommitData::getUrl)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()))
                .containsExactlyInAnyOrder(URL_1, URL_2);
    }

    @Test(expected = SDAException.class)
    public void shouldThrowSDAException() {
        // given
        when(restTemplate.getForObject(URL_COMMITS, CommitData[].class, TEST_USER, TEST_REPO))
                .thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN, ERROR_MESSAGE));
        // when
        gitHubRepoService.getCommitsByUserAndRepoName(TEST_USER, TEST_REPO);
    }
}
