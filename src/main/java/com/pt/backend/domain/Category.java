package com.pt.backend.domain;

import com.pt.backend.domain.Deal.Deal;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)      temporarily nullable
//    @JoinColumn(name = "user_id", nullable = false)           temporarily nullable
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_id", nullable = true)
    private User owner;

    @ManyToMany(mappedBy = "categories")
    private Set<Deal> deals = new HashSet<>();


    @Builder
    public Category(
            @NonNull String name,
//            @NonNull User owner
            User owner  // temporarily null
    ) {
        this.name = name;
        this.owner = owner;
    }

}
