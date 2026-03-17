package com.pt.backend.dto.dealListItem;


import com.pt.backend.domain.Deal.Unit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateDealListItemRequest(
        @NotBlank String name,
        @NotBlank Long amountCents,
        @Size(min = 3, max = 3)
        @Pattern(regexp = "[A-Z]{3}")
        @NotBlank String currency,
        @NotBlank Long amount,
        @NotBlank Unit unit,
        String note
) {}
