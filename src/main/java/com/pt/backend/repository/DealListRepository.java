package com.pt.backend.repository;

import com.pt.backend.domain.DealList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealListRepository
        extends JpaRepository<DealList, Long> {
}
