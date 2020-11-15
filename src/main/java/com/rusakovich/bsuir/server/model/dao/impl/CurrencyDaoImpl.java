package com.rusakovich.bsuir.server.model.dao.impl;

import com.rusakovich.bsuir.server.entity.Currency;
import com.rusakovich.bsuir.server.model.dao.ConnectionManager;
import com.rusakovich.bsuir.server.model.dao.CrudDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDaoImpl implements CrudDao<Currency> {
    private static final String SELECT_CURRENCY_BY_ID = "SELECT * FROM currency WHERE id = ?";
    private static final String SELECT_ALL_CURRENCY = "SELECT * FROM currency";
    private static final String INSERT_CURRENCY = "INSERT INTO currency ( name, code, short_name ) VALUES (?, ?, ?)";
    private static final String UPDATE_CURRENCY = "UPDATE currency SET name = ?,code = ?, short_name = ? WHERE id = ?";
    private static final String DELETE_CURRENCY = "DELETE FROM currency WHERE id = ?";

    @Override
    public void save(Currency currency) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CURRENCY)) {
            statement.setString(1, currency.getName());
            statement.setString(2, currency.getCode());
            statement.setString(3, currency.getShortName());
            statement.executeUpdate();
        }
    }

    @Override
    public Currency findById(Long id) {
        Currency currency = null;
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CURRENCY_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                currency = new Currency(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("code"),
                        resultSet.getString("short_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currency;
    }

    @Override
    public List<Currency> findAll() {
        List<Currency> currencies = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CURRENCY);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                currencies.add(new Currency(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("code"),
                        resultSet.getString("short_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currencies;
    }

    @Override
    public void update(Currency currency) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CURRENCY)) {
            statement.setString(1, currency.getName());
            statement.setString(2, currency.getCode());
            statement.setString(3, currency.getShortName());
            statement.setLong(4, currency.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_CURRENCY);
        statement.setLong(1, id);
        statement.executeUpdate();
    }
}
