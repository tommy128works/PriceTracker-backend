package com.pt.backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "deal_lists",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "user_id"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DealList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public DealList(
            @NonNull String name,
            @NonNull User user
    ) {
        this.name = name;
        this.user = user;
    }

}
