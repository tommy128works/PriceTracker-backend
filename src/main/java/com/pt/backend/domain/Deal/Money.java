package com.pt.backend.domain.Deal;

import jakarta.persistence.*;
import lombok.*;
import java.util.Currency;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money {

    @Column(nullable = false)
    private Long amountCents;

    @Column(length = 3, nullable = false)
    private String currency;

    public Money(Long amountCents, Currency currency) {
        this.amountCents = amountCents;
        this.currency = currency.getCurrencyCode();
    }

    public Currency getCurrency() {
        return Currency.getInstance(this.currency);
    }
}
