package com.pt.backend.domain;

import com.pt.backend.domain.Deal.Deal;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "deal_list_items")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DealListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "deal_list_id", nullable = false)
    private DealList dealList;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "deal_id", nullable = false)
    private Deal deal;

    @Column(nullable = true)
    private String note;

    @Builder
    public DealListItem(@NonNull DealList dealList, @NonNull Deal deal, String note) {
        this.dealList = dealList;
        this.deal = deal;
        this.note = note;
    }

}
