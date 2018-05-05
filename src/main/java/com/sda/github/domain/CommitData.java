package com.sda.github.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CommitData {
    private String url;
    private SingleCommit commit;
}
