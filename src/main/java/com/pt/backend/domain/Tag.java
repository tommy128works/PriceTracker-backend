package com.pt.backend.domain;

import com.pt.backend.domain.Deal.Deal;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @ManyToMany(mappedBy = "tags")
    private Set<Deal> deals = new HashSet<>();

    @Builder
    public Tag(
            @NonNull String name,
            @NonNull User owner
    ) {
        this.name = name;
        this.owner = owner;
    }

}
