package com.pt.backend.dto.deal;

import com.pt.backend.domain.Deal.Unit;

public record DealView(
        Long id,
        String name,
        Long amountCents,
        String currency,
        Long amount,
        Unit unit
) {}
