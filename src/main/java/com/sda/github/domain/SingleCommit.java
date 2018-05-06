package com.sda.github.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class SingleCommit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private AuthorData author;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private AuthorData commiter;
    private String message;
}
