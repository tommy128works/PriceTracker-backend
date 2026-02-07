package com.pt.backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, unique = true)
    private String name;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User owner;

    @Builder
    public Category(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

}
