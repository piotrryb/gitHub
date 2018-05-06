package com.sda.github.repository;

import com.sda.github.domain.CommitData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommitDataRepository extends JpaRepository<CommitData, Long> {
}
