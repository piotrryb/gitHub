package com.sda.github.repository;

import com.sda.github.domain.GitHubData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GitHubDataRepository extends JpaRepository<GitHubData, Long> {
    GitHubData getByFullName(String fullName);

    boolean existsByFullName(String fullName);
}
