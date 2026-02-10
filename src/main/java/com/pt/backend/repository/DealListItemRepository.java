package com.pt.backend.repository;

import com.pt.backend.domain.DealListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealListItemRepository
        extends JpaRepository<DealListItem, Long> {
}
