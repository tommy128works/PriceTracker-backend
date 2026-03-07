package com.pt.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Builder
    public RefreshToken(
            @NonNull String token,
            @NonNull Instant expiryDate,
            @NonNull User user
    ) {
        this.token = token;
        this.expiryDate = expiryDate;
        this.user = user;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }

}