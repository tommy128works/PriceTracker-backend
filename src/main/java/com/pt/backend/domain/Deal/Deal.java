package com.pt.backend.domain.Deal;

import com.pt.backend.domain.User;
import com.pt.backend.domain.Category;
import com.pt.backend.domain.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "deals")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @Embedded
    @Column(nullable = false)
    private Money price;

    @Embedded
    @Column(nullable = false)
    private Quantity quantity;

    @Size(min = 1)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "deal_category",
            joinColumns = @JoinColumn(name = "deal_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @Size(min = 1)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "deal_tag",
            joinColumns = @JoinColumn(name = "deal_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @Builder
    public Deal(
            @NonNull String name,
            @NonNull User owner,
            @NonNull Money price,
            @NonNull Quantity quantity,
            @NonNull Set<Category> categories,
            @NonNull Set<Tag> tags
    ) {
        if (categories.isEmpty()) {
            throw new IllegalArgumentException("Deal must have at least one category");
        }
        if (tags.isEmpty()) {
            throw new IllegalArgumentException("Deal must have at least one tag");
        }
        this.name = name;
        this.owner = owner;
        this.price = price;
        this.quantity = quantity;
        this.categories = new HashSet<>(categories);
        this.tags = new HashSet<>(tags);
    }


}
