package com.rusakovich.bsuir.server.model.dao.impl;

import com.rusakovich.bsuir.server.entity.Income;
import com.rusakovich.bsuir.server.model.dao.ConnectionManager;
import com.rusakovich.bsuir.server.model.dao.IncomeDao;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncomeDaoImpl implements IncomeDao {
    private static final String SELECT_INCOME_BY_ID = "SELECT * FROM income WHERE id = ?";
    private static final String SELECT_INCOME_BY_MEMBER_ACCOUNT_ID = "SELECT * FROM income WHERE member_account_id = ?";
    private static final String SELECT_INCOME_BY_MEMBER_ID = "SELECT * FROM income WHERE member_id = ?";
    private static final String SELECT_INCOME_BY_BANK_ACCOUNT_ID = "SELECT * FROM income WHERE bank_account_id = ?";
    private static final String SELECT_INCOME_IN_DATE_DIAPASON = "SELECT * FROM income WHERE date > ? AND date < ?";
    private static final String INSERT_INCOME =
            "INSERT INTO income ( sum, note, category_id, date, bank_account_id, " +
            "currency_id, member_id, member_account_id ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_INCOME =
            "UPDATE income SET sum = ?, note = ?, category_id = ?, date = ?, " +
            "bank_account_id = ?, currency_id = ?, member_id = ?, member_account_id = ? WHERE id = ?";
    private static final String DELETE_INCOME = "DELETE FROM income WHERE id = ?";
    private static final String GROUP_BY_CATEGORY =
            "SELECT income_category.name, SUM(income.sum) " +
            "FROM income " +
            "INNER JOIN income_category ON income.category_id = income_category.id " +
            "WHERE ((income.date BETWEEN ? AND ?) AND income.member_account_id = ?) " +
            "GROUP BY income.category_id";
    private static final String GROUP_BY_BANK_ACCOUNT =
            "SELECT bank_account.name, SUM(income.sum) " +
                    "FROM income " +
                    "INNER JOIN bank_account ON income.bank_account_id = bank_account.id " +
                    "WHERE ((income.date BETWEEN ? AND ?) AND income.member_account_id = ?) " +
                    "GROUP BY income.bank_account_id";

    @Override
    public List<Income> findAllByMemberAccountId(Long memberAccountId) {
        List<Income> incomes = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_INCOME_BY_MEMBER_ACCOUNT_ID);
            preparedStatement.setLong(1, memberAccountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                incomes.add(createIncomeObjFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return incomes;
    }

    @Override
    public List<Income> findAllByMemberId(Long memberId) {
        List<Income> incomes = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_INCOME_BY_MEMBER_ID);
            preparedStatement.setLong(1, memberId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                incomes.add(createIncomeObjFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return incomes;
    }

    @Override
    public List<Income> findAllByBankAccountId(Long bankAccountId) {
        List<Income> incomes = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_INCOME_BY_BANK_ACCOUNT_ID);
            preparedStatement.setLong(1, bankAccountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                incomes.add(createIncomeObjFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return incomes;
    }

    @Override
    public List<Income> findAllInDateDiapason(LocalDateTime begin, LocalDateTime end) {
        List<Income> incomes = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_INCOME_IN_DATE_DIAPASON);
            preparedStatement.setObject(1, begin);
            preparedStatement.setObject(2, end);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                incomes.add(createIncomeObjFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return incomes;
    }

    @Override
    public Map<String, Float> groupByCategory(Long memberAccountId, LocalDate begin, LocalDate end) {
        Map<String, Float> res = new HashMap<>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GROUP_BY_CATEGORY);
            preparedStatement.setObject(1, begin);
            preparedStatement.setObject(2, end);
            preparedStatement.setLong(3, memberAccountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                res.put(resultSet.getString(1), resultSet.getFloat(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Map<String, Float> groupByBankAccount(Long memberAccountId, LocalDate begin, LocalDate end) {
        Map<String, Float> res = new HashMap<>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GROUP_BY_BANK_ACCOUNT);
            preparedStatement.setObject(1, begin);
            preparedStatement.setObject(2, end);
            preparedStatement.setLong(3, memberAccountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                res.put(resultSet.getString(1), resultSet.getFloat(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    @Override
    public void save(Income income) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_INCOME)) {
            statement.setFloat(1, income.getSum());
            statement.setString(2, income.getNote());
            statement.setLong(3, income.getCategoryId());
            statement.setObject(4, income.getDate());
            statement.setLong(5, income.getBankAccountId());
            statement.setLong(6, income.getCurrencyId());
            statement.setLong(7, income.getMemberId());
            statement.setLong(8, income.getMemberAccountId());
            statement.executeUpdate();
        }
    }

    @Override
    public Income findById(Long id) {
        Income income = null;
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_INCOME_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                income = createIncomeObjFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return income;
    }

    @Override
    public List<Income> findAll() {
        return null;
    }

    @Override
    public void update(Income income) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_INCOME)) {
            statement.setFloat(1, income.getSum());
            statement.setString(2, income.getNote());
            statement.setLong(3, income.getCategoryId());
            statement.setObject(4, income.getDate());
            statement.setLong(5, income.getBankAccountId());
            statement.setLong(6, income.getCurrencyId());
            statement.setLong(7, income.getMemberId());
            statement.setLong(8, income.getMemberAccountId());
            statement.setLong(9, income.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException{
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_INCOME);
        statement.setLong(1, id);
        statement.executeUpdate();
    }

    private Income createIncomeObjFromResultSet(ResultSet resultSet) {
        try {
            return new Income(
                    resultSet.getLong("id"),
                    resultSet.getLong("category_id"),
                    resultSet.getLong("member_id"),
                    resultSet.getLong("member_account_id"),
                    resultSet.getLong("bank_account_id"),
                    resultSet.getLong("currency_id"),
                    resultSet.getFloat("sum"),
                    resultSet.getString("note"),
                    resultSet.getInt("quantity"),
                    resultSet.getObject("date", LocalDate.class)
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
