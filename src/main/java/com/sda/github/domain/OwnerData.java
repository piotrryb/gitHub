package com.sda.github.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class OwnerData {
    private String login;
    @JsonProperty("site_admin")
    private Boolean siteAdmin;
}
