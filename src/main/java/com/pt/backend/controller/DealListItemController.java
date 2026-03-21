package com.pt.backend.controller;

import com.pt.backend.domain.User;
import com.pt.backend.dto.dealListItem.CreateDealListItemRequest;
import com.pt.backend.dto.dealListItem.DealListItemView;
import com.pt.backend.dto.dealListItem.UpdateDealListItemRequest;
import com.pt.backend.service.DealListItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deal-lists/{listId}/items")
public class DealListItemController {

    private final DealListItemService dealListItemService;

    public DealListItemController(DealListItemService dealListItemService) {
        this.dealListItemService = dealListItemService;
    }

    @PostMapping
    public ResponseEntity<DealListItemView> create(
            @PathVariable Long listId,
            @Valid @RequestBody CreateDealListItemRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        DealListItemView response = dealListItemService.create(listId, request, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{dealId}")
    public ResponseEntity<DealListItemView> getById(
            @PathVariable Long listId,
            @PathVariable Long dealId,
            @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.ok(
                dealListItemService.getById(listId, dealId, currentUser)
        );
    }

    @GetMapping
    public ResponseEntity<List<DealListItemView>> getAll(
            @PathVariable Long listId,
            @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.ok(
                dealListItemService.getAll(listId, currentUser)
        );
    }

    @PutMapping("/{dealId}")
    public ResponseEntity<DealListItemView> update(
            @PathVariable Long listId,
            @PathVariable Long dealId,
            @Valid @RequestBody UpdateDealListItemRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.ok(
                dealListItemService.update(
                        listId,
                        dealId,
                        request,
                        currentUser
                )
        );
    }

    @DeleteMapping("/{dealId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long listId,
            @PathVariable Long dealId,
            @AuthenticationPrincipal User currentUser
    ) {
        dealListItemService.delete(listId, dealId, currentUser);
        return ResponseEntity.noContent().build();
    }

}
