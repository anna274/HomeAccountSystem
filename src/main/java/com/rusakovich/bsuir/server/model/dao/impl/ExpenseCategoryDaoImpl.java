package com.rusakovich.bsuir.server.model.dao.impl;

import com.rusakovich.bsuir.server.entity.Category;
import com.rusakovich.bsuir.server.model.dao.CategoryDao;
import com.rusakovich.bsuir.server.model.dao.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpenseCategoryDaoImpl implements CategoryDao {
    private static final String SELECT_ALL_EXPENSE_CATEGORY = "SELECT * FROM expense_category";
    private static final String SELECT_EXPENSE_CATEGORY_BY_ID = "SELECT * FROM expense_category WHERE id = ?";
    private static final String INSERT_EXPENSE_CATEGORY = "INSERT INTO expense_category ( name ) VALUES (?)";
    private static final String UPDATE_EXPENSE_CATEGORY = "UPDATE expense_category SET name = ? WHERE id = ?";
    private static final String DELETE_EXPENSE_CATEGORY = "DELETE FROM expense_category WHERE id = ?";
    @Override
    public void save(Category entity) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_EXPENSE_CATEGORY)) {
            statement.setString(1, entity.getName());
            statement.executeUpdate();
        }
    }

    @Override
    public Category findById(Long id) {
        Category category = null;
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EXPENSE_CATEGORY_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                category = createCategoryObjFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EXPENSE_CATEGORY);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                categories.add(createCategoryObjFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public void update(Category entity) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_EXPENSE_CATEGORY)) {
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_EXPENSE_CATEGORY);
        statement.setLong(1, id);
        statement.executeUpdate();
    }

    private Category createCategoryObjFromResultSet(ResultSet resultSet) {
        try {
            Category category = new Category();
            category.setName(resultSet.getString("name"));
            category.setId(resultSet.getLong("id"));
            return category;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
