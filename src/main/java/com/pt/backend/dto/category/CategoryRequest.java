package com.pt.backend.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank String name   // this might be id
) {}
