package com.sda.github.repository;

import com.sda.github.domain.SingleCommit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleCommitRepository extends JpaRepository<SingleCommit, Long> {
}
