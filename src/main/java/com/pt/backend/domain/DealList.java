package com.pt.backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "deal_lists")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DealList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @Builder
    public DealList(@NonNull String name, @NonNull User owner) {
        this.name = name;
        this.owner = owner;
    }

}
