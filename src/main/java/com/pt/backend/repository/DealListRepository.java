package com.pt.backend.repository;

import com.pt.backend.domain.DealList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DealListRepository
        extends JpaRepository<DealList, Long> {
    boolean existsByNameAndUserId(String name, Long userId);
    Optional<DealList> findByIdAndUserId(Long id, Long userId);
    List<DealList> findAllByUserId(Long userId);
}
