package com.pt.backend.dto.dealList;

import jakarta.validation.constraints.NotBlank;

public record UpdateDealListRequest(
        @NotBlank String name
) {}
