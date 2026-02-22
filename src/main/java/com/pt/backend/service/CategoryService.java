package com.pt.backend.service;


import com.pt.backend.domain.Category;
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

    public CategoryView create(CreateCategoryRequest request) {
        Category category = Category.builder()
                .name(request.name())
//                .owner(request.owner())   // temporarily excluding owner
                .build();

        Category saved = categoryRepository.save(category);
        return toView(saved);
    }

    // For now, category is retrieved using id
    // In the future, a category should only be accessed by the correct owner
    public CategoryView getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        return toView(category);
    }

    // For now, all categories are retrieved
    // In the future, it should get all categories belonging to a specific User
    public List<CategoryView> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toView)
                .toList();
    }

    // For now, I will leave this
    // In the future, a category should only be updated by the correct owner
    public CategoryView update(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        category.setName(request.name());
        return toView(category);
    }

    // For now, I will leave this
    // In the future, a category should only be updated by the correct owner
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category not found");
        }
        categoryRepository.deleteById(id);
    }

    private CategoryView toView(Category category) {
        return new CategoryView(category.getId(), category.getName());
    }
}