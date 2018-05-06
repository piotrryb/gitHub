package com.sda.github.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CommitData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String url;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private SingleCommit commit;
}
