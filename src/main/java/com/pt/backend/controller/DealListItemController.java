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

    // GET /deal-lists/{listId}/items/{dealId}
    // READ: Get a single item
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

    // GET /deal-lists/{listId}/items
    // READ: Get all items of a specific deal list
    @GetMapping
    public ResponseEntity<List<DealListItemView>> getAll(
            @PathVariable Long listId,
            @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.ok(
                dealListItemService.getAll(listId, currentUser)
        );
    }

    // PUT /deal-lists/{listId}/items/{dealId} → update note
    // UPDATE: Update note for a deal in the list
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

    /* UNFILTERED BELOW

    //    DELETE /deal-lists/{listId}/items/{dealId} → remove deal from list
    // DELETE: Remove a deal from the list
    @DeleteMapping("/{dealId}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long listId,
            @PathVariable Long dealId,
            @AuthenticationPrincipal CustomUserDetails user) {

        dealListItemService.removeDealFromList(listId, dealId, user.getId());
        return ResponseEntity.noContent().build();
    }

    */

}
