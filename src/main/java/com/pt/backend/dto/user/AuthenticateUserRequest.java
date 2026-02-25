package com.pt.backend.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticateUserRequest(
        @NotBlank @Email String email,
        @NotBlank String password
) {}