package com.rusakovich.bsuir.server.model.dao.impl;

import com.rusakovich.bsuir.server.entity.Account;
import com.rusakovich.bsuir.server.entity.AccountRole;
import com.rusakovich.bsuir.server.model.dao.ConnectionManager;
import com.rusakovich.bsuir.server.model.dao.AccountDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao {
    private static final String SELECT_ACCOUNT_BY_LOGIN = "SELECT * FROM account WHERE login = ?";
    private static final String SELECT_ACCOUNT_BY_ID = "SELECT * FROM account WHERE id = ?";
    private static final String INSERT_ACCOUNT = "INSERT INTO account ( login, password, role ) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_ACCOUNTS = "SELECT * FROM account";
    private static final String UPDATE_ACCOUNT = "UPDATE account SET login = ?,password = ?, role = ? WHERE id = ?";
    private static final String DELETE_ACCOUNT = "DELETE FROM account WHERE id = ?";

    @Override
    public Account findByLogin(String login) {
        Account account = null;
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_BY_LOGIN);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account = createAccountObjFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public void save(Account account) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ACCOUNT)) {
            statement.setString(1, account.getLogin());
            statement.setString(2, account.getPassword());
            statement.setInt(3, account.getRoleId());
            statement.executeUpdate();
        }
    }

    @Override
    public Account findById(Long id) {
        Account account = null;
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account = createAccountObjFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ACCOUNTS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accounts.add(createAccountObjFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public void update(Account account) throws SQLException {
        System.out.println("for update" + account);
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ACCOUNT)) {
            statement.setString(1, account.getLogin());
            statement.setString(2, account.getPassword());
            statement.setInt(3, account.getRoleId());
            statement.setLong(4, account.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_ACCOUNT);
        statement.setLong(1, id);
        statement.executeUpdate();
    }

    private Account createAccountObjFromResultSet(ResultSet resultSet) {
        try {
            return new Account(
                    resultSet.getLong("id"),
                    resultSet.getString("login"),
                    resultSet.getString("password"),
                    resultSet.getInt("role")
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
