package com.pt.backend.dto.dealList;

import jakarta.validation.constraints.NotBlank;

public record CreateDealListRequest(
        @NotBlank String name
) {}
