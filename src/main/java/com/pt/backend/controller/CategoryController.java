package com.pt.backend.controller;

import com.pt.backend.dto.category.CategoryRequest;
import com.pt.backend.dto.category.CategoryView;
import com.pt.backend.dto.category.CreateCategoryRequest;
import com.pt.backend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public CategoryView create(@Valid @RequestBody CreateCategoryRequest request) {
        return categoryService.create(request);
    }

    @GetMapping("/{id}")
    public CategoryView getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @GetMapping
    public List<CategoryView> getAll() {
        return categoryService.getAll();
    }

    @PutMapping("/{id}")
    public CategoryView update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request
    ) {
        return categoryService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}