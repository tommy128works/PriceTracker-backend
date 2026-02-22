package com.pt.backend.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
        @NotBlank String name
//        @NotBlank UserDTO owner    // temporarily excluding owner
) {}
