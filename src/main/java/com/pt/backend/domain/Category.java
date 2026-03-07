package com.pt.backend.domain;

import com.pt.backend.domain.Deal.Deal;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.HashSet;

@Entity
@Table(
        name = "categories",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "user_id"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(mappedBy = "categories")
    private Set<Deal> deals = new HashSet<>();


    @Builder
    public Category(
            @NonNull String name,
            @NonNull User user
    ) {
        this.name = name;
        this.user = user;
    }

}
