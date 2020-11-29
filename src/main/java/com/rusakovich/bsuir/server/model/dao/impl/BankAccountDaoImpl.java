package com.rusakovich.bsuir.server.model.dao.impl;



import com.rusakovich.bsuir.server.entity.BankAccount;
import com.rusakovich.bsuir.server.model.dao.BankAccountDao;
import com.rusakovich.bsuir.server.model.dao.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankAccountDaoImpl implements BankAccountDao {
    private static final String SELECT_BANK_ACCOUNTS_BY_MEMBER_ID = "SELECT * FROM bank_account WHERE member_id = ?";
    private static final String SELECT_BANK_ACCOUNTS_BY_MEMBER_ACCOUNT_ID = "SELECT * FROM bank_account WHERE member_account_id = ?";
    private static final String SELECT_BANK_ACCOUNT_BY_ID = "SELECT * FROM bank_account WHERE id = ?";
    private static final String INSERT_BANK_ACCOUNT = "INSERT INTO bank_account ( name, currency_id, member_id, member_account_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_BANK_ACCOUNT = "UPDATE bank_account SET name = ?,currency_id = ?, member_id = ?, member_account_id = ? WHERE id = ?";
    private static final String DELETE_BANK_ACCOUNT = "DELETE FROM bank_account WHERE id = ?";

    @Override
    public List<BankAccount> findAllByMemberId(Long memberId) {
        List<BankAccount> bankAccounts = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BANK_ACCOUNTS_BY_MEMBER_ID);
            preparedStatement.setLong(1, memberId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bankAccounts.add(
                        createBankAccountObjFromResultSet(resultSet)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bankAccounts;
    }

    @Override
    public List<BankAccount> findAllByMemberAccountId(Long accountId) {
        List<BankAccount> bankAccounts = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BANK_ACCOUNTS_BY_MEMBER_ACCOUNT_ID);
            preparedStatement.setLong(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bankAccounts.add(
                        createBankAccountObjFromResultSet(resultSet)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bankAccounts;
    }

    @Override
    public void save(BankAccount bankAccount) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_BANK_ACCOUNT)) {
            statement.setString(1, bankAccount.getName());
            statement.setLong(2, bankAccount.getCurrencyId());
            statement.setLong(3, bankAccount.getMemberId());
            statement.setLong(4, bankAccount.getMemberAccountId());
            statement.executeUpdate();
        }
    }

    @Override
    public BankAccount findById(Long id) {
        BankAccount bankAccount = null;
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BANK_ACCOUNT_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                bankAccount = createBankAccountObjFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bankAccount;
    }

    @Override
    public List<BankAccount> findAll() {
        return null;
    }

    @Override
    public void update(BankAccount bankAccount) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_BANK_ACCOUNT)) {
            statement.setString(1, bankAccount.getName());
            statement.setLong(2, bankAccount.getCurrencyId());
            statement.setLong(3, bankAccount.getMemberId());
            statement.setLong(4, bankAccount.getMemberAccountId());
            statement.setLong(5, bankAccount.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_BANK_ACCOUNT);
        statement.setLong(1, id);
        statement.executeUpdate();
    }

    private BankAccount createBankAccountObjFromResultSet(ResultSet resultSet) {
        try {
            BankAccount bankAccount = new BankAccount();
            bankAccount.setId(resultSet.getLong("id"));
            bankAccount.setName(resultSet.getString("name"));
            bankAccount.setMemberId(resultSet.getLong("member_id"));
            bankAccount.setMemberAccountId(resultSet.getLong("member_account_id"));
            bankAccount.setCurrencyId(resultSet.getLong("currency_id"));
            return bankAccount;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
