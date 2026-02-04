package com.pt.backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String firstName;

    @NonNull
    @Column(nullable = false)
    private String lastName;

    @NonNull
    @Column(nullable = false, unique = true)
    private String email;

    @Builder
    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}