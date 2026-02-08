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

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "deal_list_id", nullable = false)
    private DealList dealList;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "deal_id", nullable = false)
    private Deal deal;

    private String note;

    @Builder
    public DealListItem(DealList dealList, Deal deal, String note) {
        this.dealList = dealList;
        this.deal = deal;
        this.note = note;
    }

}
