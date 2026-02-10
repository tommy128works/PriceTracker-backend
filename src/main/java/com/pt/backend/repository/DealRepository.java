package com.pt.backend.repository;

import com.pt.backend.domain.Deal.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealRepository
        extends JpaRepository<Deal, Long> {
}
