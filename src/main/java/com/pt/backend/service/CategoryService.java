package com.pt.backend.service;


import com.pt.backend.domain.Category;
import com.pt.backend.domain.User;
import com.pt.backend.dto.category.CategoryRequest;
import com.pt.backend.dto.category.CategoryView;
import com.pt.backend.dto.category.CreateCategoryRequest;
import com.pt.backend.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryView create(CreateCategoryRequest request, User currentUser) {
        Category category = Category.builder()
                .name(request.name())
                .user(currentUser)
                .build();

        Category saved = categoryRepository.save(category);
        return toView(saved);
    }

    public CategoryView getById(Long id, User currentUser) {
        Category category = categoryRepository
                .findByIdAndUserId(id, currentUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        return toView(category);
    }

    public List<CategoryView> getAll(User currentUser) {
        return categoryRepository
                .findAllByUserId(currentUser.getId())
                .stream()
                .map(this::toView)
                .toList();
    }

    public CategoryView update(Long id, CategoryRequest request, User currentUser) {
        Category category = categoryRepository
                .findByIdAndUserId(id, currentUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        category.setName(request.name());
        return toView(category);
    }

    public void delete(Long id, User currentUser) {
        Category category = categoryRepository
                .findByIdAndUserId(id, currentUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        categoryRepository.delete(category);
    }

    private CategoryView toView(Category category) {
        return new CategoryView(category.getId(), category.getName());
    }
}