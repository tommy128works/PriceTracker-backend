package com.pt.backend.controller;

import com.pt.backend.domain.User;
import com.pt.backend.dto.dealListItem.CreateDealListItemRequest;
import com.pt.backend.dto.dealListItem.DealListItemView;
import com.pt.backend.service.DealListItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deal-lists/{listId}/items")
public class DealListItemController {

    private final DealListItemService dealListItemService;

    public DealListItemController(DealListItemService dealListItemService) {
        this.dealListItemService = dealListItemService;
    }

    // REMEMBER THERE IS NO DealController
    //    PUT /deal-lists/{listId}/items/{dealId} → update note
    //    DELETE /deal-lists/{listId}/items/{dealId} → remove deal from list



    //    POST /deal-lists/{listId}/items
    //    Creates a deal and adds it to the list
    @PostMapping
    public ResponseEntity<DealListItemView> create(
            @PathVariable Long listId,
            @Valid @RequestBody CreateDealListItemRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        DealListItemView response = dealListItemService.create(listId, request, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    /* UNFILTERED BELOW

    // READ: Get a single item
    @GetMapping("/{dealId}")
    public ResponseEntity<DealListItemDto> getItem(
            @PathVariable Long listId,
            @PathVariable Long dealId,
            @AuthenticationPrincipal CustomUserDetails user) {

        DealListItemDto item = dealListItemService.getItem(listId, dealId, user.getId());
        return ResponseEntity.ok(item);
    }

    // UPDATE: Update note for a deal in the list
    @PutMapping("/{dealId}")
    public ResponseEntity<DealListItemDto> updateItem(
            @PathVariable Long listId,
            @PathVariable Long dealId,
            @RequestBody DealListItemDto dto,
            @AuthenticationPrincipal CustomUserDetails user) {

        DealListItemDto updated = dealListItemService.updateNote(listId, dealId, dto, user.getId());
        return ResponseEntity.ok(updated);
    }

    // DELETE: Remove a deal from the list
    @DeleteMapping("/{dealId}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long listId,
            @PathVariable Long dealId,
            @AuthenticationPrincipal CustomUserDetails user) {

        dealListItemService.removeDealFromList(listId, dealId, user.getId());
        return ResponseEntity.noContent().build();
    }

    // OPTIONAL: List all items in the list
    @GetMapping
    public ResponseEntity<List<DealListItemDto>> getAllItems(
            @PathVariable Long listId,
            @AuthenticationPrincipal CustomUserDetails user) {

        List<DealListItemDto> items = dealListItemService.getAllItems(listId, user.getId());
        return ResponseEntity.ok(items);
    }

    */

}
