package com.sda.github.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class GitHubData {
    private OwnerData owner;
    @JsonProperty("full_name")
    private String fullName;
    private String description;
    private String url;
    @JsonProperty("commits_url")
    private String commitsUrl;
    @JsonProperty("watchers_count")
    private Integer watchersCount;
    @JsonIgnore
    private String error;
}
