package com.pt.backend.controller;


import com.pt.backend.domain.User;
import com.pt.backend.dto.dealList.CreateDealListRequest;
import com.pt.backend.dto.dealList.DealListView;
import com.pt.backend.dto.dealList.UpdateDealListRequest;
import com.pt.backend.service.DealListService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deal-lists")
public class DealListController {

    private final DealListService dealListService;

    public DealListController(DealListService dealListService) {
        this.dealListService = dealListService;
    }

    @PostMapping
    public ResponseEntity<DealListView> create(
            @Valid @RequestBody CreateDealListRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        DealListView response = dealListService.createDealList(request, currentUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealListView> getById(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.ok(dealListService.getById(id, currentUser));
    }

    @GetMapping
    public ResponseEntity<List<DealListView>> getAll(
            @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.ok(
                dealListService.getAll(currentUser)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<DealListView> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDealListRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.ok(
                dealListService.update(id, request, currentUser)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDealList(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser
    ) {
        dealListService.delete(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}
