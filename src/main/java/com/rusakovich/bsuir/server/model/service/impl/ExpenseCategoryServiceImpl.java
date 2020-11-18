package com.rusakovich.bsuir.server.model.service.impl;

import com.rusakovich.bsuir.server.entity.Category;
import com.rusakovich.bsuir.server.model.dao.impl.ExpenseCategoryDaoImpl;
import com.rusakovich.bsuir.server.model.dao.impl.IncomeCategoryDaoImpl;
import com.rusakovich.bsuir.server.model.service.CategoryService;

import java.sql.SQLException;
import java.util.List;

public class ExpenseCategoryServiceImpl implements CategoryService {
    private final ExpenseCategoryDaoImpl categoryDao;

    public ExpenseCategoryServiceImpl(ExpenseCategoryDaoImpl categoryDao) {
        this.categoryDao = categoryDao;
    }
    @Override
    public void addCategory(Category category) {
        try {
            categoryDao.save(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryDao.findById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }

    @Override
    public void updateCategory(Category category) {
        try {
            categoryDao.update(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCategory(Long id) {
        try {
            categoryDao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
