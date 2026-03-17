package com.pt.backend.service;


import com.pt.backend.domain.Deal.Deal;
import com.pt.backend.domain.DealList;
import com.pt.backend.domain.DealListItem;
import com.pt.backend.domain.User;
import com.pt.backend.dto.deal.CreateDealRequest;
import com.pt.backend.dto.dealListItem.CreateDealListItemRequest;
import com.pt.backend.dto.dealListItem.DealListItemView;
import com.pt.backend.repository.DealListItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DealListItemService {

    private final DealListItemRepository dealListItemRepository;
    private final DealService dealService;
    private final DealListService dealListService;

    public DealListItemService(DealListItemRepository dealListItemRepository, DealService dealService, DealListService dealListService) {
        this.dealListItemRepository = dealListItemRepository;
        this.dealService = dealService;
        this.dealListService = dealListService;
    }

    public DealListItemView create(
            Long listId,
            CreateDealListItemRequest request,
            User currentUser
    ) {
        CreateDealRequest createDealRequest = new CreateDealRequest(
                request.name(),
                request.amountCents(),
                request.currency(),
                request.amount(),
                request.unit()
        );
        Deal deal = dealService.createEntity(createDealRequest, currentUser);

        DealList dealList = dealListService.getEntityById(listId, currentUser);
        DealListItem dealListItem = DealListItem.builder()
                .dealList(dealList)
                .deal(deal)
                .note(request.note())
                .build();

        DealListItem saved = dealListItemRepository.save(dealListItem);

        return toView(saved);
    }

    public DealListItemView toView(DealListItem dealListItem) {
        return new DealListItemView(
                dealListItem.getId(),
                dealListItem.getDealList().getId(),
                dealListItem.getDeal().getId(),
                dealListItem.getDeal().getName(),
                dealListItem.getDeal().getPrice().getAmountCents(),
                dealListItem.getDeal().getPrice().getCurrency(),
                dealListItem.getDeal().getQuantity().getAmount(),
                dealListItem.getDeal().getQuantity().getUnit(),
                dealListItem.getNote()
        );
    }


}