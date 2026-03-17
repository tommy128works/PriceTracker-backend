package com.pt.backend.service;


import com.pt.backend.domain.Deal.Deal;
import com.pt.backend.domain.Deal.Money;
import com.pt.backend.domain.Deal.Quantity;
import com.pt.backend.domain.User;
import com.pt.backend.dto.deal.CreateDealRequest;
import com.pt.backend.dto.deal.DealView;
import com.pt.backend.repository.DealRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DealService {

    private final DealRepository dealRepository;

    public DealService(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    public DealView create(
            CreateDealRequest request,
            User currentUser
    ) {

        Deal deal = Deal.builder()
                .name(request.name())
                .owner(currentUser)
                .price(new Money(request.amountCents(), request.currency()))
                .quantity(new Quantity(request.amount(), request.unit()))
                .build();

        Deal saved = dealRepository.save(deal);
        return toView(saved);
    }

    public Deal createEntity(
            CreateDealRequest request,
            User currentUser
    ) {

        Deal deal = Deal.builder()
                .name(request.name())
                .owner(currentUser)
                .price(new Money(request.amountCents(), request.currency()))
                .quantity(new Quantity(request.amount(), request.unit()))
                .build();

        Deal saved = dealRepository.save(deal);
        return saved;
    }

    private DealView toView(Deal deal) {
        return new DealView(
                deal.getId(),
                deal.getName(),
                deal.getPrice().getAmountCents(),
                deal.getPrice().getCurrency(),
                deal.getQuantity().getAmount(),
                deal.getQuantity().getUnit()
        );
    }

}
