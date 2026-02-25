package com.pt.backend.dto.user;

public record UserView(
        Long id,
        String firstName,
        String lastName,
        String email
) {}
