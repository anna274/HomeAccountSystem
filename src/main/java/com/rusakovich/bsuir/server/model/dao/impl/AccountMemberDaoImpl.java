package com.rusakovich.bsuir.server.model.dao.impl;

import com.rusakovich.bsuir.server.entity.AccountMember;
import com.rusakovich.bsuir.server.model.dao.AccountMemberDao;
import com.rusakovich.bsuir.server.model.dao.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountMemberDaoImpl implements AccountMemberDao {
    private static final String SELECT_MEMBERS_BY_ACCOUNT_ID = "SELECT * FROM account_member WHERE account_id = ?";
    private static final String SELECT_MEMBER_BY_ID = "SELECT * FROM account_member WHERE id = ?";
    private static final String INSERT_MEMBER = "INSERT INTO account_member ( name, account_id ) VALUES (?, ?)";
    private static final String UPDATE_MEMBER = "UPDATE account_member SET name = ?,account_id = ? WHERE id = ?";
    private static final String DELETE_MEMBER = "DELETE FROM account_member WHERE id = ?";

    @Override
    public List<AccountMember> findAllByAccountId(Long accountId) {
        List<AccountMember> accounts = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MEMBERS_BY_ACCOUNT_ID);
            preparedStatement.setLong(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accounts.add(createAccountMemberObjFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public void save(AccountMember accountMember) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_MEMBER)) {
            statement.setString(1, accountMember.getName());
            statement.setLong(2, accountMember.getAccountId());
            statement.executeUpdate();
        }
    }

    @Override
    public AccountMember findById(Long id) {
        AccountMember account = null;
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MEMBER_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account = createAccountMemberObjFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public List<AccountMember> findAll() {
        return null;
    }

    @Override
    public void update(AccountMember accountMember) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_MEMBER)) {
            statement.setString(1, accountMember.getName());
            statement.setLong(2, accountMember.getAccountId());
            statement.setLong(3, accountMember.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_MEMBER);
        statement.setLong(1, id);
        statement.executeUpdate();
    }

    private AccountMember createAccountMemberObjFromResultSet(ResultSet resultSet) {
        try {
            return new AccountMember(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getLong("account_id")
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
