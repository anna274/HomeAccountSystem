package com.rusakovich.bsuir.server.controller.impl;

import com.rusakovich.bsuir.server.controller.Controller;
import com.rusakovich.bsuir.server.controller.ControllerHelper;
import com.rusakovich.bsuir.server.entity.Category;
import com.rusakovich.bsuir.server.model.service.impl.ExpenseCategoryServiceImpl;
import com.rusakovich.bsuir.server.model.service.impl.IncomeCategoryServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpenseCategoryController implements Controller {
    private final ExpenseCategoryServiceImpl categoryService;

    public ExpenseCategoryController(ExpenseCategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }
    @Override
    public String request(Map<String, String> params) {
        String command = params.get("command");
        switch (command) {
            case "add": {
                return add(params);
            }
            case "getById": {
                return getById(params);
            }
            case "getAll": {
                return getAll();
            }
            case "update": {
                return update(params);
            }
            case "delete": {
                return delete(params);
            }
            default:
                return "";
        }
    }

    public String add(Map<String, String> params) {
        Category category = Category.fromMap(params);
        categoryService.addCategory(category);
        return ControllerHelper.getResponse("ok", category.toString(), "");
    }

    public String getById(Map<String, String> params) {
        Category category = categoryService.getCategoryById(Long.valueOf(params.get("id")));
        return ControllerHelper.getResponse("ok", category.toString(), "");
    }

    public String getAll() {
        List<Category> categories = categoryService.getAllCategories();
        List<String> categoriesStr = new ArrayList<String>();
        for (Category category : categories) {
            categoriesStr.add(category.toString());
        }
        String data = String.join(";", categoriesStr);
        return ControllerHelper.getResponse("ok", data, "");
    }

    public String update(Map<String, String> params) {
        categoryService.updateCategory(Category.fromMap(params));
        return ControllerHelper.getResponse("ok", "", "");
    }

    public String delete(Map<String, String> params) {
        categoryService.deleteCategory(Long.valueOf(params.get("id")));
        return ControllerHelper.getResponse("ok", "", "");
    }
}
