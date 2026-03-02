package com.pt.backend.repository;

import com.pt.backend.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository
        extends JpaRepository<Category, Long> {
    Optional<Category> findByIdAndUserId(Long id, Long userId);
    List<Category> findAllByUserId(Long userId);
}
