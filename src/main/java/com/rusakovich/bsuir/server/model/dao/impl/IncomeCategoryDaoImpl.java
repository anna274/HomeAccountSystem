package com.rusakovich.bsuir.server.model.dao.impl;

import com.rusakovich.bsuir.server.entity.Category;
import com.rusakovich.bsuir.server.model.dao.ConnectionManager;
import com.rusakovich.bsuir.server.model.dao.CategoryDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IncomeCategoryDaoImpl implements CategoryDao {
    private static final String SELECT_ALL_INCOME_CATEGORY = "SELECT * FROM income_category";
    private static final String SELECT_INCOME_CATEGORY_BY_ID = "SELECT * FROM income_category WHERE id = ?";
    private static final String INSERT_INCOME_CATEGORY = "INSERT INTO income_category ( name ) VALUES (?)";
    private static final String UPDATE_INCOME_CATEGORY = "UPDATE income_category SET name = ? WHERE id = ?";
    private static final String DELETE_INCOME_CATEGORY = "DELETE FROM income_category WHERE id = ?";
    @Override
    public void save(Category entity) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_INCOME_CATEGORY)) {
            statement.setString(1, entity.getName());
            statement.executeUpdate();
        }
    }

    @Override
    public Category findById(Long id) {
        Category category = null;
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_INCOME_CATEGORY_BY_ID);
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
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_INCOME_CATEGORY);
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
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_INCOME_CATEGORY)) {
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_INCOME_CATEGORY);
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
