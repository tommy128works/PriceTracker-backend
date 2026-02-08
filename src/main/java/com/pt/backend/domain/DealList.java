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

    @NonNull
    @Column(nullable = false, unique = true)
    private String name;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @Builder
    public DealList(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

}
