package com.rusakovich.bsuir.server.model.dao.impl;

import com.rusakovich.bsuir.server.entity.Expense;
import com.rusakovich.bsuir.server.model.dao.ConnectionManager;
import com.rusakovich.bsuir.server.model.dao.ExpenseDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDaoImpl implements ExpenseDao {
    private static final String SELECT_EXPENSES_BY_MEMBER_ACCOUNT_ID = "SELECT * FROM expense WHERE member_account_id = ?";
    private static final String SELECT_EXPENSES_BY_MEMBER_ID = "SELECT * FROM expense WHERE member_id = ?";
    private static final String SELECT_EXPENSES_BY_BANK_ACCOUNT_ID = "SELECT * FROM expense WHERE bank_account_id = ?";
    private static final String SELECT_EXPENSES_IN_DATE_DIAPASON = "SELECT * FROM expense WHERE date > ? AND date < ?";
    private static final String SELECT_EXPENSE_BY_ID = "SELECT * FROM expense WHERE id = ?";
    private static final String INSERT_EXPENSE =
            "INSERT INTO expense ( sum, note, category_id, quantity, date, bank_account_id, " +
             "currency_id, member_id, member_account_id ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_EXPENSE =
            "UPDATE expense SET sum = ?, note = ?, category_id = ?, quantity = ?, date = ?, " +
            "bank_account_id = ?, currency_id = ?, member_id = ?, member_account_id = ? WHERE id = ?";
    private static final String DELETE_EXPENSE = "DELETE FROM expense WHERE id = ?";

    @Override
    public List<Expense> findAllByMemberAccountId(Long memberAccountId) {
        List<Expense> expenses = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EXPENSES_BY_MEMBER_ACCOUNT_ID);
            preparedStatement.setLong(1, memberAccountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                expenses.add(createExpenseObjFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenses;
    }

    @Override
    public List<Expense> findAllByMemberId(Long memberAccountId) {
        List<Expense> expenses = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EXPENSES_BY_MEMBER_ID);
            preparedStatement.setLong(1, memberAccountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                expenses.add(createExpenseObjFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenses;
    }

    @Override
    public List<Expense> findAllByBankAccountId(Long bankAccountId) {
        List<Expense> expenses = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EXPENSES_BY_BANK_ACCOUNT_ID);
            preparedStatement.setLong(1, bankAccountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                expenses.add(createExpenseObjFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenses;
    }

    @Override
    public List<Expense> findAllInDateDiapason(LocalDateTime begin, LocalDateTime end) {
        List<Expense> expenses = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EXPENSES_IN_DATE_DIAPASON);
            preparedStatement.setObject(1, begin);
            preparedStatement.setObject(2, end);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                expenses.add(createExpenseObjFromResultSet((resultSet)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenses;
    }

    @Override
    public void save(Expense expense) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_EXPENSE)) {
            statement.setFloat(1, expense.getSum());
            statement.setString(2, expense.getNote());
            statement.setLong(3, expense.getCategoryId());
            statement.setInt(4, expense.getQuantity());
            statement.setObject(5, expense.getDate());
            statement.setLong(6, expense.getBankAccountId());
            statement.setLong(7, expense.getCurrencyId());
            statement.setLong(8, expense.getMemberId());
            statement.setLong(9, expense.getMemberAccountId());
            statement.executeUpdate();
        }
    }

    @Override
    public Expense findById(Long id) {
        Expense expense = null;
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EXPENSE_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                expense = createExpenseObjFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expense;
    }

    @Override
    public List<Expense> findAll() {
        return null;
    }

    @Override
    public void update(Expense expense) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_EXPENSE)) {
            statement.setFloat(1, expense.getSum());
            statement.setString(2, expense.getNote());
            statement.setLong(3, expense.getCategoryId());
            statement.setInt(4, expense.getQuantity());
            statement.setObject(5, expense.getDate());
            statement.setLong(6, expense.getBankAccountId());
            statement.setLong(7, expense.getCurrencyId());
            statement.setLong(8, expense.getMemberId());
            statement.setLong(9, expense.getMemberAccountId());
            statement.setLong(10, expense.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_EXPENSE);
        statement.setLong(1, id);
        statement.executeUpdate();
    }

    private Expense createExpenseObjFromResultSet(ResultSet resultSet) {
        try {
            return new Expense(
                    resultSet.getLong("id"),
                    resultSet.getLong("category_id"),
                    resultSet.getLong("member_id"),
                    resultSet.getLong("member_account_id"),
                    resultSet.getLong("bank_account_id"),
                    resultSet.getLong("currency_id"),
                    resultSet.getFloat("sum"),
                    resultSet.getInt("quantity"),
                    resultSet.getObject("date", LocalDateTime.class),
                    resultSet.getString("note")
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
