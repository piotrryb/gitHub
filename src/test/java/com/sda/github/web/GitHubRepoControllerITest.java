package com.sda.github.web;

import com.sda.github.GitHubApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GitHubApplication.class)
@AutoConfigureMockMvc
public class GitHubRepoControllerITest {

    private static final String USERNAME = "lukasz-bacic";
    private static final String REPOSITORY = "java5pozHibernate";
    private static final String URL = "/getRepository/{owner}/{repo}";
    private static final String COMMITS = "/commits";
    private static final String ERROR_USERNAME = "lukasz-baci";
    private static final String NOT_FOUND = "404 Not Found";

    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldReturnValidResponse() throws Exception {
        mockMvc.perform(get(URL, USERNAME, REPOSITORY)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.owner.login").value(USERNAME));
    }

    @Test
    public void shouldGetErrorWhenForbiddenStatus() throws Exception {
        mockMvc.perform(get(URL, ERROR_USERNAME, REPOSITORY)).andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").value(NOT_FOUND));
    }

    @Test
    public void shouldReturnListOfCommits() throws Exception {
        mockMvc.perform(get(URL + COMMITS, USERNAME, REPOSITORY)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].commit.author.name").value("lbacic"));
    }

    @Test
    public void shouldReturn403ErrorWhenAskingForCommits() throws Exception {
        mockMvc.perform(get(URL + COMMITS, ERROR_USERNAME, REPOSITORY)).andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").value(NOT_FOUND));
    }
}
