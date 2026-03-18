package com.pt.backend.dto.dealListItem;


import com.pt.backend.domain.Deal.Unit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateDealListItemRequest(
        @NotBlank String name,
        @NotNull Long amountCents,
        @Size(min = 3, max = 3)
        @Pattern(regexp = "[A-Z]{3}")
        @NotBlank String currency,
        @NotNull Long amount,
        @NotNull Unit unit,
        String note
) {}
