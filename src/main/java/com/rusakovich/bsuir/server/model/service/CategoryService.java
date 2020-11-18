package com.rusakovich.bsuir.server.model.service;

import com.rusakovich.bsuir.server.entity.Category;

import java.util.List;

public interface CategoryService {
    void addCategory(Category category);

    Category getCategoryById(Long id);

    List<Category> getAllCategories();

    void updateCategory(Category category);

    void deleteCategory(Long id);
}
