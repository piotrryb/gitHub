package com.sda.github.repository;

import com.sda.github.domain.AuthorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorDataRepository extends JpaRepository<AuthorData, Long> {
}
