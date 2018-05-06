package com.sda.github.repository;

import com.sda.github.domain.OwnerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerDataRepository extends JpaRepository<OwnerData, Long> {
}
