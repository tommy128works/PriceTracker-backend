package com.pt.backend.dto.dealListItem;

import com.pt.backend.domain.Deal.Unit;

public record DealListItemView(
        Long dealListItemId,
        Long dealListId,
        Long dealId,
        String name,
        Long amountCents,
        String currency,
        Long amount,
        Unit unit,
        String note
) {}
