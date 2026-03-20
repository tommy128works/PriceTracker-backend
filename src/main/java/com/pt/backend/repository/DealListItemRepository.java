package com.pt.backend.repository;

import com.pt.backend.domain.DealListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DealListItemRepository
        extends JpaRepository<DealListItem, Long> {
    Optional<DealListItem> findByDealListIdAndDealId(
            Long dealListId,
            Long dealId
    );
    List<DealListItem> findAllByDealListIdAndDealListUserId(
            Long dealListId,
            Long userId
    );

}
