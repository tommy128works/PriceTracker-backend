package com.pt.backend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticateUserRequest(
        @NotBlank @Email String email,
        @NotBlank String password
) {}