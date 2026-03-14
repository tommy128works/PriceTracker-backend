package com.pt.backend.controller;

import com.pt.backend.domain.User;
import com.pt.backend.dto.category.UpdateCategoryRequest;
import com.pt.backend.dto.category.CategoryView;
import com.pt.backend.dto.category.CreateCategoryRequest;
import com.pt.backend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryView create(
            @Valid @RequestBody CreateCategoryRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        return categoryService.create(request, currentUser);
    }

    @GetMapping("/{id}")
    public CategoryView getById(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser
    ) {
        return categoryService.getById(id, currentUser);
    }

    @GetMapping
    public List<CategoryView> getAll(
            @AuthenticationPrincipal User currentUser
    ) {
        return categoryService.getAll(currentUser);
    }

    @PutMapping("/{id}")
    public CategoryView update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCategoryRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        return categoryService.update(id, request, currentUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser
    ) {
        categoryService.delete(id, currentUser);
    }
}